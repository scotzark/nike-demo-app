package com.scoti.nikesampleapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.scoti.nikesampleapp.models.Image
import com.scoti.nikesampleapp.R
import com.scoti.nikesampleapp.adapters.EndlessRecyclerAdapter
import kotlin.math.abs

class EndlessRecyclerView : LinearLayout {

    val adapter by lazy {
        EndlessRecyclerAdapter(context)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        val view = LayoutInflater.from(context).inflate(R.layout.view_endless_recycler, this, false)
        setup(view)
        addView(view)
      }

    fun setup(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager =
            ProminentLayoutManager(
                context
            )
        recycler.addOnScrollListener(CircularScrollListener())

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
    }

    fun load(images: List<Image>) {
        adapter.load(images)
    }

    internal class ProminentLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
        val minScale = 1.5f
        val scaleDownFactor = 0.5f

        override fun onLayoutCompleted(state: RecyclerView.State?) =
            super.onLayoutCompleted(state).also { scalePicture() }

        override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State) = super.scrollHorizontallyBy(dx, recycler, state).also { if (orientation == HORIZONTAL) scalePicture() }

        private fun scalePicture() {
            val containerCenter = (width) / 2f

            val threshold = minScale * containerCenter

            for (i in 0 until childCount) {
                getChildAt(i)?.let {
                    val child = it
                    val center = (child.left + child.right) / 2f
                    val scale = 1f - scaleDownFactor * (abs(center - containerCenter) / threshold).coerceAtMost(1f)
                    child.scaleX = scale
                    child.scaleY = scale
                }


            }
        }
    }

    internal class CircularScrollListener: RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstItemVisible: Int = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstItemVisible != 0 && (firstItemVisible % (recyclerView.adapter as EndlessRecyclerAdapter).numberOfItems) == 0) {
                recyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }

}
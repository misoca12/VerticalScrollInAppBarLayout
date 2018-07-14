package com.misoca.verticalscrollinappbarlayout

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.MotionEvent




class HeaderPagerAdapter(val context: Context): PagerAdapter() {
    companion object {
        const val PAGE_COUNT = 2
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layout: View? = null
        when (position) {
            0 -> {
                layout = inflater.inflate(R.layout.page_first, null)
                initNestedScrollView(layout)
            }
            1 -> {
                layout = inflater.inflate(R.layout.page_second, null)
                initRecyclerView(layout)
            }
        }

        container.addView(layout!!)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    private fun initNestedScrollView(view: View) {
        val scrollView = view.findViewById<NestedScrollView>(R.id.nested_scroll_view)
        scrollView.setOnTouchListener(getNestedVerticalScrollTouchListener())
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager(context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        val words: Array<String?> = arrayOfNulls(20)
        for (i in 0..19) {
            words[i] = "This is RecyclerView Row " + (i + 1)
        }
        val adapter = RowAdapter(words)
        recyclerView.adapter = adapter
        recyclerView.setOnTouchListener(getNestedVerticalScrollTouchListener())
    }

    private fun getNestedVerticalScrollTouchListener(): View.OnTouchListener {
        val minDistance = 100
        var downY: Float = 0F
        var upY: Float = 0F
        return View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = event.y
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    upY = event.y
                    return@OnTouchListener true
                }
            }
            val deltaY = downY - upY
            if (Math.abs(deltaY) > minDistance) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            return@OnTouchListener false
        }
    }

}
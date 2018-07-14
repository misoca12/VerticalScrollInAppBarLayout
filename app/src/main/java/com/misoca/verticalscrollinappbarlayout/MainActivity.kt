package com.misoca.verticalscrollinappbarlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.Toolbar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager(this).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        val words: Array<String?> = arrayOfNulls(20)
        for (i in 0..19) {
            words[i] = "word" + (i + 1)
        }
        val adapter = RowAdapter(words)
        recyclerView.adapter = adapter

        // ViewPager
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = HeaderPagerAdapter(this)

    }

}

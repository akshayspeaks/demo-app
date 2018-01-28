package com.example.bouch.demo

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.search_item_layout.view.*


/**
 * Created by bouch on 28/1/18.
 */
class SearchItemCursorAdapter(private val mContext: Context, cursor: Cursor, private val searchView: SearchView)
    : CursorAdapter(mContext, cursor, false) {
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        Log.i("newView","new view added")
        return mLayoutInflater.inflate(R.layout.search_item_layout, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
        view.movie_title.text=title
        val poster = cursor.getString(cursor.getColumnIndexOrThrow("poster"))
        Glide.with(view).load("https://image.tmdb.org/t/p/w92"+poster).into(view.movie_poster)
        view.setOnClickListener {
            searchView.isIconified = true
            Toast.makeText(context, "Selected movie " + title,
                    Toast.LENGTH_LONG).show()
        }
    }
}
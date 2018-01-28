package com.example.bouch.demo

import android.database.MatrixCursor
import com.example.bouch.demo.model.Results
import com.example.bouch.demo.model.SearchResult

/**
 * Created by bouch on 28/1/18.
 */
class CursorUtil {
    companion object {
        fun getCursor(list: List<Results>):MatrixCursor{
            val cursor = MatrixCursor(arrayOf("_id","title","poster"))
            val lst:List<Results> = if (list.size>6)
                list.subList(0,5)
            else
                list
            for (item in lst){
                cursor.newRow().add("_id",item.id)
                        .add("title",item.title)
                        .add("poster",item.posterPath)
            }
            return cursor
        }
    }
}
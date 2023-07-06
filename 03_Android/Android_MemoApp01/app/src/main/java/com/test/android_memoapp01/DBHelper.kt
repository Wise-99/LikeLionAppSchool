package com.test.android_memoapp01

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Memo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """create table MemoTable
            (idx integer primary key autoincrement,
            title text not null,
            content integer not null,
            date date not null)
        """.trimIndent()

        // 쿼리문을 수행한다.
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}
package com.test.android_memoapp2

import android.content.Context

class DAO {
    companion object{

        // PasswordTable에 데이터가 존재하는지 확인
        fun tableExist(context: Context) : Boolean{
            val sql = """select count(password) as count 
                from PasswordTable
            """.trimIndent()

            // 데이터베이스 오픈
            val dbHelper = DBHelper(context)
            // 쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)
            cursor.moveToNext()

            // count 컬럼의 인덱스를 가져온다.
            val idx1 = cursor.getColumnIndex("count")
            dbHelper.close()

            // 데이터의 갯수가 1 이상이면 true
            if(cursor.getInt(idx1) > 0){
                return true
            }
            // 아니라면 비밀번호 데이터가 없으므로 false
            return false
        }

        // password 저장
        fun insertPassword(context: Context, pwd:String){
            val sql = """insert into PasswordTable
                |(password)
                |values (?)
            """.trimMargin()

            val arg1 = arrayOf(pwd)

            // 데이터베이스 오픈
            val sqliteDatabase = DBHelper(context)
            // 쿼리 실행(쿼리문 ?에 셋팅할 값 배열)
            sqliteDatabase.writableDatabase.execSQL(sql, arg1)
            // 데이터 베이스를 닫아준다.
            sqliteDatabase.close()
        }

        fun comparePassword(context: Context, pwd : String) : Boolean{
            val sql = "select * from PasswordTable where idx=1"

            val sqliteDatabase = DBHelper(context)
            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)
            cursor.moveToNext()

            val idx1 = cursor.getColumnIndex("password")
            dbHelper.close()

            if(cursor.getString(idx1) == pwd){
                return true
            }

            return false
        }

        fun selectAllTable(context: Context): MutableList<String>{
            // 모든 행을 가져오는 쿼리문을 작성한다.
            val sql = "select tbl_name from sqlite_master"

            // 데이터베이스 오픈
            val dbHelper = DBHelper(context)
            // 쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            val tableList = mutableListOf<String>()
            while (cursor.moveToNext()) {
                // 컬럼의 이름을 지정하여 컬럼의 순서 값을 가져온다.
                val idx1 = cursor.getColumnIndex("tbl_name")

                // 데이터를 가져온다.
                val table = cursor.getString(idx1)

                // 기본 테이블 제외 및 비밀번호가 들어있는 테이블 제외
                if (table != "android_metadata" && table != "sqlite_sequence" && table != "PasswordTable"){
                    tableList.add(0, table)
                }
            }

            dbHelper.close()
            return  tableList
        }

        // 카테고리 이름으로 된 테이블 생성
        fun createCategory(context: Context, tableName:String){
            val sql = """create table "$tableName"
                (idx integer primary key autoincrement,
                title text not null,
                content text not null,
                date Date not null)
            """.trimIndent()

            // 데이터베이스 오픈
            val sqliteDatabase = DBHelper(context)
            // 쿼리 실행
            sqliteDatabase.writableDatabase.execSQL(sql)
            // 데이터 베이스를 닫아준다.
            sqliteDatabase.close()
        }

        fun updateCategory(context: Context, oldTableName: String, newTableName:String){
            val sql = """alter table "$oldTableName"
                rename to "$newTableName"
            """.trimIndent()

            // 데이터베이스 오픈
            val sqliteDatabase = DBHelper(context)
            // 쿼리 실행
            sqliteDatabase.writableDatabase.execSQL(sql)
            // 데이터 베이스를 닫아준다.
            sqliteDatabase.close()
        }

        fun deleteCategory(context: Context, tableName:String){
            val sql = """drop table "$tableName"
            """.trimMargin()

            // 데이터베이스 오픈
            val sqliteDatabase = DBHelper(context)
            // 쿼리 실행
            sqliteDatabase.writableDatabase.execSQL(sql)
            // 데이터 베이스를 닫아준다.
            sqliteDatabase.close()
        }

        fun insertMemo(context: Context, memo:MemoClass, table:String){
            // autoinctement가 있는 컬럼은 제외하고 나머지만 지정
            val sql = """insert into "$table"
                | (title, content, date)
                | values (?, ?, ?)
            """.trimMargin()

            // ? 에 설정할 값을 배열에 담아준다.
            val arg1 = arrayOf(memo.title, memo.content, memo.date)

            // 데이터베이스 오픈
            val sqliteDatabase = DBHelper(context)
            // 쿼리 실행(쿼리문 ?에 셋팅할 값 배열)
            sqliteDatabase.writableDatabase.execSQL(sql, arg1)
            // 데이터 베이스를 닫아준다.
            sqliteDatabase.close()
        }

        // 메모 하나 선택
        fun selectMemo(context: Context, idx:Int, table:String):MemoClass{
            // 쿼리문
            val sql = """select * from "$table" where idx=?"""
            // ?에 들어갈 값(문자열 배열)
            val arg1 = arrayOf("$idx")

            // 데이터베이스 오픈
            val dbHelper = DBHelper(context)
            // 쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg1)
            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서 값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("title")
            val idx3 = cursor.getColumnIndex("content")
            val idx4 = cursor.getColumnIndex("date")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val title = cursor.getString(idx2)
            val content = cursor.getString(idx3)
            val date = cursor.getString(idx4)

            val studentClass = MemoClass(idx, title, content, date)

            dbHelper.close()
            return studentClass
        }

        // 모든 메모를 가져온다.
        fun selectAllMemo(context: Context, table:String) : MutableList<MemoClass>{
            // 모든 행을 가져오는 쿼리문을 작성한다.
            val sql = """select * from "$table" order by idx DESC"""

            // 데이터베이스 오픈
            val dbHelper = DBHelper(context)
            // 쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)
            // cursor 객체에는 쿼리문에 맞는 행에 접근할 수 있는 객체가 된다.
            // 처음에는 아무 행도 가르치고 있지 않는다.
            // moveToNext 메서드를 호출하면 다음 행에 접근할 수 있다.
            // 이 때 접근할 행이 있으면 true를 반환하고 없으면 false를 반환한다.
            val dataList = mutableListOf<MemoClass>()
            while (cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서 값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("title")
                val idx3 = cursor.getColumnIndex("content")
                val idx4 = cursor.getColumnIndex("date")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val title = cursor.getString(idx2)
                val content = cursor.getString(idx3)
                val date = cursor.getString(idx4)

                val studentClass = MemoClass(idx, title, content, date)
                dataList.add(studentClass)
            }

            dbHelper.close()
            return  dataList
        }

        // 메모 데이터 수정
        fun updateMemo(context: Context, obj:MemoClass, idx:Int, table:String){
            // 쿼리문
            val sql = """update "$table"
                | set title = ?, content = ?, date = ?
                | where idx = ?
            """.trimMargin()

            // ?에 들어갈 값
            val args = arrayOf(obj.title, obj.content, obj.date, idx)
            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // 조건에 맞는 메모 삭제
        fun deleteMemo(context: Context, idx: Int, table:String){
            // 쿼리문
            val sql = """delete from "$table" where idx = ?"""
            // ?에 들어갈 값
            val args = arrayOf(idx)
            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}

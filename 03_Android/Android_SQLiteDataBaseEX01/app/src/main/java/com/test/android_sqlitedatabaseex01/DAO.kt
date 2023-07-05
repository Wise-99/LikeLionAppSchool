package com.test.android_sqlitedatabaseex01

import android.content.Context

class DAO {
    companion object{
        // Create : 저장
        fun insertData(context: Context, data:StudentClass){
            // autoinctement가 있는 컬럼은 제외하고 나머지만 지정한다.
            val sql = """insert into InfoTable
                | (name, age, korean)
                | values (?, ?, ?)
            """.trimMargin()

            // ? 에 설정할 값을 배열에 담아준다.
            val arg1 = arrayOf(data.name, data.age, data.korean)

            // 데이터베이스 오픈
            val sqliteDatabase = DBHelper(context)
            // 쿼리 실행(쿼리문 ?에 셋팅할 값 배열)
            sqliteDatabase.writableDatabase.execSQL(sql, arg1)
            // 데이터 베이스를 닫아준다.
            sqliteDatabase.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, idx:Int):StudentClass{
            // 쿼리문
            val sql = "select * from InfoTable where idx=?"
            // ?에 들어갈 값(문자열 배열)
            val arg1 = arrayOf("$idx")

            // 데이터베이스 오픈
            val dbHelper = DBHelper(context)
            // 쿼리실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg1)
            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서 값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("name")
            val idx3 = cursor.getColumnIndex("age")
            val idx4 = cursor.getColumnIndex("korean")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val name = cursor.getString(idx2)
            val age = cursor.getInt(idx3)
            val korean = cursor.getInt(idx4)

            val studentClass = StudentClass(idx, name, age, korean)

            dbHelper.close()
            return studentClass
        }

        // Read All : 모든 행을 가져온다.
        fun selectAllData(context: Context) : MutableList<StudentClass>{
            // 모든 행을 가져오는 쿼리문을 작성한다.
            val sql = "select * from InfoTable"

            // 데이터베이스 오픈
            val dbHelper = DBHelper(context)
            // 쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)
            // cursor 객체에는 쿼리문에 맞는 행에 접근할 수 있는 객체가 된다.
            // 처음에는 아무 행도 가르치고 있지 않는다.
            // moveToNext 메서드를 호출하면 다음 행에 접근할 수 있다.
            // 이 때 접근할 행이 있으면 true를 반환하고 없으면 false를 반환한다.
            val dataList = mutableListOf<StudentClass>()
            while (cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서 값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("name")
                val idx3 = cursor.getColumnIndex("age")
                val idx4 = cursor.getColumnIndex("korean")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val name = cursor.getString(idx2)
                val age = cursor.getInt(idx3)
                val korean = cursor.getInt(idx4)

                val studentClass = StudentClass(idx, name, age, korean)
                dataList.add(studentClass)
            }

            dbHelper.close()
            return  dataList
        }

        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, obj:StudentClass, idx:Int){
            // 쿼리문
            val sql = """update InfoTable
                | set name = ?, age = ?, korean = ?
                | where idx = ?
            """.trimMargin()

            // ?에 들어갈 값
            val args = arrayOf(obj.name, obj.age, obj.korean, idx)
            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // Delete : 조건에 맞는 행을 삭제한다.
        fun deleteData(context: Context, idx: Int){
            // 쿼리문
            val sql = "delete from InfoTable where idx = ?"
            // ?에 들어갈 값
            val args = arrayOf(idx)
            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}
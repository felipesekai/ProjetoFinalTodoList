package com.sekai.todolist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.sekai.todolist.model.Task

class HelperDb(
    context: Context?,
) : SQLiteOpenHelper(
    context, NOME_DB, null, VERSION) {
    companion object {
       const val NOME_DB = "todolist.db"
       const val VERSION = 1

        const val TABLE_NAME = "lISTA_TAREFAS"
        const val COLUMN_ID = "ID"
        const val COLUMN_TITLE = "TITLE"
        const val COLUMN_DESCRIPTION = "DESCRIPTION"
        const val COLUMN_DATA = "DATA"
        const val COLUMN_HOUR = "HORA"
        const val DROP_TABLE_LIST = "DROP TABLE IF EXISTS $TABLE_NAME"

        // creatTable
        const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "$COLUMN_TITLE STRING NOT NULL," +
                "$COLUMN_DESCRIPTION STRING," +
                "$COLUMN_DATA STRING NOT NULL," +
                "$COLUMN_HOUR STRING NOT NULL" +
                "" +
                ")"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE_LIST)
            onCreate(db)

        }
    }

    fun insertList(item: Task) {
        val db = writableDatabase ?: return
        val content = contentValuesOf()
        content.put(COLUMN_TITLE, item.title)
        content.put(COLUMN_DESCRIPTION, item.description)
        content.put(COLUMN_DATA, item.date)
        content.put(COLUMN_HOUR, item.hour)

        db.insert(TABLE_NAME, null, content)
        db.close()
    }

    fun getAllList(): ArrayList<Task> {
        val db = readableDatabase ?: return arrayListOf()
        val sql = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(sql, null)
        val lista = arrayListOf<Task>()

        if (cursor == null) {
            return arrayListOf<Task>()
        }
        while (cursor.moveToNext()) {

            val item = Task(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DATA)),
                cursor.getString(cursor.getColumnIndex(COLUMN_HOUR))
            )

            lista.add(item)
        }
        db.close()
        cursor.close()

        return lista

    }

    fun updateItem(item: Task){
        val db = writableDatabase ?: return
        val content = contentValuesOf()
        content.put(COLUMN_TITLE, item.title)
        content.put(COLUMN_DESCRIPTION, item.description)
        content.put(COLUMN_DATA, item.date)
        content.put(COLUMN_HOUR, item.hour)
        val where = "$COLUMN_ID = ?"
        val args = arrayOf("${item.id}")
        db.update(TABLE_NAME,content,where,args)
        db.close()
    }

    fun deleteItem(item: Task){
        val db = writableDatabase ?: return
        val where = "$COLUMN_ID =  ?"
        val args = arrayOf("${item.id}")
        db.delete(TABLE_NAME,where,args)
        db.close()
    }

}
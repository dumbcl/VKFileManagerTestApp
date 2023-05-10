package com.example.vkfilemanagertestapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.nio.file.Files

class DBHelper(context: Context) : SQLiteOpenHelper(context, "files.db", null, 1) {

    var changedFiles: MutableList<File> = ArrayList()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS files (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "path TEXT UNIQUE," +
                "hash TEXT NOT NULL," +
                "changed TEXT NOT NULL);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}

fun saveFileHashToDb(dbHelper: DBHelper, filePath: String, new_hash: String){
    val db = dbHelper.writableDatabase
    val cursor = db.query("files", arrayOf("id", "path", "hash", "changed"), "path=?", arrayOf(filePath), null, null, null)
    if (cursor.moveToFirst()) {
        val id = cursor.getInt(0)
        val old_hash = cursor.getString(2)
        if (!old_hash.equals(new_hash)){
            db.execSQL(
                "UPDATE files SET hash=? WHERE id=?",
                arrayOf(new_hash, id)
            )
            db.execSQL(
                "UPDATE files SET changed=? WHERE id=?",
                arrayOf("YES", id)
            )
        } else {
            db.execSQL(
                "UPDATE files SET changed=? WHERE id=?",
                arrayOf("NO", id)
            )
        }
    } else {
        db.execSQL(
            "INSERT INTO files (path,hash,changed) VALUES (?,?,?)",
            arrayOf(filePath, new_hash, "YES")
        )
    }
    cursor.close()
    db.close()
}

fun getChangedFiles(dbHelper: DBHelper): String{
    val db = dbHelper.writableDatabase
    val cursor = db.query("files", arrayOf("path"), "changed=?", arrayOf("YES"), null, null, null)

    cursor.moveToFirst()
    var files = ""
    while(!cursor.isAfterLast){
        files = files + cursor.getString(0) + "\n"
        cursor.moveToNext()
    }
    cursor.close()
    db.close()

    return files
}

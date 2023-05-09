package com.example.vkfilemanagertestapp

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_manager)

        val filesRecyclerView = findViewById<RecyclerView>(R.id.files_recycler_view)
        val oopsText = findViewById<TextView>(R.id.oops_text)
        val returnToMainButton = findViewById<Button>(R.id.return_to_main_button)

        returnToMainButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        val path = intent.getStringExtra("path")
        if (path == null) {
            oopsText.visibility = View.VISIBLE
            returnToMainButton.visibility = View.VISIBLE
        } else {
            oopsText.visibility = View.INVISIBLE
            returnToMainButton.visibility = View.INVISIBLE
            val root = File(path)
            var files = root.listFiles()
            if (files == null || files.isEmpty()) {
                oopsText.visibility = View.VISIBLE
                oopsText.setText(R.string.no_files)
            } else {
                var sortType = intent.getStringExtra("sort_type")
                if (sortType == null) {
                    sortType = "byNameDesc"
                }
                files = sortFiles(sortType, files)
                filesRecyclerView.layoutManager = LinearLayoutManager(this)
                filesRecyclerView.adapter = Adapter(applicationContext, files)
            }
        }
    }

    fun sortFiles(sortType: String, files: Array<File>): Array<File>{
        return when (sortType) {
            "bySizeAsc" -> sortBySizeAsc(files)
            "bySizeDesc" -> sortBySizeDesc(files)
            "byNameAsc" -> sortByNameAsc(files)
            "byNameDesc" -> sortByNameDesc(files)
            "byDateAsc" -> sortByDateAsc(files)
            "byDateDesc" -> sortByDateDesc(files)
            "byExtAsc" -> sortByExtAsc(files)
            "byExtDesc" -> sortByExtDesc(files)
            else -> sortByNameAsc(files)
        }
    }

}
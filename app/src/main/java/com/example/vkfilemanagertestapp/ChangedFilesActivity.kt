package com.example.vkfilemanagertestapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ChangedFilesActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changed_files)

        val changedFilesRecyclerView = findViewById<RecyclerView>(R.id.changed_files_recycler_view)
        val noText = findViewById<TextView>(R.id.no_text)
        noText.visibility = View.INVISIBLE
        var files = getChangedFilesList()
        if (files == null || files.isEmpty()) {
            noText.visibility = View.VISIBLE
            noText.setText(R.string.no_files)
        } else {
            changedFilesRecyclerView.layoutManager = LinearLayoutManager(this)
            changedFilesRecyclerView.adapter = ChangedAdapter(applicationContext, files)
        }
    }

    private fun getChangedFilesList() : Array<File?> {
        if (intent.getStringExtra("changed_files") == null) return emptyArray()
        else {
            val files = intent.getStringExtra("changed_files")!!.split("\n")
            val changedFiles = arrayOfNulls<File>(files.size)
            for (i in 0 until files.size){
                changedFiles[i] = File(files[i])
            }
            return changedFiles
        }
    }
}
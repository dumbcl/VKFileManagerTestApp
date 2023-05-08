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
            val files = root.listFiles()
            if (files == null || files.isEmpty()) {
                oopsText.visibility = View.VISIBLE
                oopsText.setText(R.string.no_files)
            } else {
                filesRecyclerView.layoutManager = LinearLayoutManager(this)
                filesRecyclerView.adapter = Adapter(applicationContext, files)
            }
        }
    }

    private fun sortBySizeDesc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortBySizeAsc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortByDateDesc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortByDateAsc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortByExtDesc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortByExtAsc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortByNameDesc(files: Array<File>): Array<File> {
        return files
    }

    private fun sortByNameAsc(files: Array<File>): Array<File> {
        return files
    }

}
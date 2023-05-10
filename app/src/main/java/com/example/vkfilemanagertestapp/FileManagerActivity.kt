package com.example.vkfilemanagertestapp

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
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
        val ascSortsButton = findViewById<ImageView>(R.id.ascSortsView)
        val descSortsButton = findViewById<ImageView>(R.id.descSortsView)
        val listOptionsButton = findViewById<ImageView>(R.id.listOptionsView)

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


        ascSortsButton.setOnClickListener {
            openAscMenu(path!!, ascSortsButton)
        }

        descSortsButton.setOnClickListener {
            openDescMenu(path!!, descSortsButton)
        }
    }

    private fun sortFiles(sortType: String, files: Array<File>): Array<File>{
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

    private fun openAscMenu(path: String, it: ImageView){
        val optionsMenu = PopupMenu(this, it)
        optionsMenu.menu.add("Sort by size asc")
        optionsMenu.menu.add("Sort by name asc")
        optionsMenu.menu.add("Sort by date asc")
        optionsMenu.menu.add("Sort by extension asc")

        optionsMenu.setOnMenuItemClickListener { item ->
            if (item.title == "Sort by size asc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "bySizeAsc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            if (item.title == "Sort by name asc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "byNameAsc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            if (item.title == "Sort by date asc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "byDateAsc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            if (item.title == "Sort by extension asc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "byExtAsc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            true
        }
        optionsMenu.show()
    }

    private fun openDescMenu(path: String, it: ImageView){
        val optionsMenu = PopupMenu(this, it)
        optionsMenu.menu.add("Sort by size desc")
        optionsMenu.menu.add("Sort by name desc")
        optionsMenu.menu.add("Sort by date desc")
        optionsMenu.menu.add("Sort by extension desc")

        optionsMenu.setOnMenuItemClickListener { item ->
            if (item.title == "Sort by size desc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "bySizeDesc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            if (item.title == "Sort by name desc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "byNameDesc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            if (item.title == "Sort by date desc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "byDateDesc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            if (item.title == "Sort by extension desc") {
                val intent = Intent(this, FileManagerActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("sort_type", "byExtDesc")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            true
        }
        optionsMenu.show()
    }

}
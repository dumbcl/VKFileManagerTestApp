package com.example.vkfilemanagertestapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionRequestButton = findViewById<Button>(R.id.permission_request_button)
        val toFileManagerButton = findViewById<Button>(R.id.to_manager_button)
        val toChangedFilesButton = findViewById<Button>(R.id.to_changed_files)

        permissionRequestButton.setOnClickListener(View.OnClickListener {
            if (checkPermission()) {
                val intent = Intent(this, FileManagerActivity::class.java)
                val path = Environment.getExternalStorageDirectory().path
                intent.putExtra("path", path)
                startActivity(intent)
            } else {
                requestPermission()
            }
        })

        toFileManagerButton.setOnClickListener(View.OnClickListener {
            if (checkPermission()) {
                val intent = Intent(this, FileManagerActivity::class.java)
                val path = Environment.getExternalStorageDirectory().path
                intent.putExtra("path", path)
                startActivity(intent)
            } else {
                val intent = Intent(this, FileManagerActivity::class.java)
                startActivity(intent)
            }
        })

        toChangedFilesButton.setOnClickListener(View.OnClickListener {
            if (checkPermission()) {
                val intent = Intent(this, ChangedFilesActivity::class.java)
                val changedFiles = startActivityGetChangedFiles()
                intent.putExtra("changed_files", changedFiles)
                startActivity(intent)
            } else {
                requestPermission()
            }
        })

    }

    private fun checkPermission(): Boolean {
        val writePermissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return (writePermissionCheck == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )
    }

    private fun startActivityGetChangedFiles(): String{
        dbHelper = DBHelper(this)
        var changedFiles = ""
        if (checkPermission()){
            File(Environment.getExternalStorageDirectory().path).walkTopDown().forEach {
                if (it.isFile){
                    val fileHash = getFileHash(it)
                    saveFileHashToDb(dbHelper, it.path, fileHash)
                }
            }
            changedFiles = getChangedFiles(dbHelper)
        }
        return changedFiles
    }

}
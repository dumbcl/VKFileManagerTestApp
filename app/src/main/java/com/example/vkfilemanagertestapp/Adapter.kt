package com.example.vkfilemanagertestapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.Date

class Adapter(var context: Context, var files: Array<File>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedFile = files[position]
        holder.fileSizeText.text = getFileStringSize(selectedFile)
        holder.fileDateText.text = "Last modified: " + getFileStringDate(selectedFile)
        holder.fileNameText.text = selectedFile.name

        if (selectedFile.isDirectory) {
            holder.fileImageView.setImageResource(R.drawable.directory_icon)
        } else {
            val extension = selectedFile.extension
            setIcon(holder, extension)
        }

        holder.itemView.setOnClickListener {
            if (selectedFile.isDirectory) {
                openDirectory(selectedFile)
            } else {
                openFile(selectedFile)
            }
        }

        holder.fileOptionsButton.setOnClickListener {
            val optionsMenu = PopupMenu(context, it)
            optionsMenu.menu.add("Delete")
            optionsMenu.menu.add("Open")
            optionsMenu.menu.add("Rename")
            optionsMenu.menu.add("Share")

            optionsMenu.setOnMenuItemClickListener { item ->
                if (item.title == "Delete") {
                    val deleted = selectedFile.deleteRecursively()
                    if (deleted) {
                        Toast.makeText(
                            context.applicationContext,
                            "DELETED",
                            Toast.LENGTH_SHORT
                        ).show()
                        holder.itemView.visibility = View.GONE
                    }
                }
                if (item.title == "Open") {
                    if (selectedFile.isDirectory) {
                        openDirectory(selectedFile)
                    } else {
                        openFile(selectedFile)
                    }
                }
                if (item.title == "Rename") {
                    Toast.makeText(context.applicationContext, "RENAMED", Toast.LENGTH_SHORT)
                        .show()
                }
                if (item.title == "Share") {
                    Toast.makeText(context.applicationContext, "SHARED", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
            optionsMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileNameText: TextView
        var fileSizeText: TextView
        var fileDateText: TextView
        var fileImageView: ImageView
        var fileOptionsButton: ImageButton

        init {
            fileNameText = itemView.findViewById<TextView>(R.id.file_name_text)
            fileSizeText = itemView.findViewById<TextView>(R.id.file_size_text)
            fileDateText = itemView.findViewById<TextView>(R.id.file_date_text)
            fileImageView = itemView.findViewById<ImageView>(R.id.file_image_view)
            fileOptionsButton = itemView.findViewById<ImageButton>(R.id.file_options_button)
        }
    }

    private fun getFileStringSize(file: File): String{
        val bytes = Files.size(file.toPath()).toDouble()
        val kbytes = bytes / 1024
        val mbytes = kbytes / 1024
        var size = ""
        if (mbytes < 1) {
            size = String.format("%.2f", kbytes) + " kb"
        } else {
            size = String.format("%.2f", mbytes) + " mb"
        }
        return size
    }

    private fun getFileStringDate(file: File): String{
        val date = Date(file.lastModified())
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return format.format(date)
    }

    private fun setIcon(holder: ViewHolder, extension: String) {
        when (extension) {
            "apk" -> holder.fileImageView.setImageResource(R.drawable.apk_icon)
            "avi" -> holder.fileImageView.setImageResource(R.drawable.avi_icon)
            "bmp" -> holder.fileImageView.setImageResource(R.drawable.bmp_icon)
            "docx" -> holder.fileImageView.setImageResource(R.drawable.docx_icon)
            "exe" -> holder.fileImageView.setImageResource(R.drawable.exe_icon)
            "gif" -> holder.fileImageView.setImageResource(R.drawable.gif_icon)
            "iso" -> holder.fileImageView.setImageResource(R.drawable.iso_icon)
            "jpg" -> holder.fileImageView.setImageResource(R.drawable.jpg_icon)
            "mov" -> holder.fileImageView.setImageResource(R.drawable.mov_icon)
            "mp3" -> holder.fileImageView.setImageResource(R.drawable.mp3_icon)
            "mp4" -> holder.fileImageView.setImageResource(R.drawable.mp4_icon)
            "pdf" -> holder.fileImageView.setImageResource(R.drawable.pdf_icon)
            "png" -> holder.fileImageView.setImageResource(R.drawable.png_icon)
            "rar" -> holder.fileImageView.setImageResource(R.drawable.rar_icon)
            "svg" -> holder.fileImageView.setImageResource(R.drawable.svg_icon)
            "txt" -> holder.fileImageView.setImageResource(R.drawable.txt_icon)
            "wav" -> holder.fileImageView.setImageResource(R.drawable.wav_icon)
            "zip" -> holder.fileImageView.setImageResource(R.drawable.zip_icon)
            else -> holder.fileImageView.setImageResource(R.drawable.file_icon)
        }
    }

    private fun openDirectory(directory: File) {
        val intent = Intent(context, FileManagerActivity::class.java)
        val path = directory.absolutePath
        intent.putExtra("path", path)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun openFile(file: File) {
        try {
            val extension = file.extension
            var type = "*/*"
            type = when (extension) {
                "jpeg" -> "image/*"
                "jpg" -> "image/*"
                "png" -> "image/*"
                "gif" -> "image/*"
                "mp3" -> "audio/*"
                "mp4" -> "video/*"
                "mkv" -> "video/*"
                else -> "*/*"
            }
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.parse(file.absolutePath), type)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context.applicationContext,
                "Cannot open the file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
package com.example.vkfilemanagertestapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class Adapter(var context: Context, var files: Array<File>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedFile = files[position]
        holder.textView.text = selectedFile.name
        if (selectedFile.isDirectory) {
            holder.imageView.setImageResource(R.drawable.ic_launcher_background)
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
        holder.itemView.setOnClickListener {
            if (selectedFile.isDirectory) {
                val intent = Intent(context, FileManagerActivity::class.java)
                val path = selectedFile.absolutePath
                intent.putExtra("path", path)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } else {
                //open thte file
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    val type = "image/*"
                    intent.setDataAndType(Uri.parse(selectedFile.absolutePath), type)
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
        holder.itemView.setOnLongClickListener { v ->
            val popupMenu = PopupMenu(context, v)
            popupMenu.menu.add("DELETE")
            popupMenu.menu.add("MOVE")
            popupMenu.menu.add("RENAME")
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.title == "DELETE") {
                    val deleted = selectedFile.delete()
                    if (deleted) {
                        Toast.makeText(
                            context.applicationContext,
                            "DELETED ",
                            Toast.LENGTH_SHORT
                        ).show()
                        v.visibility = View.GONE
                    }
                }
                if (item.title == "MOVE") {
                    Toast.makeText(context.applicationContext, "MOVED ", Toast.LENGTH_SHORT)
                        .show()
                }
                if (item.title == "RENAME") {
                    Toast.makeText(context.applicationContext, "RENAME ", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
            popupMenu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var imageView: ImageView

        init {
            textView = itemView.findViewById<TextView>(R.id.file_name_text_view)
            imageView = itemView.findViewById<ImageView>(R.id.icon_view)
        }
    }
}
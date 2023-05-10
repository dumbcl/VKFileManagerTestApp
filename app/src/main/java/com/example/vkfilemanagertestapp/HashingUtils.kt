package com.example.vkfilemanagertestapp

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

fun getFileHash(file: File): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val inputStream = FileInputStream(file)
    val buffer = ByteArray(4096)
    var read: Int
    while (inputStream.read(buffer).also { read = it } != -1) {
        digest.update(buffer, 0, read)
    }
    inputStream.close()
    return bytesToHex(digest.digest())
}

private fun bytesToHex(bytes: ByteArray): String {
    val hexArray = "0123456789ABCDEF".toCharArray()
    val hexChars = CharArray(bytes.size * 2)
    for (i in bytes.indices) {
        var v:Int=bytes[i].toInt() and 0xFF;
        hexChars[i * 2] = (hexArray[v shr 4]);
        hexChars[i * 2 + 1] = (hexArray[v and 0x0F]);
    }
    return String(hexChars);
}
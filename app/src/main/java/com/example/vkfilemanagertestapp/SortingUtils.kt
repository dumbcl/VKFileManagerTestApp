package com.example.vkfilemanagertestapp

import java.io.File
import java.nio.file.Files

enum class SortType{
    SIZE_ASC, SIZE_DESC, DATE_DESC, DATE_ASC, EXT_DESC, EXT_ASC, NAME_DESC, NAME_ASC
}

fun sortBySizeDesc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> Files.size(f2.toPath()).toInt() - Files.size(f1.toPath()).toInt() })
    return files
}

fun sortBySizeAsc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> Files.size(f1.toPath()).toInt() - Files.size(f2.toPath()).toInt() })
    return files
}

fun sortByDateDesc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> f2.lastModified().toInt() - f1.lastModified().toInt() })
    return files
}

fun sortByDateAsc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> f1.lastModified().toInt() - f2.lastModified().toInt() })
    return files
}

fun sortByExtDesc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> f2.extension.compareTo(f1.extension) })
    return files
}

fun sortByExtAsc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> f1.extension.compareTo(f2.extension) })
    return files
}

fun sortByNameDesc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> f2.name.compareTo(f1.name) })
    return files
}

fun sortByNameAsc(files: Array<File>): Array<File> {
    files.sortWith(Comparator { f1: File, f2: File -> f1.name.compareTo(f2.name) })
    return files
}
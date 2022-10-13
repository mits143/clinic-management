package com.clinic.management.util

import android.widget.TextView


fun TextView.setDate(date: String) {
    val parts: List<String> = date.split("-")
    val part1 = parts[1]
    val part2 = parts[2]
    var date = ""
    if (part1 == "01")
        date = "$part2\nJan"
    if (part1 == "02")
        date = "$part2\nFeb"
    if (part1 == "03")
        date = "$part2\nMar"
    if (part1 == "04")
        date = "$part2\nApr"
    if (part1 == "05")
        date = "$part2\nMay"
    if (part1 == "06")
        date = "$part2\nJun"
    if (part1 == "07")
        date = "$part2\nJul"
    if (part1 == "08")
        date = "$part2\nAug"
    if (part1 == "09")
        date = "$part2\nSept"
    if (part1 == "10")
        date = "$part2\nOct"
    if (part1 == "11")
        date = "$part2\nNov"
    if (part1 == "12")
        date = "$part2\nDec"
    text = date
}

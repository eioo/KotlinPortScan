package com.eioo.portscan

fun extractSingleInt(s: String): ArrayList<Int> {
    val items = ArrayList<Int>()

    try {
        val i = s.toInt()
        items.add(i)
    } catch (e: NumberFormatException) {
    }

    return items
}

fun extractSeperatedInts(s: String, delimiter: String): ArrayList<Int> {
    val items = ArrayList<Int>()
    val splitted = s.split(delimiter)

    for (item in splitted) {
        try {
            val i = item.toInt()
            items.add(i)
        } catch (e: NumberFormatException) {
        }
    }

    return items
}

fun extractRangeInts(s: String): ArrayList<Int> {
    val items = ArrayList<Int>()
    val splitted = s.split("-")

    if (splitted.size == 2) {
        try {
            val start = splitted[0].toInt()
            val stop = splitted[1].toInt()

            if (start <= stop) {
                for (i in start..stop) {
                    items.add(i)
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

    return items
}
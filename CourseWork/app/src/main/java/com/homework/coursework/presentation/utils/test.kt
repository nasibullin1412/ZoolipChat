package com.homework.coursework.presentation.utils

fun getNewRecycleList(
    oldList: List<Int>,
    newList: List<Int>
): List<Int> {
    val firstElemOfNew = newList.last()
    val idx = oldList.indexOfFirst { it == firstElemOfNew }
    if (idx == -1) {
        return newList + oldList
    }
    val resultList: MutableList<Int> = oldList.toMutableList()
    val (untilIdx, remain) = getUntilIdx(
        idx = idx,
        newSize = newList.size
    )
    for (i in idx downTo untilIdx ) {
        val newIdx = newList.lastIndex - idx + i
        resultList[i] = newList[newIdx]
    }
    for (i in remain-1 downTo 0) {
        resultList.add(0, newList[i])
    }
    return resultList
}

fun getUntilIdx(idx: Int, newSize: Int): Pair<Int, Int> {
    val remainElemNumber = idx - newSize + 1
    return if (remainElemNumber >= 0) {
        Pair(remainElemNumber, 0)
    } else {
        Pair(0, -remainElemNumber)
    }
}

fun test1(){
    val oldList = listOf(4, 3, 2, 1)
    val newList = listOf(5, 3)
    println("Test 1: ${getNewRecycleList(oldList, newList) == listOf(5, 3, 2, 1)}")
    println(getNewRecycleList(oldList, newList))
}

fun test2(){
    val oldList = listOf(4, 3, 2, 1)
    val newList = listOf(5, 2)
    println("Test 2: ${getNewRecycleList(oldList, newList) == listOf(4, 5, 2, 1)}")
    println(getNewRecycleList(oldList, newList))
}

fun test3(){
    val oldList = listOf(4, 3, 2, 1)
    val newList = listOf(5, 1)
    println("Test 3: ${getNewRecycleList(oldList, newList) == listOf(4, 3, 5, 1)}")
    println(getNewRecycleList(oldList, newList))
}

fun test4(){
    val oldList = listOf(4, 3, 2, 1)
    val newList = listOf(5, 4)
    println("Test 4: ${getNewRecycleList(oldList, newList) == listOf(5, 4, 3, 2, 1)}")
    println(getNewRecycleList(oldList, newList))
}

fun test5(){
    val oldList = listOf(4, 3, 2, 1)
    val newList = listOf(6, 5)
    println("Test 5: ${getNewRecycleList(oldList, newList) == listOf(6, 5, 4, 3, 2, 1)}")
    println(getNewRecycleList(oldList, newList))
}

fun test6(){
    val oldList = listOf(4, 3, 2, 1)
    val newList = listOf(6, 5, 4)
    println("Test 6: ${getNewRecycleList(oldList, newList) == listOf(6, 5, 4, 3, 2, 1)}")
    println(getNewRecycleList(oldList, newList))
}

fun main() {
    test1()
    test2()
    test3()
    test4()
    test5()
    test6()
}
package com.example.tictactoe


class DetermineWinner {
    var gameWinner = -1

    fun evaluateHorizontalWin(p: ArrayList<Int>, c: ArrayList<Int>) {
        val list1 = listOf(1, 2, 3)
        val list2 = listOf(4 ,5 ,6)
        val list3 = listOf(7 ,8 ,9)
        when {
            checkListContainsValues(p, list1, list2, list3) -> gameWinner = 1
            checkListContainsValues(c, list1, list2, list3) -> gameWinner = 2
        }
    }

    fun evaluateVerticalWin(p: ArrayList<Int>, c: ArrayList<Int>) {
        val list1 = listOf(1, 4, 7)
        val list2 = listOf(2 ,5 ,8)
        val list3 = listOf(3 ,6 ,9)
        when {
            checkListContainsValues(p, list1, list2, list3) -> gameWinner = 1
            checkListContainsValues(c, list1, list2, list3) -> gameWinner = 2
        }
    }

    fun evaluateDiagonalWin(p: ArrayList<Int>, c: ArrayList<Int>) {
        val list1 = listOf(1, 5, 9)
        val list2 = listOf(3 ,5 ,7)
        when {
            checkListContainsValues(p, list1, list2) -> gameWinner = 1
            checkListContainsValues(c, list1, list2) -> gameWinner = 2
        }
    }

    private fun checkListContainsValues(optionsList: ArrayList<Int>, vararg winningNumbers:
    List<Int>): Boolean {
        winningNumbers.forEach { action ->
            when {
                optionsList.containsAll(action) -> return true
            }
        }
        return false
    }
}
package com.example.tictactoe

import android.content.Context
import android.widget.Toast

class DetermineWinner(private val context: Context) {
    var humanPlayer = ArrayList<Int>()
    var computerPlayer = ArrayList<Int>()
    private var gameWinner = -1

    fun determineWinner() {
        evaluateHorizontalWin()
        evaluateVerticalWin()
        evaluateDiagonalWin()
        when (gameWinner) {
            1 -> {
                Toast.makeText(
                    context, "You Have Won, Congratulations",
                    Toast.LENGTH_SHORT
                ).show()
            }
            2 -> {
                Toast.makeText(context, "You Lost",
                    Toast.LENGTH_SHORT).show()
            }else -> {
            Toast.makeText(context, "No Winner, Continue Playing", Toast.LENGTH_SHORT).show()
        }
        }
    }

    private fun evaluateHorizontalWin() {
        when {
            humanPlayer.contains(1) && humanPlayer.contains(2) && humanPlayer.contains(3) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(1) && computerPlayer.contains(2) && computerPlayer
                .contains(3) -> gameWinner = 2
        }
        when {
            humanPlayer.contains(4) && humanPlayer.contains(5) && humanPlayer.contains(6) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(4) && computerPlayer.contains(5) && computerPlayer
                .contains(6) -> gameWinner = 2
        }
        when {
            humanPlayer.contains(7) && humanPlayer.contains(8) && humanPlayer.contains(9) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(7) && computerPlayer.contains(8) && computerPlayer
                .contains(9) -> gameWinner = 2
        }

    }

    private fun evaluateVerticalWin() {
        when {
            humanPlayer.contains(1) && humanPlayer.contains(4) && humanPlayer.contains(7) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(1) && computerPlayer.contains(4) && computerPlayer
                .contains(7) -> gameWinner = 2
        }
        when {
            humanPlayer.contains(2) && humanPlayer.contains(5) && humanPlayer.contains(8) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(2) && computerPlayer.contains(5) && computerPlayer
                .contains(8) -> gameWinner = 2
        }
        when {
            humanPlayer.contains(3) && humanPlayer.contains(6) && humanPlayer.contains(9) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(3) && computerPlayer.contains(6) && computerPlayer
                .contains(9) -> gameWinner = 2
        }

    }

    private fun evaluateDiagonalWin() {
        when {
            humanPlayer.contains(1) && humanPlayer.contains(5) && humanPlayer.contains(9) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(1) && computerPlayer.contains(5) && computerPlayer
                .contains(9) -> gameWinner = 2
        }
        when {
            humanPlayer.contains(3) && humanPlayer.contains(5) && humanPlayer.contains(7) ->
                gameWinner = 1
        }
        when {
            computerPlayer.contains(3) && computerPlayer.contains(5) && computerPlayer
                .contains(7) -> gameWinner = 2
        }

    }


}
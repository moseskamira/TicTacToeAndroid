package com.example.tictactoe
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val boardButtonsArray = Array<Array<Button?>>(3) {
        arrayOfNulls(3)
    }
    private var dynamicallyAssignedButtonId: String? = null
    private val determineWinner = DetermineWinner()
    private var clickedButton: Button? = null
    private var selectedCellId = 0
    private var humanPlayerTurn = true
    private var computerRandomIndex = 0
    companion object{
        var humanPlayer = ArrayList<Int>()
        var computerPlayer = ArrayList<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (m in 0..2) {
            for (n in 0..2) {
                dynamicallyAssignedButtonId = "button_$m$n"
                    val resourceId = resources.getIdentifier(dynamicallyAssignedButtonId,
                        "id", packageName)
                    boardButtonsArray[m][n] = findViewById(resourceId)
                    boardButtonsArray[m][n]?.setOnClickListener {view->
                        onButtonClick(view)
                    }
            }
        }
        val resetButton = reset_button_id
        resetButton.setOnClickListener {
            onResetButtonClick()
        }
    }

    private fun onButtonClick(view: View) {
        clickedButton = view as Button
        when(clickedButton?.id) {
            R.id.button_00->selectedCellId = 1
            R.id.button_01->selectedCellId = 2
            R.id.button_02->selectedCellId = 3
            R.id.button_10->selectedCellId = 4
            R.id.button_11->selectedCellId = 5
            R.id.button_12->selectedCellId = 6
            R.id.button_20->selectedCellId = 7
            R.id.button_21->selectedCellId = 8
            R.id.button_22->selectedCellId = 9
        }
        playGameByHuman(selectedCellId, clickedButton!!)
    }

    private fun playGameByHuman(selectedCellId: Int, clickedButton: Button) {
        when {
            humanPlayerTurn -> {
                clickedButton.text = "P"
                clickedButton.setBackgroundColor(Color.parseColor("#009688"))
                humanPlayer.add(selectedCellId)
                humanPlayerTurn = false
                playGameByComputer()
            }
            else -> {
                clickedButton.text = "C"
                clickedButton.setBackgroundColor(Color.parseColor("#03A9F4"))
                computerPlayer.add(selectedCellId)
                humanPlayerTurn = true
            }
        }
        clickedButton.isEnabled = false
        getWinner()
    }

    private fun playGameByComputer() {
        val selectedCell = ArrayList<Int>()
        for (selectedCellId in 1..9) {
            when {
                !(humanPlayer.contains(selectedCellId) ||
                        computerPlayer
                            .contains(selectedCellId)) -> selectedCell.add(selectedCellId)
            }
        }
        val randomInstance = Random()
        if (selectedCell.size > 0) {
            computerRandomIndex = randomInstance.nextInt(selectedCell.size-0)+0
            selectedCellId = selectedCell[computerRandomIndex]
            clickedButton = when(selectedCellId) {
                1-> findViewById(R.id.button_00)
                2-> findViewById(R.id.button_01)
                3-> findViewById(R.id.button_02)
                4-> findViewById(R.id.button_10)
                5-> findViewById(R.id.button_11)
                6-> findViewById(R.id.button_12)
                7-> findViewById(R.id.button_20)
                8-> findViewById(R.id.button_21)
                9-> findViewById(R.id.button_22)
                else-> findViewById(R.id.button_00)
            }
            playGameByHuman(selectedCellId, clickedButton!!)
            clickedButton?.isEnabled = false
        }
    }

    private fun getWinner() {
        determineWinner.evaluateHorizontalWin(humanPlayer, computerPlayer)
        determineWinner.evaluateVerticalWin(humanPlayer, computerPlayer)
        determineWinner.evaluateDiagonalWin(humanPlayer, computerPlayer)
        when (determineWinner.gameWinner) {
            1 -> {
                Toast.makeText(this, "You Have Won, Congratulations",
                    Toast.LENGTH_SHORT
                ).show()
            }
            2 -> {
                Toast.makeText(this, "You Lost",
                    Toast.LENGTH_SHORT).show()
            }else -> {
            Toast.makeText(this, "No Winner, Continue Playing", Toast.LENGTH_SHORT)
                .show()
        }
        }
    }

    private fun onResetButtonClick() {
        for (m in 0..2) {
            for (n in 0..2) {
                boardButtonsArray[m][n]?.text = ""
                boardButtonsArray[m][n]?.isEnabled = true
                boardButtonsArray[m][n]?.setBackgroundColor(Color.LTGRAY)
            }
        }
        humanPlayerTurn = true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean("humanPlayerTurn", humanPlayerTurn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        humanPlayerTurn= savedInstanceState!!.getBoolean("humanPlayerTurn")
    }
}

package com.example.tictactoe
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val boardButtonsArray = Array<Array<Button?>>(3) {
        arrayOfNulls(3)
    }
    private var dynamicallyAssignedButtonId: String? = null
    private var humanPlayerTurn: Boolean = true
    private var selectedCellId = 0
    private lateinit var clickedButton: Button
    private val determineWinnerInstance = DetermineWinner(this)

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

    private fun onResetButtonClick() {
        resetPlayerBoard()
    }

    private fun onButtonClick(view: View) {
        clickedButton = view as Button
        selectedCellId = 0
        when(clickedButton.id) {
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
        playGameByHuman(selectedCellId, clickedButton)
    }

    private fun playGameByHuman(selectedCellId: Int, clickedButton: Button) {
        when {
            humanPlayerTurn -> {
                clickedButton.text = "X"
                clickedButton.setBackgroundColor(Color.BLUE)
                determineWinnerInstance.humanPlayer.add(selectedCellId)
                humanPlayerTurn = false
                playGameByComputer()
            }
            else -> {
                clickedButton.text = "Y"
                clickedButton.setBackgroundColor(Color.YELLOW)
                determineWinnerInstance.computerPlayer.add(selectedCellId)
                humanPlayerTurn = true
            }
        }
        clickedButton.isEnabled = false
        determineWinnerInstance.determineWinner()
    }

    private fun playGameByComputer() {
        val selectedCell = ArrayList<Int>()
        for (selectedCellId in 1..9) {
            when {
                !(determineWinnerInstance.humanPlayer.contains(selectedCellId) || determineWinnerInstance.computerPlayer
                    .contains(selectedCellId)) -> selectedCell.add(selectedCellId)
            }
        }
        val randomInstance = Random()
        val randomIndex = randomInstance.nextInt(selectedCell.size-0)+0
        selectedCellId = selectedCell[randomIndex]
        clickedButton = when(selectedCellId) {
            1-> button_00
            2-> button_01
            3-> button_02
            4-> button_10
            5-> button_11
            6-> button_12
            7-> button_20
            8-> button_21
            9-> button_22
            else-> button_00
        }
        playGameByHuman(selectedCellId, clickedButton)
        clickedButton.isEnabled = false
    }

    private fun resetPlayerBoard() {
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

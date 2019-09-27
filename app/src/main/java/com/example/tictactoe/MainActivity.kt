package com.example.tictactoe
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val boardButtonsArray = Array<Array<Button?>>(3) {
        arrayOfNulls(3)
    }
    private lateinit var playerPointsTextView: TextView
    private var dynamicallyAssignedButtonId: String? = null
    private lateinit var computerPointsTextView: TextView

    private var playerPoints = 0
    private var computerPoints = 0
    private var roundCount = 0
    var humanPlayer = ArrayList<Int>()
    var computerPlayer = ArrayList<Int>()
    private var humanPlayerTurn: Boolean = true
    private var selectedCellId = 0
    private lateinit var clickedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerPointsTextView = player_id
        computerPointsTextView = computer_id
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
            onRestButtonClick()
        }
    }

    private fun onRestButtonClick() {
        playerPoints = 0
        computerPoints = 0
//        updatePointText()
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
        playGame(selectedCellId, clickedButton)
    }

    private fun playGame(selectedCellId: Int, clickedButton: Button) {
        if (humanPlayerTurn) {
            clickedButton.text = "X"
            clickedButton.setBackgroundColor(Color.BLUE)
            humanPlayer.add(selectedCellId)
            humanPlayerTurn = false
            playGameByComputer()
        }else {
            clickedButton.text = "O"
            clickedButton.setBackgroundColor(Color.YELLOW)
            computerPlayer.add(selectedCellId)
            humanPlayerTurn = true

        }
        clickedButton.isEnabled = false

        determineWinner()
    }
    private fun playGameByComputer() {
        val unSelectedCells = ArrayList<Int>()
        for (unSelectedCellId in 1..9) {
            if (!(humanPlayer.contains(unSelectedCellId) || computerPlayer
                    .contains(unSelectedCellId))) {
                unSelectedCells.add(unSelectedCellId)

            }
        }
        val r = Random()
        val randomIndex = r.nextInt(unSelectedCells.size-0)+0
        val unSelectedCellId = unSelectedCells[randomIndex]
        val selectedButton: Button
        selectedButton = when(unSelectedCellId) {
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
        playGame(unSelectedCellId, selectedButton)


    }
    private fun determineWinner() {
        var gameWinner = -1
        // Performing Horizontal Check/ Rows
        if (humanPlayer.contains(1) && humanPlayer.contains(2) && humanPlayer.contains(3)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(1) && computerPlayer.contains(2) && computerPlayer.contains(3)) {
            gameWinner = 2
        }
        if (humanPlayer.contains(4) && humanPlayer.contains(5) && humanPlayer.contains(6)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(4) && computerPlayer.contains(5) && computerPlayer.contains(6)) {
            gameWinner = 2
        }
        if (humanPlayer.contains(7) && humanPlayer.contains(7) && humanPlayer.contains(9)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(7) && computerPlayer.contains(8) && computerPlayer.contains(9)) {
            gameWinner = 2
        }
        // Performing a Vertical Check/Columns
        if (humanPlayer.contains(1) && humanPlayer.contains(4) && humanPlayer.contains(7)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(1) && computerPlayer.contains(4) && computerPlayer.contains(7)) {
            gameWinner = 2
        }
        if (humanPlayer.contains(2) && humanPlayer.contains(5) && humanPlayer.contains(8)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(2) && computerPlayer.contains(5) && computerPlayer.contains(8)) {
            gameWinner = 2
        }
        if (humanPlayer.contains(3) && humanPlayer.contains(6) && humanPlayer.contains(9)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(3) && computerPlayer.contains(6) && computerPlayer.contains(9)) {
            gameWinner = 2
        }
        // Performing a Diagonal Check

        if (humanPlayer.contains(1) && humanPlayer.contains(5) && humanPlayer.contains(9)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(1) && computerPlayer.contains(5) && computerPlayer.contains(9)) {
            gameWinner = 2
        }
        if (humanPlayer.contains(3) && humanPlayer.contains(5) && humanPlayer.contains(7)) {
            gameWinner = 1
        }
        if (computerPlayer.contains(3) && computerPlayer.contains(5) && computerPlayer.contains(7)) {
            gameWinner = 2
        }

        if(gameWinner != -1) {
            if (gameWinner == 1) {
                playerPoints++
                Toast.makeText(this, "You Have Won and Computer Lost", Toast.LENGTH_SHORT).show()
                updatePointText()


            }else {
                computerPoints++
                Toast.makeText(this, "You Lost and Computer Won", Toast.LENGTH_SHORT).show()
                updatePointText()

            }
        }
    }

    private fun goneDraw() {
        Toast.makeText(this, "YOU WENT DRAW", Toast.LENGTH_SHORT).show()
        resetPlayerBoard()
    }

//    private fun computerWon() {
//        computerPoints++
//        Toast.makeText(this, "COMPUTER WON!", Toast.LENGTH_SHORT).show()
//        updatePointText()
//        resetPlayerBoard()
//    }

    private fun resetPlayerBoard() {
        for (m in 0..2) {
            for (n in 0..2) {
                boardButtonsArray[m][n]?.text = ""
            }
        }
        roundCount = 0
        humanPlayerTurn = true
    }

    private fun updatePointText() {
        playerPointsTextView.text = "${getString(R.string.player)}$playerPoints"
        computerPointsTextView.text = "${getString(R.string.Computer)}$computerPoints"
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt("playerPoints", playerPoints)
        outState.putInt("computerPoints", computerPoints)
        outState.putInt("roundCount", roundCount)
        outState.putBoolean("humanPlayerTurn", humanPlayerTurn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        playerPoints = savedInstanceState!!.getInt("playerPoints")
        computerPoints = savedInstanceState.getInt("computerPoints")
        roundCount = savedInstanceState.getInt("roundCount")
        humanPlayerTurn= savedInstanceState.getBoolean("humanPlayerTurn")
    }
}

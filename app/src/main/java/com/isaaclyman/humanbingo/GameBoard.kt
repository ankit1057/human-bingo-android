package com.isaaclyman.humanbingo

import android.content.Context
import android.support.v7.widget.GridLayout
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*

class GameBoard {
    private var context: Context
    private var board: GridLayout
    private val peopleSquares = PeopleSquares()
    private var peopleIndexes: List<Int>? = null

    constructor(context: Context, board: GridLayout, mode: GameMode?, code: String?) {
        this.context = context
        this.board = board
        if (mode != null) {
            // Create new game
            newBoard(mode.value)
        }
    }

    fun newBoard(size: Int) {
        board.removeAllViews()
        val squares = size * size
        val people = getRandomPeople(squares)
        createBoard(context, board, size, people)
    }

    fun getCode(): String {
        return peopleIndexes?.joinToString("-") ?: ""
    }

    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    private fun createBoard(context: Context, board: GridLayout, size: Int, people: List<String>) {
        board.columnCount = size
        board.rowCount = size
        var iterator = 0

        for(col in 0 until size) {
            for (row in 0 until size) {
                val cell = RelativeLayout(context)
                val cellSpec = { GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f) }
                val params = GridLayout.LayoutParams(cellSpec(), cellSpec())
                params.width = 0
                cell.layoutParams = params
                cell.setBackgroundResource(R.drawable.bordered_rectangle)
                cell.gravity = Gravity.CENTER
                cell.setPadding(10, 4, 10, 4)

                val text = TextView(context)
                text.text = people[iterator++]
                text.maxLines = 5
                text.setSingleLine(false)
                text.gravity = Gravity.CENTER

                cell.addView(text)
                board.addView(cell)
            }
        }
    }

    private fun getRandomPeople(num: Int): List<String> {
        var validIndexes = (0..peopleSquares.squares.lastIndex).toMutableList()
        var chosenIndexes = mutableListOf<Int>()
        var randomPeople = mutableListOf<String>()
        for (i in 1..num) {
            val validIndexIndex = (0..validIndexes.lastIndex).random()
            val peopleIndex = validIndexes[validIndexIndex]
            chosenIndexes.add(peopleIndex)
            val person = peopleSquares.squares[peopleIndex]
            randomPeople.add(person)
            validIndexes.remove(peopleIndex)
        }
        peopleIndexes = chosenIndexes.toList()
        return randomPeople.toList()
    }
}
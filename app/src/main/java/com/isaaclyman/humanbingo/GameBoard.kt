package com.isaaclyman.humanbingo

import android.content.Context
import android.support.v7.widget.GridLayout
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class GameBoard {
    val peopleSquares = PeopleSquares()

    constructor(context: Context, board: GridLayout, mode: GameMode?, code: String?) {
        if (mode != null) {
            // Create new game
            val size = mode.value
            val squares = size * size
            val people = getRandomPeople(squares)
            createBoard(context, board, size, people)
        }
    }

    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    private fun createBoard(context: Context, board: GridLayout, size: Int, people: List<String>) {
        board.columnCount = size
        board.rowCount = size
        var iterator = 0

        for(col in 0 until size) {
            for (row in 0 until size) {
                val text = TextView(context)
                text.text = people[iterator++]
                text.maxLines = 4
                text.setSingleLine(false)

                val currentRow = { GridLayout.spec(GridLayout.UNDEFINED, GridLayout.START, 1f) }
                val currentCol = { GridLayout.spec(GridLayout.UNDEFINED, GridLayout.START, 1f) }
                val params = GridLayout.LayoutParams(currentRow(), currentCol())
                text.layoutParams = params
                text.gravity = Gravity.FILL
                board.addView(text)
            }
        }
    }

    private fun getRandomPeople(num: Int): List<String> {
        var validIndexes = (0..peopleSquares.squares.lastIndex).toMutableList()
        var randomPeople = mutableListOf<String>()
        for (i in 1..num) {
            val validIndex = (0..validIndexes.lastIndex).random()
            val peopleIndex = validIndexes[validIndex]
            val person = peopleSquares.squares[peopleIndex]
            randomPeople.add(person)
            validIndexes.remove(validIndex)
        }
        return randomPeople.toList()
    }
}
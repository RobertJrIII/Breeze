package com.the3rdwheel.breeze.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView
import com.the3rdwheel.breeze.ui.viewholders.PostViewHolder


const val DOWN_VOTE_COLOR = "#9494FF"
const val UP_VOTE_COLOR = "#FF8b60"
const val SAVE = "#FFFF33"
const val MORE_OPTIONS = "#C0C0C0"

class Gestures(swipeDirs: Int) : ItemTouchHelper.SimpleCallback(ACTION_STATE_IDLE, swipeDirs) {

//    private var swipeBack = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //TODO add voting, saving, etc. here
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        if (viewHolder is PostViewHolder) {
            return super.getSwipeDirs(recyclerView, viewHolder)
        }
        return ACTION_STATE_IDLE


    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
//        recyclerView.setOnTouchListener { v, event ->
//            swipeBack =
//                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
//            false
//        }

        val itemView = viewHolder.itemView
        val widthOfSingleLabel = itemView.width / 4
        val width = itemView.width

        if (dX < widthOfSingleLabel && dX > 0) {

            setBackground(
                Color.parseColor(SAVE),
                itemView.left,
                itemView.top,
                dX.toInt(),
                itemView.bottom, c
            )
        } else if (dX > widthOfSingleLabel && ((dX < width / 2) || (dX > width / 2))) {

            setBackground(
                Color.parseColor(MORE_OPTIONS),
                itemView.left,
                itemView.top,
                dX.toInt(),
                itemView.bottom, c
            )

        } else if (dX < 0 && (width + dX > widthOfSingleLabel * 3)) {

            setBackground(
                Color.parseColor(UP_VOTE_COLOR),
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom, c
            )
        } else {
            setBackground(
                Color.parseColor(DOWN_VOTE_COLOR),
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom, c
            )
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    private fun setBackground(
        color: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        canvas: Canvas
    ) {
        val background = ColorDrawable(color)
        background.setBounds(left, top, right, bottom)

        background.draw(canvas)
    }
}
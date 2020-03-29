package com.the3rdwheel.breeze.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView
import com.the3rdwheel.breeze.ui.viewholders.PostViewHolder

const val DOWN_VOTE_COLOR = "#7193FF"
const val UP_VOTE_COLOR = "#FF4500"

class Gestures(swipeDirs: Int) : ItemTouchHelper.SimpleCallback(ACTION_STATE_IDLE, swipeDirs) {


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
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
        val itemView = viewHolder.itemView
        val height = itemView.height
        val widthOfSingleLabel = itemView.width / 4
        val width = itemView.width
        var swipeBackGround: ColorDrawable? = null

        if (dX < widthOfSingleLabel && dX > 0) {
            swipeBackGround = ColorDrawable(Color.parseColor(UP_VOTE_COLOR))
            swipeBackGround.setBounds(
                itemView.left,
                itemView.top,
                dX.toInt(),
                itemView.bottom
            )
        } else if (dX > widthOfSingleLabel && dX < width / 2) {

        } else if (dX < width && dX > widthOfSingleLabel * 3) {
//            swipeBackGround = ColorDrawable(Color.parseColor(UP_VOTE_COLOR))
//            swipeBackGround.setBounds(
//                itemView.left,
//                itemView.top,
//                dX.toInt(),
//                itemView.bottom
//            )




        } else {

        }
        swipeBackGround?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
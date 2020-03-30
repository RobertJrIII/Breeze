package com.the3rdwheel.breeze.gestures

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView
import com.the3rdwheel.breeze.ui.viewholders.PostViewHolder


const val LEFT_AND_RIGHT = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
const val RIGHT = ItemTouchHelper.RIGHT
const val LEFT = ItemTouchHelper.LEFT

class SimpleSwipe(
    swipeDirs: Int,
    private val items: List<SwipeItems>,
    recyclerView: RecyclerView
) :
    ItemTouchHelper.SimpleCallback(ACTION_STATE_IDLE, swipeDirs) {
    private val colorDrawable: ColorDrawable = ColorDrawable()

    init {

        ItemTouchHelper(this).attachToRecyclerView(recyclerView)
    }



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


        val itemView = viewHolder.itemView
        val widthOfSingleLabel = itemView.width / 4
        val width = itemView.width

        if (dX < widthOfSingleLabel && dX > 0) {

            setBackground(
                items[0].color,
                itemView.left,
                itemView.top,
                dX.toInt(),
                itemView.bottom, c
            )
        } else if (dX > widthOfSingleLabel && ((dX < width / 2) || (dX > width / 2))) {

            setBackground(
                items[1].color,
                itemView.left,
                itemView.top,
                dX.toInt(),
                itemView.bottom, c
            )

        } else if (dX < 0 && (width + dX > widthOfSingleLabel * 3)) {

            setBackground(
                items[3].color,
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom, c
            )
        } else {
            setBackground(
                items[2].color,
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
        colorDrawable.color = color
        colorDrawable.setBounds(left, top, right, bottom)

        colorDrawable.draw(canvas)
    }
}
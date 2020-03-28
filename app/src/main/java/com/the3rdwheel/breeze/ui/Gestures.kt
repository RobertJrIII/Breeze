package com.the3rdwheel.breeze.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView
import com.the3rdwheel.breeze.ui.viewholders.PostViewHolder


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


}
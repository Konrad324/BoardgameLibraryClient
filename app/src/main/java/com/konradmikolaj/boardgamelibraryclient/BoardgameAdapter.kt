package com.konradmikolaj.boardgamelibraryclient

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konradmikolaj.boardgamelibraryclient.model.BoardGame
import kotlinx.android.synthetic.main.boardgame_list_item.view.*

class BoardgameAdapter(val items : ArrayList<BoardGame>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.boardgame_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = items.get(position).title
        holder.itemView.setOnClickListener { u ->
            val detailActivity = GameDetailActivity.newIntent(u.context, items.get(position))
            u.context.startActivity(detailActivity)
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val title = view.listBoardgameTitle
}
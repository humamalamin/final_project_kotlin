package com.humam.mobile.finalprojectkotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.humam.mobile.finalprojectkotlin.model.PlayerItem
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerAdapter (private val items: MutableList<PlayerItem>,
private val clickListener: (PlayerItem) -> Unit) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    companion object {
        private const val ID_IMAGE = 1
        private const val ID_NAME = 2
        private const val ID_POSISION = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val playerImage: ImageView = view.findViewById(ID_IMAGE)
        private val playerName: TextView = view.findViewById(ID_NAME)
        private val playerPosition: TextView = view.findViewById(ID_POSISION)

        fun bind(item: PlayerItem, clickListener: (PlayerItem) -> Unit) {
            Picasso.get()
                    .load(item.strCutout)
                    .into(playerImage)

            playerName.text = item.strPlayer
            playerPosition.text = item.strPosition

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    inner class ItemUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    padding = dip(16)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        imageView {
                            id = ID_IMAGE
                        }.lparams(dip(50), dip(50))

                        textView {
                            id = ID_NAME
                        }.lparams(wrapContent, wrapContent) {
                            leftMargin = dip(16)
                        }
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        id = ID_POSISION
                        gravity = Gravity.END
                    }.lparams(matchParent, wrapContent, 1f)
                }
            }
        }
    }
}
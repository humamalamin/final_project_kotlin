package com.humam.mobile.finalprojectkotlin.view.playerdetail

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.model.PlayerItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.startActivity

class PlayerDetail : AppCompatActivity() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, player: PlayerItem) {
            context?.startActivity<PlayerDetail>(EXTRA_PARAM to player)
        }
    }

    private lateinit var player: PlayerItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        setupData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            android.R.id.home -> {
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupData(){
        player = intent.getParcelableExtra(EXTRA_PARAM)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = player.strPlayer

        loadData()
    }

    private fun loadData(){
        Picasso.get()
                .load(player.strFanart1)
                .into(iv_player)

        tv_weight.text = player.strWeight
        tv_height.text = player.strHeight
        tv_posisi.text = player.strPosition
        tv_deskripsi.text = player.strDescriptionEN
    }
}

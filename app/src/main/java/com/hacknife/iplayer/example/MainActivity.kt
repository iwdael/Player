package com.hacknife.iplayer.example

import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.hacknife.player.IPlayer.Companion.thumbnailLoader
import com.hacknife.player.ThumbnailLoader
import com.hacknife.player.Url
import com.hacknife.player.compat.openFullIPlayer
import com.hacknife.player.compat.openWindowIPlayer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)
        thumbnailLoader = object : ThumbnailLoader {
            override fun load(view: ImageView, url: String) {
                view.setBackgroundColor(Color.BLUE)
            }
        }
        val url = Url
            .Builder()
            .dataSource(assets.openFd("source.mp4"))
            .title("源文件")
            .build()
        player.setDataSource(url)
    }

    fun onWindowClick(view: View) {
        val url = Url.Builder()
            .dataSource(assets.openFd("source.mp4"))
            .title("源文件")
            .build()
        openWindowIPlayer(url)
    }

    fun onFullClick(view: View) {
        val url = Url.Builder()
            .dataSource(assets.openFd("source.mp4"))
            .title("源文件")
            .build()
        openFullIPlayer(url)
    }
}
package com.shengj.glidepaletteblur.app

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.shengj.glidepaletteblur.*
import com.shengj.glidepaletteblur.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private fun withPaletteBg(view: ImageView, image: String) {
        Glide.with(view)
            .load(image)
            .listener(GlidePalette().generate { palette ->
                palette?.darkColor()?.let { paletteColor ->
                    view.ofTypeParent<ConstraintLayout>()?.setBackgroundColor(paletteColor)
                }
            })
            .into(view)
    }

    private fun withBlurBg(view: ImageView, image: String) {
        Glide.with(view)
            .load(image)
            .listener(GlideBlur().generate { bitmap ->
                view.ofTypeParent<ConstraintLayout>()?.background =
                    BitmapDrawable(resources, bitmap)
            })
            .into(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        withPaletteBg(binding.iv1, avifs[0])
        withBlurBg(binding.iv2, avifs[1])

        withPaletteBg(binding.iv3, webps[0])
        withBlurBg(binding.iv4, webps[1])

        withPaletteBg(binding.iv5, jpegs[0])
        withBlurBg(binding.iv6, jpegs[1])

        withPaletteBg(binding.iv7, gifs[0])
        withBlurBg(binding.iv8, gifs[1])
    }

    private val avifs = listOf(
        "https://images.unsplash.com/photo-1519677704001-6d410c3ef07e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YWR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1551383616-a9e150c07fca?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YWR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1569961384048-0001b31bb6f7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8YWR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
    )

    private val webps = listOf(
        "https://cdn.pixabay.com/photo/2022/08/03/04/41/beech-leaves-7361863__340.jpg",
        "https://cdn.pixabay.com/photo/2022/09/05/09/50/tomatoes-7433786__340.jpg",
        "https://cdn.pixabay.com/photo/2022/08/15/12/13/robot-7387740__340.jpg"
    )

    private val jpegs = listOf(
        "https://images.pexels.com/photos/5611254/pexels-photo-5611254.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://images.pexels.com/photos/4841447/pexels-photo-4841447.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://images.pexels.com/photos/34231/antler-antler-carrier-fallow-deer-hirsch.jpg?auto=compress&cs=tinysrgb&w=600"
    )

    private val gifs = listOf(
        "https://media1.giphy.com/media/uMrxJOS6xbsgCJvZnO/giphy.gif?cid=ecf05e47c861c7df6000a96ef95c1f2ca38530ca83d2a181&rid=giphy.gif&ct=g",
        "https://media1.giphy.com/media/ENcROyB1aZIk4KchRS/giphy.gif?cid=ecf05e472d6ad75d52b4642b4f1441e0699b554afb4add83&rid=giphy.gif&ct=g",
        "https://media4.giphy.com/media/sNcG8zOLxIwMgKGiMh/giphy.gif?cid=ecf05e479c6828a7c8ef584f0d3f792de57ed01e5650eec6&rid=giphy.gif&ct=g"
    )

}
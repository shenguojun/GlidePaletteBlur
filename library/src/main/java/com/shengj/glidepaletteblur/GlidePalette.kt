package com.shengj.glidepaletteblur

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.ref.WeakReference

/**
 * 配合Glide返回[Palette]取色对象
 *
 * Glide the [Palette] object from Glide
 *
 * @author shengj
 */
class GlidePalette : RequestListener<Drawable> {
    private var callback: GlidePaletteCallback? = null

    /**
     * 获取Glide加载的Palette结果
     *
     * Pass a [GlidePaletteCallback] to receive the [Palette] result from Glide Listener
     */
    fun generate(callback: GlidePaletteCallback): GlidePalette {
        this.callback = callback
        return this
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        callback?.invoke(null)
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        when (resource) {
            is BitmapDrawable -> {
                resource.bitmap
            }
            is GifDrawable -> {
                resource.firstFrame
            }
            else -> null
        }?.let {
            val callbackRef = WeakReference(callback)
            Palette.from(it).generate { palette ->
                callbackRef.get()?.invoke(palette)
            }
        }
        return false
    }
}

/**
 * Glide获取[Palette]回调
 *
 * The callback to get the [Palette] result
 */
typealias GlidePaletteCallback = (Palette?) -> Unit

/**
 * 获取[Palette]浅色色值
 *
 * Get light color from [Palette]
 */
fun Palette.lightColor(): Int? {
    return lightVibrantSwatch?.rgb
        ?: lightMutedSwatch?.rgb
        ?: vibrantSwatch?.rgb
}

/**
 * 获取[Palette]深色色值
 *
 * Get dark color from [Palette]
 */
fun Palette.darkColor(): Int? {
    return darkMutedSwatch?.rgb
        ?: darkVibrantSwatch?.rgb
        ?: mutedSwatch?.rgb
}
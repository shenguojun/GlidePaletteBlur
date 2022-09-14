package com.shengj.glidepaletteblur

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.renderscript.Toolkit

/**
 * 配合Glide模糊取背景
 *
 * Get blur bitmap from Glide
 *
 * @author shengj
 */
class GlideBlur : RequestListener<Drawable> {
    private var callback: GlideBlurCallback? = null
    private var radius: Int = 5

    /**
     * 获取Glide加载的模糊背景，[radius]为模糊半径，默认为5
     *
     * Set blur [radius] which default is 5, and pass a [GlideBlurCallback] to get blur bitmap result
     */
    fun generate(radius: Int? = null, callback: GlideBlurCallback): GlideBlur {
        this.callback = callback
        radius?.let { this.radius = it.coerceIn(1..25) }
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
            val blurBitmap = Toolkit.blur(it, radius)
            callback?.invoke(blurBitmap)
        }
        return false
    }
}

/**
 * Glide获取模糊背景回调
 *
 * Callback to the get the bitmap from Glide
 */
typealias GlideBlurCallback = (Bitmap?) -> Unit
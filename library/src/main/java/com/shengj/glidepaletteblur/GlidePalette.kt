package com.shengj.glidepaletteblur

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
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
        resource.getBitmap().let { bitmap ->
            if (bitmap != null) {
                val callbackRef = WeakReference(callback)
                Palette.from(bitmap).generate { palette ->
                    callbackRef.get()?.invoke(palette)
                }
            } else {
                callback?.invoke(null)
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
 * 根据[Palette]选择合适的浅色背景色
 *
 * Returns the appropriate color to use for view background in light mode
 */
fun Palette.lightBackground(@ColorInt defaultColor: Int = Color.TRANSPARENT): Int {
    val hsv = dominantSwatch?.hsl ?: return defaultColor
    if (hsv[1] < 0.02f || hsv[1] > 0.1f) hsv[1] = 0.05f
    if (hsv[2] < 0.9f || hsv[2] > 0.98f) hsv[2] = 0.95f
    return Color.HSVToColor(hsv)
}

/**
 * 根据[Palette]选择合适的深色背景色
 *
 * Returns the appropriate color to use for view background in dark mode
 */
fun Palette.darkBackground(@ColorInt defaultColor: Int = Color.TRANSPARENT): Int {
    val hsv = dominantSwatch?.hsl ?: return defaultColor
    if (hsv[1] < 0.3f || hsv[1] > 0.4f) hsv[1] = 0.35f
    if (hsv[2] < 0.25f || hsv[2] > 0.3f) hsv[2] = 0.27f
    return Color.HSVToColor(hsv)
}
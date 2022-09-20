package com.shengj.glidepaletteblur

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.load.resource.gif.GifDrawable
import java.lang.reflect.Field
import java.lang.reflect.Method


/**
 * 寻找最近一个类型为[T]的父节点
 *
 * Find the nearest parent of type [T]
 */
inline fun <reified T : View> View.ofTypeParent(): T? {
    var viewParent = parent
    while (viewParent != null) {
        if (viewParent is T) return viewParent
        viewParent = viewParent.parent
    }
    return null
}

/**
 * 获取drawable的bitmap，如果是[GifDrawable]则获取第一帧
 *
 * Get bitmap from drawable, if it's [GifDrawable] than get the first frame.
 */
fun Drawable?.getBitmap(): Bitmap? {
    return when (this) {
        is BitmapDrawable -> {
            bitmap
        }
        is GifDrawable -> {
            getBitmap()
        }
        else -> null
    }
}

private fun GifDrawable.getBitmap(): Bitmap? {
    if (firstFrame != null) return firstFrame
    try {
        // According to issue: https://github.com/bumptech/glide/issues/2158
        // we cannot get firstFrame after it displayed once.
        // In this situation, we need to reflect the frameLoader to get the current frame's bitmap.
        val gifState = constantState ?: return null
        val frameLoader: Field = gifState::class.java.getDeclaredField("frameLoader")
        frameLoader.isAccessible = true
        val gifFrameLoader = frameLoader.get(gifState) ?: return null
        val current: Method = gifFrameLoader::class.java.getDeclaredMethod("getCurrentFrame")
        current.isAccessible = true
        return current.invoke(gifFrameLoader) as? Bitmap
    } catch (ignore: Exception) {
        return null
    }
}
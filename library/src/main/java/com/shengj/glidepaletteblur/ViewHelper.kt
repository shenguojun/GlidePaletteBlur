package com.shengj.glidepaletteblur

import android.view.View


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
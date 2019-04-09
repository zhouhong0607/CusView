package com.example.macroz.myapplication.kotlin

data class TurnTo(val title: String, val layoutId: Int, val clazz: Class<*>?) {
    constructor(title: String, layoutId: Int) : this(title, layoutId, null)
    constructor(title: String, clazz: Class<*>?) : this(title, 0, clazz)
}
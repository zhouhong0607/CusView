package com.example.macroz.myapplication.kotlin

/**
 * 类描述:
 * 创建人: macroz
 * 创建时间: 2019/5/8 5:24 PM
 * 修改人: macroz
 * 修改时间: 2019/5/8 5:24 PM
 * 修改备注:
 * @version
 */
fun main(args: Array<String>) {

    val items = listOf(1, 2, 3, 4, 5)

//    items.fold(-1, { acc: Int, i: Int ->
//        println("initial $acc")
//        val result = acc + i
//        println("result $result")
//        result
//    })

    items.fold("element", { acc: String, i: Int ->
        println("acc $acc")
        val res = acc + " " + i
        println("res $res")
        res
    })
}

class Person(val name: String) {
    fun printName() {
        println(name)
    }
}
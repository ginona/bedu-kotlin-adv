package com.gino.projectbedu.testClass

object TestingObject {
    fun checkBraces(s:String) : Boolean {
        return s.count{ it == '(' } == s.count { it == ')'}
    }
}
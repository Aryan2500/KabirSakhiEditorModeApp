package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils

import android.util.Log
import java.util.regex.Pattern

object Splitter {
    lateinit var pattern: Pattern
    fun splitText(text: String, regex: String): Array<String> {
        pattern = Pattern.compile(regex)
        Log.v("res", "${pattern.split(text).size}")
        return pattern.split(text)
    }

}
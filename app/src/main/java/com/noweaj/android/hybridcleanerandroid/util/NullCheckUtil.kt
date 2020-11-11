package com.noweaj.android.hybridcleanerandroid.util

object NullCheckUtil {

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R){
        if(a != null && b != null) code(a, b)
    }

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: () -> R){
        if(a != null && b != null) code()
    }

    inline fun <A, B, C, R> ifNotNull(a: A?, b: B?, c: C?, code: (A, B, C) -> R){
        if(a != null && b != null && c != null) code(a, b, c)
    }

    inline fun <A, B, C, R> ifNotNull(a: A?, b: B?, c: C?, code: () -> R){
        if(a != null && b != null && c != null) code()
    }
}
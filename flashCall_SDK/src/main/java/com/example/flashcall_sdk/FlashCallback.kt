package com.example.flashcall_sdk

import javax.inject.Singleton

@Singleton
interface FlashCallback {
    fun onSuccess(s: String)
    fun onFailure(s: String)
}



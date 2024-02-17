package com.example.navigationdrawerpractice.ui.slide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideViewModel : ViewModel() {
    private val _currentId = MutableLiveData<Int>()
    val currentId: LiveData<Int> = _currentId
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val randomDescriptions = listOf(
        "lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
        "adipiscing", "elit", "sed", "do", "eiusmod", "tempor", "incididunt",
    )

    fun setId(id: Int) {
        _currentId.value = id
        _description.value = """
            id: $id
            random description: ${randomDescriptions.random()}
        """.trimIndent()
    }
}
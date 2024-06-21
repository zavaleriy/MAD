package com.example.mad.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoViewModel(val repository: Repository) : ViewModel() {

    private val _uris = MutableStateFlow<List<Uri>>(emptyList())
    val uris: StateFlow<List<Uri>> get() = _uris

    fun getImages() {
        val listUri = repository.getImages()
        viewModelScope.launch {
            _uris.emit(listUri.toMutableList())
        }
    }

    fun removeImage(uri: Uri) {
        getImages()
        _uris.value -= uri
    }
}
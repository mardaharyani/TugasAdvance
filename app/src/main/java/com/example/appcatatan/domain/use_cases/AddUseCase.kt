package com.example.appcatatan.domain.use_cases

import com.example.appcatatan.data.local.model.Note
import com.example.appcatatan.domain.repository.Repository
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.insert(note)
}
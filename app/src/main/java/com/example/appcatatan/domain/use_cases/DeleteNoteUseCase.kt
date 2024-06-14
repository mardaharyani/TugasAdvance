package com.example.appcatatan.domain.use_cases

import com.example.appcatatan.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}
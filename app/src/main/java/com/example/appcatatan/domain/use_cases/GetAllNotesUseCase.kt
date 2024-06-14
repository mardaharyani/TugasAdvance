package com.example.appcatatan.domain.use_cases

import com.example.appcatatan.data.local.model.Note
import com.example.appcatatan.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotes()
}
package com.example.appcatatan.data.repository

import com.example.appcatatan.data.local.NoteDao
import com.example.appcatatan.data.local.model.Note
import com.example.appcatatan.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): Repository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteById(id: Long): Flow<Note> {
        return noteDao.getNoteById(id)
    }

    override suspend fun insert(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun update(note: Note) {
        return noteDao.update(note)
    }

    override suspend fun delete(id: Long) {
        return noteDao.delete(id)
    }

    override fun getBookmarkedNotes(): Flow<List<Note>> {
        return noteDao.getBookmarkedNotes()
    }
}
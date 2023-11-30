package com.devmuyiwa.notesapp.data.repository

import androidx.lifecycle.LiveData // importa LiveData
import com.devmuyiwa.notesapp.data.model.Note // importa Note
import com.devmuyiwa.notesapp.data.model.NotesDao // importa NotesDao

class NotesRepository(private val dao: NotesDao) { // crea la clase NotesRepository con dao como parametro
    val getAllNotes: LiveData<List<Note>> = dao.getAllNotes() // obtiene todas las notas de la base de datos

    // agrega una nueva nota
    suspend fun addNewNote(note: Note) {
        dao.addNote(note) 
    }

    // actualiza una nota existente
    suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    // elimina una nota
    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    // elimina todas las notas
    suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

    // busca notas en la base de datos
    fun searchDb(query: String): LiveData<List<Note>> {
        return dao.searchDb(query)
    }
}

package com.devmuyiwa.notesapp.presentation.allNotes

import androidx.recyclerview.widget.DiffUtil // importa la clase DiffUtil
import com.devmuyiwa.notesapp.data.model.Note

class AllNotesDiffUtil(
    private val listOfOldNotes: List<Note>, 
    private val listOfNewNotes: List<Note>
) : DiffUtil.Callback() {
    
    override fun getOldListSize(): Int = listOfOldNotes.size // obtiene el tamaño de la lista vieja
    
    override fun getNewListSize(): Int = listOfNewNotes.size // obtiene el tamaño de la nueva lista

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOfOldNotes[oldItemPosition] === listOfNewNotes[newItemPosition] // compara si es el mismo item
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = listOfOldNotes[oldItemPosition] // obtiene nota vieja
        val newNote = listOfNewNotes[newItemPosition] // obtiene nota nueva
        return oldNote.id == newNote.id && oldNote.title == newNote.title && // compara id y titulo
                oldNote.category == newNote.category && oldNote.description == newNote.description // compara categoria y descripcion
    }
}

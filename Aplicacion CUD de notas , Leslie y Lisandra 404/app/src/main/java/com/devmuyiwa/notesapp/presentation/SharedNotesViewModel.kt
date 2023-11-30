// - La línea 3 importa el paquete donde se encuentra la clase  Application . 
// - La línea 4 importa el paquete  AndroidViewModel  de AndroidX. 
// - La línea 5 importa el paquete  viewModelScope  de AndroidX. 
// - Las líneas 7 a 12 importan las clases y paquetes necesarios para la implementación del ViewModel. 
// - La línea 14 declara la clase  SharedNotesViewModel  que extiende de  AndroidViewModel . 
// - En la línea 15 se inicializa el objeto  dao  con el acceso a la base de datos de notas. 
// - En la línea 16 se crea una instancia del repositorio de notas utilizando el objeto  dao . 
// - El método  insertNote  (líneas 18 a 22) inserta una nueva nota en la base de datos utilizando el repositorio en un contexto de  viewModelScope . 
// - El método  updateNote  (líneas 24 a 28) actualiza una nota existente en la base de datos utilizando el repositorio en un contexto de  viewModelScope . 
// - El método  deleteNote  (líneas 30 a 34) elimina una nota de la base de datos utilizando el repositorio en un contexto de  viewModelScope . 
// - El método  validateUserInput  (líneas 36 a 45) valida la entrada del usuario verificando si el título, la descripción y la categoría no están vacíos o nulos. 
// - El método  categoryToInt  (líneas 47 a 56) convierte una categoría del tipo  Category  a un entero correspondiente. 
// - El método  stringToCategory  (líneas 58 a 67) convierte una cadena de texto en una categoría del tipo  Category . Si la cadena no coincide con ninguna categoría, se devuelve  Category.UNCATEGORIZED .

package com.devmuyiwa.notesapp.presentation

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.devmuyiwa.notesapp.data.model.DbInstance
import com.devmuyiwa.notesapp.data.model.Note
import com.devmuyiwa.notesapp.data.repository.NotesRepository
import com.devmuyiwa.notesapp.domain.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedNotesViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = DbInstance.getDatabase(app).getNotesDao()
    private val repository: NotesRepository = NotesRepository(dao)

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNewNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun validateUserInput(title: String, description: String, category: String?): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) ||
            TextUtils.isEmpty(category)
        ) false
        else !(title.isEmpty() || description.isEmpty() || category.isNullOrEmpty())
    }

    fun categoryToInt(category: Category): Int {
        return when (category) {
            Category.UNCATEGORIZED -> 0
            Category.HOME -> 1
            Category.WORK -> 2
            Category.STUDY -> 3
            Category.PERSONAL -> 4
        }
    }

    fun stringToCategory(category: String): Category {
        return when (category) {
            "Uncategorized" -> Category.UNCATEGORIZED
            "Home" -> Category.HOME
            "Work" -> Category.WORK
            "Study" -> Category.STUDY
            "Personal" -> Category.PERSONAL
            else -> Category.UNCATEGORIZED
        }
    }

}
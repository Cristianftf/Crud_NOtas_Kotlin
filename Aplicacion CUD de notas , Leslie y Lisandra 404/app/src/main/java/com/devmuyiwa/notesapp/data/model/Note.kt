package com.devmuyiwa.notesapp.data.model

import android.os.Parcelable
import androidx.room.Entity // Anotación para marcar la clase como una entidad de Room
import androidx.room.PrimaryKey // Anotación para definir la clave primaria
import com.devmuyiwa.notesapp.domain.Category 
import kotlinx.parcelize.Parcelize

@Entity // Marca la clase como una entidad 
@Parcelize // Habilita la funcionalidad de parcelización
data class Note(
    // Usar val en lugar de var evita que se agreguen nuevos datos
    @PrimaryKey(autoGenerate = true) // Define el id como clave primaria autogenerada
    var id: Int,  
    var date: String,
    var title: String,
    var category: Category,
    var description: String  
): Parcelable // Implementa la interfaz Parcelable

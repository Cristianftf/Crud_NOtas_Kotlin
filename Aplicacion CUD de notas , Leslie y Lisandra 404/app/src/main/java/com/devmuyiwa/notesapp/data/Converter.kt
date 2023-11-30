package com.devmuyiwa.notesapp.data

import androidx.room.TypeConverter // importa la anotación TypeConverter de Room
import com.devmuyiwa.notesapp.domain.Category // importa el enum Category

class Converter {
    @TypeConverter // anotación para indicar que esta función convierte un tipo a otro
    fun categoryToString(category: Category): String {
        // convierte un objeto Category a String
        return category.name 
    }

    @TypeConverter // anotación para indicar que esta función convierte un tipo a otro
    fun stringToCategory(name: String): Category {
        // convierte un String a un objeto Category
        return Category.valueOf(name)  
    }
}
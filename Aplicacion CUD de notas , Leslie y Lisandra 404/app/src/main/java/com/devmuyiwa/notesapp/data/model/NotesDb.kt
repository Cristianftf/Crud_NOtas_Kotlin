package com.devmuyiwa.notesapp.data.model

import androidx.room.* // importa las anotaciones de Room
import com.devmuyiwa.notesapp.data.*

@Database(entities = [Note::class], version = 1, exportSchema = false) // define la base de datos de Room con la entidad Note y versión 1
@TypeConverters(Converter::class) // habilita convertidores de tipos personalizados
abstract class NotesDb : RoomDatabase() { // clase abstracta que extiende de RoomDatabase
    
    // función abstracta para obtener el DAO de notas
    abstract fun getNotesDao(): NotesDao 
}
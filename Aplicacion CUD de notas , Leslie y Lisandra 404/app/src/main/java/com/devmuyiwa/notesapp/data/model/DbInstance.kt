package com.devmuyiwa.notesapp.data.model

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

object DbInstance {
    // Declara una propiedad volátil para la instancia de base de datos
    @Volatile  
    private var instance: NotesDb? = null

    // Permite el uso de coroutinas internas sincronizadas
    @OptIn(InternalCoroutinesApi::class)
    fun getDatabase(context: Context): NotesDb {
        // Obtiene una referencia temporal a la instancia
        val temp = instance
        if (temp != null) {
            // Si la instancia temporal no es nula, retorna esa instancia
            return temp
        }
        
        // Ejecuta el bloque de código sincronizadamente
        synchronized(this) {
            // Construye la instancia de Room database
            val dbInstance = Room.databaseBuilder(
                context.applicationContext, NotesDb::class.java, "notes.db")
                .fallbackToDestructiveMigration()
                .build()
            
            // Asigna la instancia recién creada
            instance = dbInstance
            
            // Retorna la instancia de base de datos
            return dbInstance
        }
    }
}
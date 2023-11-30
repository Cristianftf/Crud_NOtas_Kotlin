// @Dao indica que esta interfaz es un DAO de Room
@Dao  
interface NotesDao {

    // @Query ejecuta la consulta SQL para obtener todas las notas ordenadas por fecha descendente
    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

    // @Insert inserta una nueva nota, ignorando conflictos 
    @Insert(onConflict = OnConflictStrategy.IGNORE) 
    suspend fun addNote(note: Note)

    // @Update actualiza una nota existente
    @Update  
    suspend fun updateNote(note: Note)

    // @Delete elimina una nota
    @Delete
    suspend fun deleteNote(note: Note)

    // @Query elimina todas las notas
    @Query("DELETE FROM note")
    suspend fun deleteAllNotes()

    // @Query busca notas por título
    @Query("SELECT * FROM note WHERE title LIKE :query ORDER BY date DESC")
    fun searchDb(query: String): LiveData<List<Note>>

    // @Query obtiene notas filtradas por categoría
    @Query("SELECT * FROM note WHERE category LIKE :category")
    fun sortByCategory(category: String): LiveData<List<Note>>
}

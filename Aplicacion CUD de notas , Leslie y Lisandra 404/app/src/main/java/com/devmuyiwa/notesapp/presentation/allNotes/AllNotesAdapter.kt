// Esta línea importa las clases necesarias
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devmuyiwa.notesapp.R
import com.devmuyiwa.notesapp.data.model.Note
import com.devmuyiwa.notesapp.databinding.LayoutAllNotesBinding
import com.devmuyiwa.notesapp.domain.Category

// Declara una constante para el tag de logs
private const val TAG = "AllNotesAdapter"

// Declara la clase adapter que extiende de RecyclerView.Adapter
class AllNotesAdapter(val context: Context) :
    RecyclerView.Adapter<AllNotesAdapter.NoteViewHolder>() {

// Declara la lista de notas  
    private var listOfNotes = ArrayList<Note>()

// Clase interna para el view holder 
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // obtiene una referencia al binding
        var binding = LayoutAllNotesBinding.bind(itemView)
       // función para establecer el color de fondo según la categoría
        fun setColor(note: Note, view: View) {
            when (note.category) {
                Category.UNCATEGORIZED -> view
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.uncategorized))
                Category.HOME -> view
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.home))
                Category.WORK -> view
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.work))
                Category.STUDY -> view
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.study))
                Category.PERSONAL -> view
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.personal))
            }
        }
    }

// Crea la vista del item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        Log.i(TAG, "onCreateViewHolder successful")
        val view = LayoutInflater.from(context).inflate(R.layout.layout_all_notes, parent, false)
        return NoteViewHolder(view)
    }

// Vincula los datos a la vista
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder : position $position")
        val note = listOfNotes[position]
        holder.setColor(note, holder.binding.noteBackground)
        holder.binding.noteTitle.text = note.title
        holder.binding.date.text = note.date
        holder.binding.noteBackground.setOnClickListener {
            val action = AllNotesFragmentDirections.actionAllNotesFragmentToDetailedNoteFragment(note)
            holder.itemView.findNavController().navigate(action)
        }

    }

// Devuelve el tamaño de la lista
    override fun getItemCount(): Int = listOfNotes.size

// Actualiza la lista de notas y notifica los cambios
    fun setData(notes: ArrayList<Note>) {
        val diffUtil = AllNotesDiffUtil(listOfNotes, notes)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        this.listOfNotes = notes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

La línea 1 importa el paquete donde se encuentra la clase  AllNotesFragment . 
// - Las líneas 3 a 16 importan las clases y paquetes necesarios para la implementación de la lista de notas. 
// - La línea 18 declara la clase  AllNotesFragment  que extiende de la clase  Fragment . 
// - Las líneas 20 a 23 declaran las propiedades privadas de la clase, incluyendo el enlace a la vista, el modelo de vista para todas las notas, el adaptador de notas y un objeto de diseño de cuadrícula para la lista de notas. 
// - El método  onCreateView  (líneas 25 a 33) infla el diseño de la vista para la pantalla de lista de notas y devuelve la vista raíz. 
// - El método  onViewCreated  (líneas 35 a 54) configura la pantalla de lista de notas, incluyendo el adaptador de notas, el diseño de cuadrícula, la búsqueda de notas y la eliminación de todas las notas. 
// - El método  inflateToolbar  (líneas 56 a 84) infla la barra de herramientas en la pantalla de lista de notas y configura la búsqueda de notas y la eliminación de todas las notas. 
// - El método  deleteAllNotes  (líneas 86 a 100) muestra un diálogo de alerta para confirmar la eliminación de todas las notas y, si se confirma, borra todas las notas de la base de datos. 
// - El método  displayNoData  (líneas 102 a 113) muestra un mensaje cuando no hay notas para mostrar en la lista. 
// - El método  searchDb  (líneas 115 a 125) busca notas en la base de datos según el título de la nota. 
// - El método  onDestroyView  (líneas 127 a 129) se ejecuta cuando la vista se destruye y se establece  _binding  en  null  para evitar fugas de memoria.
package com.devmuyiwa.notesapp.presentation.allNotes

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devmuyiwa.notesapp.R
import com.devmuyiwa.notesapp.data.model.Note
import com.devmuyiwa.notesapp.databinding.FragmentAllNotesBinding
import com.devmuyiwa.notesapp.domain.SpanSize

class AllNotesFragment : Fragment() {
    private var _binding: FragmentAllNotesBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel: AllNotesViewModel by viewModels()
    private var notesAdapter: AllNotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAllNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateToolbar()
        notesAdapter = AllNotesAdapter(requireContext())
        noteViewModel.getAllNotes.observe(viewLifecycleOwner) { listOfNotes ->
            noteViewModel.isDbEmpty(listOfNotes)
            notesAdapter?.setData(listOfNotes as ArrayList<Note>)
        }
        binding.listOfNotesRecyclerView.adapter = notesAdapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        layoutManager.spanSizeLookup = SpanSize(3, 1, 2)
        binding.listOfNotesRecyclerView.layoutManager = layoutManager
        binding.listOfNotesRecyclerView.hasFixedSize()

        noteViewModel.emptyDb.observe(viewLifecycleOwner) {
            displayNoData(it)
        }

        binding.addNewNote.setOnClickListener {
            findNavController().navigate(R.id.action_allNotesFragment_to_addNewNoteFragment)
        }
    }

    private fun inflateToolbar() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider, SearchView.OnQueryTextListener {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list_fragment, menu)
                val search = menu.findItem(R.id.search_note)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.queryHint = "Search by note title"
                searchView?.setOnQueryTextListener(this)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_all_notes -> {
                        deleteAllNotes()
                        true
                    }
                    else -> false
                }
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchDb(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchDb(newText)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext(), R.style.Theme_NotesApp_AlertDialogTheme)
        builder.setPositiveButton("Yes") { _, _ ->
            noteViewModel.deleteAllNotes()
            Toast.makeText(requireContext(), "All Notes deleted successfully", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all your notes permanently?")
        builder.create().show()
    }

    private fun displayNoData(isEmpty: Boolean) {
        if (isEmpty) {
            binding.noNotes.visibility = View.VISIBLE
            binding.listOfNotesRecyclerView.visibility = View.GONE
        } else {
            binding.noNotes.visibility = View.GONE
            binding.listOfNotesRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun searchDb(query: String?) {
        val searchQuery = "%$query%"
        noteViewModel.searchDb(searchQuery).observe(viewLifecycleOwner) { listOfNotes ->
            listOfNotes?.let {
                notesAdapter?.setData(it as ArrayList<Note>)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
// 
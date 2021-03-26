
package br.com.brunoccbertolini.applicationcontentprovider

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.brunoccbertolini.applicationcontentprovider.database.NotesDatabaseHelper.Companion.TITLE_NOTES
import br.com.brunoccbertolini.applicationcontentprovider.database.NotesProvider.Companion.URI_NOTES
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    lateinit var notesRecyclerView: RecyclerView
    lateinit var noteAdd: FloatingActionButton

    lateinit var adapterNote: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAdd = findViewById(R.id.notes_add)
        noteAdd.setOnClickListener {
            NotesDetailFragment().show(supportFragmentManager, "dialog")
        }

        setupAdapter()
        setupRecyclerView()

    }

    private fun setupAdapter() {
        adapterNote = NotesAdapter(object : NoteClickedListener {
            override fun noteClickedItem(cursor: Cursor) {
                val id = cursor?.getLong(cursor.getColumnIndex(_ID))
                val fragment = NotesDetailFragment.newInstance(id)
                fragment.show(supportFragmentManager, "dialog")

            }

            override fun noteRemoveItem(cursor: Cursor?) {
                val id = cursor?.getLong(cursor.getColumnIndex(_ID))
                contentResolver.delete(Uri.withAppendedPath(URI_NOTES, id.toString()), null, null)
            }

        })
        adapterNote.apply {
            setHasStableIds(true)
        }
    }

    private fun setupRecyclerView() {
        notesRecyclerView = findViewById(R.id.notes_recycler)
        notesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterNote
            hasFixedSize()
        }
        LoaderManager.getInstance(this).initLoader(0, null, this)
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
            CursorLoader(this, URI_NOTES, null, null, null,TITLE_NOTES)

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null){
            adapterNote.setCursor(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterNote.setCursor(null)
    }
}
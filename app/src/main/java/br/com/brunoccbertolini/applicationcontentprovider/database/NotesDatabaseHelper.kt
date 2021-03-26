package br.com.brunoccbertolini.applicationcontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotesDatabaseHelper(
    context: Context
):SQLiteOpenHelper(context, "databaseNotes", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NOTES (" +
                "$_ID INTEGER NOT NULL PRIMARY KEY, " +
                "$TITLE_NOTES TEXT NOT NULL, " +
                "$DESCRIPTION_NOTES TEXT NOT NULL)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        // Praticas de projetos do SQLite
        const val TABLE_NOTES = "Notes" //Tabela sempre tem que ter a primeira letra maiuscula
        const val TITLE_NOTES = "title" // Colunas sempre tudo minusculo
        const val DESCRIPTION_NOTES = "description"
    }
}
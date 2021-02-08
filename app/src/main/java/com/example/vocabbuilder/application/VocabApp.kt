package com.example.vocabbuilder.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vocabbuilder.db.VocabDatabase
import org.koin.core.context.startKoin

class VocabApp() : Application() {

    lateinit var appCtx: Context

    val migration_1_2 = object:Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE vocabulary ADD COLUMN synonym TEXT")
        }

    }
    override fun onCreate() {
        super.onCreate()
        VOCAB_DB = _createDatabase()
        ctx = applicationContext
        Log.d("KRIS", "VOCAB BUILT? ${VOCAB_DB != null}")
        startKoin {

        }
    }


    companion object {
        lateinit var ctx: Context
        lateinit var VOCAB_DB: VocabDatabase
    }

    private fun _createDatabase(): VocabDatabase {
        return Room.databaseBuilder(this, VocabDatabase::class.java, "vocabDb")
            .addMigrations(migration_1_2).build()
    }

}
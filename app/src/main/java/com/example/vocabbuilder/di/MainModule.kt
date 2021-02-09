package com.example.vocabbuilder.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vocabbuilder.db.VocabDatabase
import com.example.vocabbuilder.repositories.VocabularyRepository
import com.example.vocabbuilder.repositories.VocabularyRepositoryImpl
import com.example.vocabbuilder.viewmodels.AddWordViewModel
import com.example.vocabbuilder.viewmodels.VocabListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single { VocabularyRepositoryImpl() as VocabularyRepository }
    single<Migration>(named("migration1_2")) {
        object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE vocabulary ADD COLUMN synonym TEXT")
            }
        }
    }
    single<Migration>(named("migration2_3")) {
        object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE vocabulary ADD COLUMN synonym TEXT")
            }
        }
    }

    single<VocabDatabase> {
        Room.databaseBuilder(get(Application::class.java), VocabDatabase::class.java, "vocabDb")
            .addMigrations(get(Migration::class.java, qualifier = named("migration1_2"))).build()
    }
}

val vmModules = module {
    viewModel { AddWordViewModel(androidApplication()) }
    viewModel { VocabListViewModel(androidApplication(), get()) }
}
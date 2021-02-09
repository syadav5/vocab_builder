package com.example.vocabbuilder.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vocabbuilder.db.VocabDatabase
import com.example.vocabbuilder.di.dataModule
import com.example.vocabbuilder.di.vmModules
import com.example.vocabbuilder.testModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VocabApp() : Application() {
    lateinit var appCtx: Context

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        startKoin {
            androidContext(this@VocabApp)
            modules(dataModule, testModule, vmModules)
        }
    }
    companion object {
        lateinit var ctx: Context
    }
}
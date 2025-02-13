package com.example.proyectonuevobd3h.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectonuevobd3h.dao.InventarioDao
import com.example.proyectonuevobd3h.modelo.PuntuacionPeliculas

@Database(entities = [PuntuacionPeliculas::class], version = 1, exportSchema = false)
abstract class InventarioBaseDatos : RoomDatabase() {

    abstract fun inventarioDao(): InventarioDao

    companion object {
        @Volatile
        private var Instance: InventarioBaseDatos? = null

        fun obtenerBaseDatos(context: Context): InventarioBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventarioBaseDatos::class.java, "peliculasdb")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
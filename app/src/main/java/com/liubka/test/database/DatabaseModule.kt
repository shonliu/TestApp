package com.liubka.test.database

import android.content.Context
import androidx.room.Room
import com.liubka.test.repository.MyRepository
import com.liubka.test.repository.MyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideObjectDao(appDatabase: AppDatabase): ObjectDao = appDatabase.objDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "database-name").build()

    @Provides
    @Singleton
    fun provideMyRepository(objectDao: ObjectDao): MyRepository = MyRepositoryImpl(objectDao)

}
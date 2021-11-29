package com.homework.coursework.di.modules

import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.di.StreamFragmentScope
import dagger.Module
import dagger.Provides

@Module
class RecycleViewPoolModule {

    @StreamFragmentScope
    @Provides
    fun provideRecycleViewPool() = RecyclerView.RecycledViewPool()
}

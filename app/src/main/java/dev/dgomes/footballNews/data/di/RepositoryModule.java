package dev.dgomes.footballNews.data.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dev.dgomes.footballNews.data.repository.NewsRepositoryImpl;
import dev.dgomes.footballNews.domain.NewsRepository;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule { // If using @Binds, the module must be abstract

    @Binds
    @Singleton // Ensure the binding respects the scope of the implementation
    public abstract NewsRepository bindNewsRepository(NewsRepositoryImpl newsRepositoryImpl);
}
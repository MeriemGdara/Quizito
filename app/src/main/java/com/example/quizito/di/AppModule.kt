/**
 * Module Hilt responsable de fournir les instances nécessaires :
 * DataSources, Repositories et UseCases.
 */

package com.example.quizito.di

import com.example.quizito.data.data_sources.remote.FirebaseAuthDataSource  // Gardé (pour Auth, même si pas directement lié aux thèmes)
import com.example.quizito.data.data_sources.remote.ThemeRemoteDataSource  // Import ajouté : Pour ThemeRemoteDataSource
import com.example.quizito.data.repositories.AuthRepositoryImpl  // Import ajouté : Pour AuthRepositoryImpl
import com.example.quizito.data.repositories.ThemeRepositoryImpl  // Import ajouté : Pour ThemeRepositoryImpl
import com.example.quizito.domain.repositories.ThemeRepository  // Import ajouté : Pour ThemeRepository
import com.example.quizito.domain.use_cases.AuthUseCases  // Gardé (pour Auth)
import com.example.quizito.domain.use_cases.GetThemesUseCase  // Import ajouté : Pour GetThemesUseCase
import com.google.firebase.firestore.FirebaseFirestore  // Import ajouté : Pour FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context

import com.example.quizito.domain.use_cases.GetUserScoresUseCase
import com.example.quizito.data.data_sources.local.QuizLocalDataSource
import com.example.quizito.data.data_sources.remote.QuizRemoteDataSource
import com.example.quizito.data.repositories.QuizRepositoryImpl
import com.example.quizito.domain.repositories.QuizRepository
import com.example.quizito.domain.use_cases.GetQuizByIdUseCase
import com.example.quizito.domain.use_cases.GetQuizzesByThemeUseCase
import com.example.quizito.data.data_sources.remote.ScoreRemoteDataSource
import com.example.quizito.domain.use_cases.SaveScoreUseCase
import com.example.quizito.domain.use_cases.GetThemeTitleUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ---------------- AUTH ---------------- //

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(): FirebaseAuthDataSource =
        FirebaseAuthDataSource()

    @Provides
    @Singleton
    fun provideAuthRepository(
        ds: FirebaseAuthDataSource
    ): AuthRepositoryImpl = AuthRepositoryImpl(ds)

    @Provides
    @Singleton
    fun provideAuthUseCases(
        repo: AuthRepositoryImpl
    ): AuthUseCases = AuthUseCases(repo)

    // ---------------- FIREBASE ---------------- //

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore =  // Ajouté : Provider pour FirebaseFirestore (injecté dans ThemeRemoteDataSource)
        FirebaseFirestore.getInstance()
// ---------- QUIZ ----------

    @Provides
    @Singleton
    fun provideQuizRemoteDataSource(
        db: FirebaseFirestore
    ): QuizRemoteDataSource = QuizRemoteDataSource(db)

    @Provides
    @Singleton
    fun provideQuizLocalDataSource(): QuizLocalDataSource = QuizLocalDataSource()

    @Provides
    @Singleton
    fun provideQuizRepository(
        remote: QuizRemoteDataSource,
        local: QuizLocalDataSource
    ): QuizRepository = QuizRepositoryImpl(remote, local)

    @Provides
    @Singleton
    fun provideGetQuizzesByThemeUseCase(
        repo: QuizRepository
    ): GetQuizzesByThemeUseCase = GetQuizzesByThemeUseCase(repo)

    @Provides
    @Singleton
    fun provideGetQuizByIdUseCase(
        repo: QuizRepository
    ): GetQuizByIdUseCase = GetQuizByIdUseCase(repo)
    // ---------------- THEMES ---------------- //

    @Provides
    @Singleton
    fun provideThemeRemoteDataSource(
        db: FirebaseFirestore
    ): ThemeRemoteDataSource = ThemeRemoteDataSource(db)

    @Provides
    @Singleton
    fun provideThemeRepository(
        ds: ThemeRemoteDataSource,
        @ApplicationContext context: Context
    ): ThemeRepository = ThemeRepositoryImpl(ds, context)

    @Provides
    @Singleton
    fun provideGetThemesUseCase(
        repo: ThemeRepository
    ): GetThemesUseCase = GetThemesUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveScoreUseCase(remoteDataSource: ScoreRemoteDataSource): SaveScoreUseCase {
        return SaveScoreUseCase(remoteDataSource)
    }
    @Provides
    @Singleton
    fun provideGetUserScoresUseCase(remoteDataSource: ScoreRemoteDataSource): GetUserScoresUseCase {
        return GetUserScoresUseCase(remoteDataSource)
    }
    @Provides
    @Singleton
    fun provideGetThemeTitleUseCase(themeRemoteDataSource: ThemeRemoteDataSource): GetThemeTitleUseCase {
        return GetThemeTitleUseCase(themeRemoteDataSource)
    }

}
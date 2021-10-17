package com.faraji.newsapp.core.domain.use_cases

data class UseCases(
    val addToFavoritesUseCase: AddToFavoritesUseCase,
    val deleteSavedArticleUseCase: DeleteSavedArticleUseCase,
    val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    val searchNewsUseCase: SearchNewsUseCase
)

package com.example.red30.data

sealed class MainAction {
    data class OnSessionClick(val sessionId: Int): MainAction()
    data class OnDayClick(val day: Day): MainAction()
    data class OnFavoriteClick(val sessionId: Int): MainAction()
    object OnScrollComplete: MainAction()
    object OnActiveDestinationClick: MainAction()
    object OnDestinationClick: MainAction()
}

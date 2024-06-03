package com.finto.feature.createproject.mvi

sealed interface ProjectEvent {

    data object Initialize : ProjectEvent
    data object OnNavigateBack : ProjectEvent

    data class OnTitleInput(val text: String) : ProjectEvent
    data class OnDescriptionInput(val text: String) : ProjectEvent
    data object OpenAddUserDialog : ProjectEvent
    data class AddUserToChosen(val id: String) : ProjectEvent
    data object SaveChosenUsers : ProjectEvent

    data class RemoveTeamMember(val id: String) : ProjectEvent

    data object ShowDatePickerDialog : ProjectEvent
    data object ShowTimePickerDialog : ProjectEvent

    data class PickerTime(val time: Int) : ProjectEvent
    data class PickerDate(val date: Long) : ProjectEvent

    data object CreateProject : ProjectEvent

}
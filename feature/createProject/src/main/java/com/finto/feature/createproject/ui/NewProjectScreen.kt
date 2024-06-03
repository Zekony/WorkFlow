package com.finto.feature.createproject.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finto.feature.createproject.mvi.ProjectEvent
import com.finto.feature.createproject.mvi.ProjectState
import com.finto.feature.createproject.ui.composables.AddUserDialog
import com.finto.feature.createproject.ui.composables.DescriptionTextField
import com.finto.feature.createproject.ui.composables.TimeDateRow
import com.finto.feature.createproject.ui.composables.TimePickerDialog
import com.finto.feature.createproject.ui.composables.TitleTextField
import com.finto.feature.createproject.ui.composables.UsersRow
import com.finto.utility.functions.getCurrentTimeInES
import com.finto.utility.functions.getYearRange
import com.finto.utility.sharedComposables.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProjectScreen(state: ProjectState, onEvent: (ProjectEvent) -> Unit) {


    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= getCurrentTimeInES() * 1000 - 86400
        }
    }
    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState(
        selectableDates = selectableDates,
        yearRange = getYearRange(10)
    )

    if (state.showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { onEvent(ProjectEvent.ShowDatePickerDialog) },
            confirmButton = {
                onEvent(
                    ProjectEvent.PickerDate(
                        datePickerState.selectedDateMillis ?: 0L
                    )
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


    if (state.showTimePickerDialog)
        TimePickerDialog(
            title = "SelectDate",
            onEvent
        )

    if (state.showAddUserDialog) AddUserDialog(state, onEvent)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TitleTextField(state.currentProject.title, onEvent)
        DescriptionTextField(state.currentProject.description, onEvent)
        Text(text = "Add Team Members", style = MaterialTheme.typography.titleMedium)

        UsersRow(state.currentProject.projectMembers, onEvent)

        TimeDateRow(state.currentProject.dueDateEpochSeconds, onEvent)

        PrimaryButton(text = if (state.currentProject.id.isNotEmpty()) "Save" else "Create", enabled = state.currentProject.title.isNotEmpty()) {
            onEvent(ProjectEvent.CreateProject)
        }
    }
}
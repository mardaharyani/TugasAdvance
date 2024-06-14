package com.example.appcatatan.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    noteId: Long,
    assistedFactory: DetailAssistedFactory,
    navigateUp: () -> Unit,
) {
    val viewModel = viewModel(
        modelClass = DetailViewModel::class.java,
        factory = DetailedViewModelFactory(
            noteId = noteId,
            assistedFactory = assistedFactory
        )
    )

    val state = viewModel.state
    DetailScreen(
        modifier = modifier,
        isUpdatingNote = state.isUpdatingNote,
        isFormNotBlank = viewModel.isFormNotBlank,
        title = state.title,
        content = state.content,
        isBookMark = state.isBookmark,
        onBookMarkChange = viewModel::onBookMarkChange,
        onContentChange = viewModel::onContentChange,
        onTitleChange = viewModel::onTitleChange,
        onBtnClick = {
            viewModel.addOrUpdateNote()
            navigateUp()
        },
        onNavigate = navigateUp
    )
}

@Composable
private fun DetailScreen(
    modifier: Modifier,
    isUpdatingNote: Boolean,
    title: String,
    content: String,
    isBookMark: Boolean,
    onBookMarkChange: (Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBtnClick: () -> Unit,
    onNavigate: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(align = Alignment.Center),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopSection(
                title = title,
                isBookMark = isBookMark,
                onBookmarkChange = onBookMarkChange,
                onTitleChange = onTitleChange,
                onNavigate = onNavigate
            )
            Spacer(modifier = Modifier.size(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
            ) {
                NotesTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    value = content,
                    label = "Content",
                    onValueChange = onContentChange
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            AnimatedVisibility (isFormNotBlank) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onBtnClick) {
                        val icon = if (isUpdatingNote) Icons.Default.Update else Icons.Default.Check
                        Icon(imageVector = icon, contentDescription = "Update or Check")
                    }
                }
            }
        }
    }
}


@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookMark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onNavigate: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigate) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back"
            )

        }
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = title,
            label = "Title",
            labelAlign = TextAlign.Center,
            onValueChange = onTitleChange
        )
        IconButton(onClick = { onBookmarkChange(!isBookMark) }) {
            val icon = if (isBookMark) Icons.Default.BookmarkRemove else Icons.Outlined.BookmarkAdd
            Icon(
                imageVector = icon,
                contentDescription = "Remove or Add"
            )

        }
    }
}

@Composable
private fun NotesTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelAlign: TextAlign? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlign,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}
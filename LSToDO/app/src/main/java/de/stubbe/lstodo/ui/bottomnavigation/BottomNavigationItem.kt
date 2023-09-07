package de.stubbe.lstodo.ui.bottomnavigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val text: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

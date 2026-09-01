package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SaveTripDialog(
    defaultTitle: String,
    onDismiss: () -> Unit,
    onSave: (title: String, notes: String) -> Unit
) {
    var title by remember { mutableStateOf(defaultTitle) }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(text = "Rotayı Kaydet", style = MaterialTheme.typography.titleLarge)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Özelleştirilmiş gezi planınızı ve seçtiğiniz durakları daha sonra tekrar görüntülemek için kaydedin.",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Gezi Başlığı") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("save_title_input")
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Kişisel Notlar (Opsiyonel)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth().testTag("save_notes_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, notes) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("confirm_save_btn")
            ) {
                Text("Kaydet")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}

@Composable
fun DepartureTimePickerDialog(
    currentHour: Int,
    currentMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    var hour by remember { mutableIntStateOf(currentHour) }
    var minute by remember { mutableIntStateOf(currentMinute) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(text = "Kalkış Saatini Ayarla", style = MaterialTheme.typography.titleLarge)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = String.format("%02d:%02d", hour, minute),
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Saat: $hour",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Slider(
                        value = hour.toFloat(),
                        onValueChange = { hour = it.toInt() },
                        valueRange = 0f..23f,
                        steps = 22,
                        modifier = Modifier.fillMaxWidth().testTag("hour_slider")
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Dakika: $minute",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Slider(
                        value = minute.toFloat(),
                        onValueChange = { minute = (it / 5).toInt() * 5 },
                        valueRange = 0f..55f,
                        steps = 10,
                        modifier = Modifier.fillMaxWidth().testTag("minute_slider")
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(hour, minute) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("confirm_time_btn")
            ) {
                Text("Uygula")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}

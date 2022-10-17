package com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeleteReportDialog(
    setShowDialog : (Boolean) -> Unit,
    confirmButtonClickHandler : () -> Unit
) {
   AlertDialog(
       onDismissRequest = { setShowDialog(false) },
       confirmButton = { Button(
           onClick = {
               confirmButtonClickHandler()
               setShowDialog(false) },
           colors = ButtonDefaults.buttonColors(
               backgroundColor = Color.White,
               contentColor = Color.Red
           )
       ) {
           Text(text = "حذف", style = MaterialTheme.typography.body1)
       }},
       dismissButton = {
           Button(
               onClick = {setShowDialog(false)},
               colors = ButtonDefaults.buttonColors(
                       backgroundColor = Color.White,
                        contentColor = Color.Black)
           ) {
               Text(text = "لغو", style = MaterialTheme.typography.body1)
           } },
       title = { Text(text = "حذف آیتم", style = MaterialTheme.typography.body1)},
       text = { Text(text = "آیا از حذف این مورد اطمینان دارید؟", style = MaterialTheme.typography.body2)},
       shape = RoundedCornerShape(18.dp),
   )
}
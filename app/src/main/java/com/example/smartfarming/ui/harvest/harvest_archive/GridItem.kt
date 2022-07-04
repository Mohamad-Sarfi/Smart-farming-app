package com.example.smartfarming.ui.harvest.harvest_archive

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun GridItem(gardenName : String){
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, MainGreen)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .size(130.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            val (icon, name) = createRefs()

            Text(
                text = gardenName,
                style = MaterialTheme.typography.h5,
                color = MainGreen,
                modifier = Modifier
                    .constrainAs(name){
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )

            Icon(
                Icons.Default.Eco,
                contentDescription = null,
                tint = MainGreen,
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(icon) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )
        }
    }
}

@Composable
@Preview
fun PreviewGridItem(){
    GridItem(gardenName = "محمد")
}
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserProfileScreen() {
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                ConstraintLayout(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(10.dp),
                ) {
                    val (editIcon, userPhoto) = createRefs()
                    var editClicked by remember {
                        mutableStateOf(false)
                    }

                    Row(modifier = Modifier
                        .constrainAs(editIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    if (editClicked == false) {
                                        editClicked = true
                                    } else {

                                        editClicked = false
                                    }
                                }
                        )
                        
                        AnimatedVisibility(visible = editClicked) {
                            Text(
                                text = "ویرایش اطلاعات",
                                style = MaterialTheme.typography.subtitle2,
                                modifier = Modifier.padding(start = 4.dp),
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }

                    Icon(
                        Icons.Default.Person2,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .constrainAs(userPhoto) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .offset(y = 60.dp)
                            .background(MaterialTheme.colors.primary)
                            .border(3.dp, MaterialTheme.colors.onPrimary)
                            .clip(CircleShape)
                            .size(100.dp)
                            .padding(10.dp)
                    )
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 70.dp, horizontal = 50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "محمد صرفی", style = MaterialTheme.typography.h5, modifier = Modifier.padding(10.dp))
                    Row(Modifier.padding(7.dp)) {
                        Text(text = "کشاورز", style = MaterialTheme.typography.h6)
                        Text(text = "نوع کاربر: ", style = MaterialTheme.typography.h6, modifier = Modifier.padding(horizontal = 8.dp))
                    }
                }
            }

            Image(painter = painterResource(id = R.drawable.farmers), contentDescription = null, modifier = Modifier.size(270.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(text = "خروج از برنامه", style = MaterialTheme.typography.body2)
                        Icon(Icons.Default.Logout, contentDescription =null, modifier = Modifier.padding(start = 15.dp))
                    }
                }
            }
        }
    }
}
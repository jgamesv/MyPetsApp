package com.example.mypets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mypets.R
import com.example.mypets.domain.model.BaseResponse
import com.example.mypets.domain.model.Pet
import com.example.mypets.domain.model.PetMiss
import com.example.mypets.ui.apply_adoption.RequestAdoptionViewModel
import com.example.mypets.ui.navigation.Destination
import com.example.mypets.ui.pet.PetViewModel
import com.example.mypets.ui.profile.ProfileViewModel
import com.example.mypets.util.Constants
import com.example.mypets.util.Functions
import kotlinx.coroutines.runBlocking
import java.util.*

@Composable
fun Logo() {
    Image(
        painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.logo_dark) else painterResource(
            id = R.drawable.logo_light
        ),
        contentDescription = "Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp),
        alignment = Alignment.TopCenter
    )
}

@Composable
fun Images(imageName: String, modifier: Modifier) {
    AsyncImage(
        model = Constants.BASE_URL + "/api/image/" + imageName,
        contentDescription = "Image $imageName",
        contentScale = ContentScale.Crop,
        modifier = modifier,
        alignment = Alignment.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemList(navController: NavController, pet: Pet) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier.padding(10.dp),
        onClick = { navController.navigate(Destination.Details.createRoute(pet.id)) }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Images(
                imageName = pet.image, modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp), Arrangement.Center
            ) {

                Text(
                    text = pet.name.uppercase(Locale.getDefault()),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                InfoItem(imageVector = Icons.Filled.Place, text = pet.location)
            }


        }
    }
}

@Composable
fun ItemMiss(petMiss: PetMiss) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Images(
                imageName = petMiss.image, modifier = Modifier
                    .size(250.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(text = petMiss.summary, modifier = Modifier.padding(40.dp))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemType(type: String, viewModel: PetViewModel) {
    Card(
        modifier = Modifier
            .padding(10.dp),
        onClick = {
            runBlocking {
                viewModel.filter(type = type)
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserName(
    keyboardController: SoftwareKeyboardController?,
    username: String,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = onTextFieldChanged,
        label = { Text(text = "Username") },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
        singleLine = true,

        leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "User") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserEmail(
    keyboardController: SoftwareKeyboardController?,
    email: String,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onTextFieldChanged,
        label = { Text(text = "Email") },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
        leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        colors = OutlinedTextFieldDefaults.colors(),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserPhone(
    keyboardController: SoftwareKeyboardController?,
    phone: String?,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = if (phone.isNullOrEmpty()) "" else phone,
        onValueChange = onTextFieldChanged,
        label = { Text(text = "Phone") },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "phone") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserPass(
    keyboardController: SoftwareKeyboardController?,
    password: String,
    onTextFieldChanged: (String) -> Unit
) {


    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onTextFieldChanged,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),

        singleLine = true,
        label = { Text(text = "Password") },
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = "Pass") },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                if (isPasswordVisible) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                        contentDescription = "Hidden Pass"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_visibility_24),
                        contentDescription = "Look Pass"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
    )
}

@Composable
fun Summary(summary: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = summary,
        onValueChange = onTextFieldChanged,
        maxLines = 10,
        placeholder = { Text(text = "Enter your text here") },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
    )
}

@Composable
fun TextFieldForm(label: String, text: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextFieldChanged,
        maxLines = 10,
        isError = !Functions.isValidText(text),
        label = { Text(text = label) },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
    )
}

@Composable
fun IdentificationFieldForm(label: String, text: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextFieldChanged,
        maxLines = 10,
        isError = !Functions.isValidIdentification(text) && !Functions.isValidIdentificationNIE(text) && !Functions.isValidPassport(text) && !Functions.isValidBrp(text),
        label = { Text(text = label) },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
    )
}
@Composable
fun NumberFieldForm(label: String, number: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = number,
        onValueChange = onTextFieldChanged,
        maxLines = 10,
        isError = !Functions.isValidNumber(number),
        label = { Text(text = label) },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
@Composable
fun AddressFieldForm(label: String, text: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextFieldChanged,
        maxLines = 10,
        isError = text.isEmpty(),
        label = {Text(text = label)},
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
    )
}
@Composable
fun ArrowBackIcon(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "ArrowBack",
                modifier = Modifier.size(30.dp),
                tint =  MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
fun LogoutIcon(navController: NavController, viewModel: ProfileViewModel) {
    IconButton(
        onClick = {
            runBlocking {
                viewModel.logout()
            }
            navController.navigate(Destination.MainScreen.route)
        },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.ExitToApp,
                contentDescription = "Logout",
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(30.dp)
            )
        }

    }
}

@Composable
fun ErrorMessage(text: String) {

    Text(
        text = text,
        modifier = Modifier.padding(10.dp),
        color = MaterialTheme.colorScheme.error,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

}

@Composable
fun InfoItem(imageVector: ImageVector, text: String) {
    Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(text = text)
    }
}

@Composable
fun InfoItem(image: Int, text: String) {
    Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Icon(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(text = text)
    }
}

@Composable
fun Suitable(value: Boolean, text: String) {

    if (value) {
        InfoItem(imageVector = Icons.Filled.Check, text = " The animal is suitable for $text")
    } else {
        InfoItem(imageVector = Icons.Filled.Close, text = " The animal is not suitable for $text")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarArrowBack(navController: NavController, title: String) {
    TopAppBar(
        navigationIcon = { ArrowBackIcon(navController) },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color =  MaterialTheme.colorScheme.surface
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun Subtitle(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
@Composable
fun TextRadioButton(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
        textAlign = TextAlign.Center
    )
}
@Composable
fun RadioButtonBoolean(value: Boolean, viewModel: RequestAdoptionViewModel, code: Int) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = value,
                onClick = {
                    when (code) {
                        1 -> {
                            viewModel.onKidsChange(true)
                        }

                        2 -> {
                            viewModel.onPetsChange(true)
                        }
                    }
                })
            TextRadioButton(text = "Yes")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = !value,
                onClick = {
                    when (code) {
                        1 -> {
                            viewModel.onKidsChange(false)
                        }

                        2 -> {
                            viewModel.onPetsChange(false)
                        }
                    }
                })
            TextRadioButton(text = "No")
        }

    }
}
@Composable
fun ErrorDialog(code: BaseResponse) {

    var openDialog by remember { mutableStateOf(code.code > 0) }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(text = code.message)
            },
            confirmButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}
package upca.example.mealapp.ui.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import upca.example.mealapp.ui.theme.MealAppTheme
import upca.example.mealapp.ui.viewmodel.register.RegisterViewModel
import upca.example.mealapp.ui.viewmodel.register.RegisterViewState

@Composable
fun RegisterContent(
    uiState: RegisterViewState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(24.dp)
        )

        OutlinedTextField(
            value = uiState.name,
            onValueChange = { name ->
                onNameChange(name)
            },
            label = {
                Text("Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { email ->
                onEmailChange(email)
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { password ->
                onPasswordChange(password)
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )

        uiState.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(12.dp)
            )
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            TextButton(
                onClick = { onLoginClick() }
            ) {
                Text("I already have an account")
            }
        }
    }
}


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    RegisterContent(
        uiState = uiState,
        onNameChange = { name ->
            viewModel.updateName(name)
        },
        onEmailChange = { email ->
            viewModel.updateEmail(email)
        },
        onPasswordChange = { password ->
            viewModel.updatePassword(password)
        },
        onRegisterClick = {
            viewModel.register(onRegisterSuccess)
        },
        onLoginClick = onLoginClick,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MealAppTheme {
        RegisterContent(
            uiState = RegisterViewState(
                name = "name",
                email = "user@email.com",
                password = "ola123",
                error = null
            ),
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRegisterClick = {},
            onLoginClick = {}
        )
    }
}
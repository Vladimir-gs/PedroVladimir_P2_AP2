package edu.ucne.pedrovladimir_p2_ap2.presentation.depositos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoEdit(
    viewModel: DepositoViewModel = hiltViewModel(),
    goBack: () -> Unit,
    idDeposito: Int
) {
    LaunchedEffect(idDeposito) {
        viewModel.selectedDeposito(idDeposito)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DepositoBodyEdit(
        uiState = uiState,
        onFechaChange = viewModel::onDateChange,
        onConceptoChange = viewModel::onConceptoChange,
        onMontoChange = viewModel::onMontoChange,
        onIdCuentaChange = viewModel::onIdCuentaChange,
        onEdit = viewModel::save,
        goBack = goBack,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoBodyEdit(
    uiState: UiState,
    onFechaChange: (Date) -> Unit,
    onConceptoChange: (String) -> Unit,
    onMontoChange: (Double) -> Unit,
    onIdCuentaChange: (Int) -> Unit,
    onEdit: () -> Unit,
    goBack: () -> Unit,
){
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = uiState.fecha?.let { dateFormatter.format(it) } ?: ""
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text(
                        "Editar Deposito"
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(red = 102, green = 79, blue = 163, alpha = 255),
                    titleContentColor = Color.White
                )
            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)

        ){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Fecha") },
                value = formattedDate,
                readOnly = true,
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.clickable { showDatePicker = true }
                    )
                }
            )
            if (showDatePicker) {
                val calendar = Calendar.getInstance()
                val datePicker = android.app.DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time
                        onFechaChange(selectedDate)
                        showDatePicker = false
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.setOnCancelListener {
                    showDatePicker = false
                }
                datePicker.show()
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Concepto") },
                value = uiState.concepto,
                onValueChange = onConceptoChange,
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Id Cuenta") },

                value = uiState.idCuenta.toString(),
                onValueChange = {
                    onIdCuentaChange(it.toIntOrNull() ?: 0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Monto") },
                value = uiState.monto.toString(),
                onValueChange = {
                    onMontoChange(it.toDoubleOrNull() ?: 0.0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )



            uiState.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        goBack()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Volver")
                }
                Button(
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        onEdit()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Guardar"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Guardar")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ApiPreview() {
    DepositoBodyEdit(
        uiState = UiState(


        ),
        onFechaChange = {},
        onConceptoChange = {},
        onMontoChange = {},
        onIdCuentaChange = {},
        onEdit = {},
        goBack = {},
    )
}

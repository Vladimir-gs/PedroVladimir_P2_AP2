package edu.ucne.pedrovladimir_p2_ap2.presentation.depositos

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DepositoList(
    viewModel: DepositoViewModel = hiltViewModel(),
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onBack: () -> Unit,
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDepositos()
    }

    DepositoBody(
        uiState = uiState,
        onCreate = onCreate,
        onEdit = onEdit,
        onBack = onBack,
        refresh = viewModel::getDepositos
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoBody(
    uiState: UiState,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onBack: () -> Unit,
    refresh: () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Depositos") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(red = 102, green = 79, blue = 163, alpha = 255),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Ticket")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = {
                        refresh()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Recargar", tint = Color.Blue)
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(uiState.depositos) {
                        DepositoRow(
                            deposito = it,
                            onCreate = onCreate,
                            onEdit = onEdit
                        )
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }

}

@Composable
fun DepositoRow(
    deposito: DepositoDto,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Fecha: ${deposito.fecha?.let { dateFormat.format(it) }}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Cuenta: ${deposito.idCuenta}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Concepto: ${deposito.concepto}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Monto: ${deposito.monto}",
                    style = MaterialTheme.typography.bodyLarge,
                )

            }
            IconButton(
                onClick = { },
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "opciones", tint = Color.Blue)
            }
        }
    }

}

@Preview
@Composable
private fun DepositoPreview() {
    val apiDep = listOf(
        DepositoDto(
            idDeposito = 1,
            idCuenta = 1,
            concepto = "Juan",
            fecha = null,
            monto = 100.0,
        ),
        DepositoDto(
            idDeposito = 1,
            idCuenta = 1,
            concepto = "Juan",
            fecha = null,
            monto = 100.0,
        )

    )
    DepositoBody(
        uiState = UiState(depositos = apiDep),
        onCreate = {},
        onEdit = {},
        onBack = {},
        refresh = {}
    )
}
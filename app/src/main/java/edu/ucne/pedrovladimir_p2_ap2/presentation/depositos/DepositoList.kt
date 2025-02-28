import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.pedrovladimir_p2_ap2.presentation.depositos.DepositoViewModel
import edu.ucne.pedrovladimir_p2_ap2.presentation.depositos.UiState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DepositoList(
    viewModel: DepositoViewModel = hiltViewModel(),
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onBack: () -> Unit
) {
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
        onDelete = onDelete,
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
    onDelete: (Int) -> Unit,
    onBack: () -> Unit,
    refresh: () -> Unit
) {
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
                },
                actions = {
                    IconButton(onClick = refresh) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Recargar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreate) {
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
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.depositos) { deposito ->
                        DepositoRow(
                            deposito = deposito,
                            onEdit = onEdit,
                            onDelete = onDelete
                        )
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DepositoRow(
    deposito: DepositoDto,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val expanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { expanded.value = true } // Abre el menú al tocar
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = deposito.fecha?.let { dateFormat.format(it) } ?: "Sin fecha",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    text = "Cuenta: ${deposito.idCuenta}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Concepto: ${deposito.concepto}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${deposito.monto}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Box {
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            expanded.value = false
                            onEdit(deposito.idDeposito!!)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar", color = Color.Red) },
                        onClick = {
                            expanded.value = false
                            //onDelete(deposito.idDeposito)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DepositoPreview() {
    val currentDate = Calendar.getInstance().time // Obtener la fecha actual
    val apiDep = listOf(
        DepositoDto(
            idDeposito = 1,
            idCuenta = 1,
            concepto = "Pago de servicio",
            fecha = currentDate,
            monto = 150.0
        ),
        DepositoDto(
            idDeposito = 2,
            idCuenta = 2,
            concepto = "Depósito de ahorro",
            fecha = currentDate,
            monto = 300.0
        )
    )
    DepositoBody(
        uiState = UiState(depositos = apiDep),
        onCreate = {},
        onEdit = {},
        onDelete = {},
        onBack = {},
        refresh = {}
    )
}

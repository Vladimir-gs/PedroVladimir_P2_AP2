package edu.ucne.pedrovladimir_p2_ap2.presentation.depositos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.pedrovladimir_p2_ap2.data.remote.Resource
import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.pedrovladimir_p2_ap2.data.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class DepositoViewModel(
    private val apiRepository: ApiRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState =  _uiState.asStateFlow()

    init {
        getDepositos()
    }

    fun save() {
        viewModelScope.launch {
            val errorMessage = validate()
            if (errorMessage != null) {
                _uiState.update { it.copy(errorMessage = errorMessage) }
            } else {
                apiRepository.saveDeposito(_uiState.value.toEntity())
                nuevo()
            }
        }
    }

    fun edit() {
        viewModelScope.launch {
            val errorMessage = validate()
            if (errorMessage != null) {
                _uiState.update { it.copy(errorMessage = errorMessage) }
            } else {
                val deposito = _uiState.value.toEntity()
                try{
                    //apiRepository.updateDeposito(deposito.idDeposito ?: 0, deposito.toEntity())
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(errorMessage = "Ticket editado localmente. Sincronización pendiente: ${e.message}")
                    }
                }
            }
        }
    }

    private fun validate(): String? {
        return when {
            _uiState.value.idCuenta == 0 -> "El id del cliente no puede estar vacío"
            _uiState.value.concepto.isBlank() -> "El concepto no puede estar vacío"
            _uiState.value.monto == 0.0 -> "El monto no puede estar vacío"
            _uiState.value.fecha == null -> "La fecha no puede estar vacía"
            else -> null
        }
    }

    fun nuevo(){
        _uiState.value = UiState()
    }


    fun selectedDeposito(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val depositoApi = apiRepository.getDeposito(ticketId)
                _uiState.update {
                    it.copy(
                        idDeposito = depositoApi?.idDeposito,
                        fecha = depositoApi?.fecha,
                        idCuenta = depositoApi?.idCuenta ?: 0,
                        concepto = depositoApi?.concepto ?: "",
                        monto = depositoApi?.monto ?: 0.0,
                    )
                }
            }
        }
    }


    fun getDepositos() {
        viewModelScope.launch {
            apiRepository.getAllDepositos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Suceess -> {
                        _uiState.update {
                            it.copy(
                                depositos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onDateChange(date: Date) {
        _uiState.update {
            it.copy(fecha = date, errorMessage = null)
        }
    }

    fun onConceptoChange(concepto: String) {
        _uiState.update {
            it.copy(concepto = concepto, errorMessage = null)
        }
    }

    fun onMontoChange(monto: Double) {
        _uiState.update {
            it.copy(monto = monto, errorMessage = null)
        }
    }

    fun onIdCuentaChange(idCuenta: Int) {
        _uiState.update {
            it.copy(idCuenta = idCuenta, errorMessage = null)
        }
    }
}

data class UiState(
    val idDeposito: Int? = null,
    val fecha: Date? = null,
    val idCuenta: Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
    val depositos: List<DepositoDto> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

fun UiState.toEntity() = DepositoDto(
    idDeposito = idDeposito,
    fecha = fecha,
    idCuenta = idCuenta,
    concepto = concepto,
    monto = monto
)

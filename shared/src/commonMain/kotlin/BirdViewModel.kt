import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.BirdImage

data class BirdUiState(
    val images: List<BirdImage> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories = images.map { it.category }.toSet()
    val selectedImages = images.filter { it.category == selectedCategory }
}

class BirdViewModel : ViewModel() {

    private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    private val _uiStateFlow = MutableStateFlow(BirdUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        updateImages()
    }

    private suspend fun getImages(): List<BirdImage> {
        return httpClient
            .get("https://sebi.io/demo-image-api/pictures.json")
            .body<List<BirdImage>>()
    }

    private fun updateImages() {
        viewModelScope.launch {
            val images = getImages()
            _uiStateFlow.update {
                it.copy(images = images)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }

    fun updateCategory(category: String) {
        _uiStateFlow.update {
            it.copy(selectedCategory = category)
        }
    }

}





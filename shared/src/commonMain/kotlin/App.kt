import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.BirdListScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val birdViewModel = getViewModel(Unit, viewModelFactory { BirdViewModel() })
        BirdListScreen(birdViewModel)
    }
}

expect fun getPlatformName(): String
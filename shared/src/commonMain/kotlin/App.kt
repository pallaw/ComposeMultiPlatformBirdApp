import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import model.BirdImage
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            println(getImages())
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                KamelImage(
                    asyncPainterResource("https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRLc7H7naq3VSXNCuPm9jd_0Ynw1p1eGbX7gQehq2yqnLC2qM6sObLJic31pDZGAebs"),
                    null
                )
            }
        }
    }
}

val httpClient: HttpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getImages(): List<BirdImage> {
    return httpClient
        .get("https://sebi.io/demo-image-api/pictures.json")
        .body<List<BirdImage>>()
}

expect fun getPlatformName(): String
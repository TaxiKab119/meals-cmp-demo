package app.sizzle.cmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import app.sizzle.cmp.meals.data.MealsDbClient
import app.sizzle.cmp.core.data.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                client = remember {
                    MealsDbClient(createHttpClient(OkHttp.create()))
                }
            )
        }
    }
}
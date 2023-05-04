package com.example.thindie.gitdevelopmentprocess

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.thindie.gitdevelopmentprocess.ui.theme.LessonOneGITTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toastInvokerHolder = SealedClass(context = apply { })
        setContent {
            var isButtonClicked by rememberSaveable { mutableStateOf(false) }
            val changeButtonClickedStatus = { isButtonClicked = !isButtonClicked }

            LessonOneGITTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = changeButtonClickedStatus) {
                            if (isButtonClicked) {
                                toastInvokerHolder("message")
                                changeButtonClickedStatus()
                            }
                            Text(text = stringResource(id = R.string.common_button_annotation))
                        }
                    }
                }
            }
        }
    }
}

private sealed class SealedClass {
    data class DataClass(
        private val context: Context
    ) : SealedClass() {
        operator fun invoke(message: String) {
            with(Toast(context)) {
                setText(message)
                duration = Toast.LENGTH_SHORT
                show()
            }
        }
    }
    companion object {
         operator fun invoke(context: Context): DataClass {
            return DataClass(context)
        }
    }
}


@Suppress("SwallowedException", "NestedBlockDepth")
@Composable

private inline operator fun <reified T : SealedClass> T?.invoke() {
    val commonProducer: T? = this
        .apply {
            also { exampleAlsoScopeImplementation ->
                checkNotNull(exampleAlsoScopeImplementation)
            }
                .run {
                    let {
                        try {
                            require(false)
                        } catch (alwaysFailedRequirement: IllegalArgumentException) {
                            null
                        }
                    }?.let { this != null }
                }
        }
    when (T::class) {
        SealedClass.DataClass::class -> {
            commonProducer as SealedClass.DataClass
            commonProducer(
                runScopeFunWithoutContextObject
                { rememberUrTheBestTeacher().secretMessage }
            )
        }
        else -> {}
    }
}


@Composable
private fun runScopeFunWithoutContextObject(foo: @Composable () -> String) = run {
    foo()
}

@Composable
fun rememberUrTheBestTeacher(): MessageState {
    val message = stringResource(id = R.string.sherlock)
    return remember(message) {
        MessageState(message)
    }
}

@Stable
class MessageState(private val message: String) {
    val secretMessage
        @Composable get() = message
}
package com.example.sample

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.red30.R
import com.example.red30.ui.theme.Red30TechTheme
import androidx.compose.foundation.Image as createImage
import androidx.compose.material3.ElevatedButton as createButton
import androidx.compose.material3.Text as createText

@SuppressLint("ComposableNaming")
@Composable
fun buttonContent() {
    createText(
        modifier = Modifier.fillMaxWidth(fraction = 0.5f),
        text = "Let's go!",
        textAlign = TextAlign.Center
    )
}

fun getColumnContent(): @Composable ColumnScope.() -> Unit = {
    createImage(
        painter = painterResource(R.drawable.alternate_stacked_logo_color),
        contentDescription = "logo"
    )
    createButton(
        onClick = { },
        content = { buttonContent() }
    )
}

@Composable
fun Red30TechAppExtended(modifier: Modifier = Modifier) {
    Red30TechTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false,
        content = {
            Scaffold(
                modifier = modifier then Modifier.fillMaxSize(),
                content = { innerPadding: PaddingValues ->
                    Column(
                        modifier = Modifier.padding(innerPadding) then Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = getColumnContent()
//                        content = {
//                            createImage(
//                                painter = painterResource(R.drawable.alternate_stacked_logo_color),
//                                contentDescription = "logo"
//                            )
//
//                            createButton(
//                                onClick = { },
//                                content = { buttonContent() }
//                            )
//                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Red30TechAppExtendedPreview() {
    Red30TechAppExtended()
}

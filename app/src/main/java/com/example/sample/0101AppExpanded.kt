package com.example.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.red30.R
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
fun Red30TechAppExtended(modifier: Modifier = Modifier) {
    Red30TechTheme {
        Scaffold(
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.alternate_stacked_logo_color),
                    contentDescription = "logo"
                )

                ElevatedButton(
                    onClick = { }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                        text = "Let's go!",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Red30TechAppExtendedPreview() {
    Red30TechAppExtended()
}

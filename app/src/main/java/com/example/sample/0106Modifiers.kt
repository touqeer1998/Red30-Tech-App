package com.example.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
fun Modifiers0106(modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .padding(top = 4.dp, end = 4.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .padding(top = 4.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                )
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(top = 4.dp)
                        .clickable(onClick = {})
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .align(Alignment.TopCenter)
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.error)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.inversePrimary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Modifiers0106Preview() {
    Red30TechTheme {
        Modifiers0106()
    }
}

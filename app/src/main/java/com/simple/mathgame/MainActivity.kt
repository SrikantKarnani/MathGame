package com.simple.mathgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.simple.mathgame.operations.OperationActivity
import com.simple.mathgame.operations.OperationViewModel
import com.simple.mathgame.ui.theme.MathGameTheme
import com.simple.mathgame.ui.theme.OperationCardTheme
import com.simple.mathgame.ui.theme.generateRandomGradientColor
import com.simple.mathgame.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<OperationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathGameTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    HomeScreen()
                }
            }
        }
    }

    @Composable
    fun HomeScreen() {
        Column {
            Column(modifier = Modifier.weight(1.0f)) {
                OperationCard(
                    operation = AdditionOperation,
                    level = "Level ${
                        viewModel.getLevelForOperator(
                            getSignFromOperation(
                                AdditionOperation
                            )
                        )?.level ?: 1
                    }",
                    R.drawable.ic_addition
                )
            }
            Column(modifier = Modifier.weight(1.0f)) {
                OperationCard(
                    operation = SubtractionOperation,
                    level = "Level ${
                        viewModel.getLevelForOperator(
                            getSignFromOperation(
                                SubtractionOperation
                            )
                        )?.level ?: 1
                    }",
                    R.drawable.ic_subtract
                )
            }
            Column(modifier = Modifier.weight(1.0f)) {
                OperationCard(
                    operation = MultiplicationOperation,
                    level = "Level ${
                        viewModel.getLevelForOperator(
                            getSignFromOperation(
                                MultiplicationOperation
                            )
                        )?.level ?: 1
                    }",
                    R.drawable.ic_multiply
                )
            }
            Column(modifier = Modifier.weight(1.0f)) {
                OperationCard(
                    operation = DivisionOperation,
                    level = "Level ${
                        viewModel.getLevelForOperator(
                            getSignFromOperation(
                                DivisionOperation
                            )
                        )?.level ?: 1
                    }",
                    R.drawable.ic_divide
                )
            }
        }
    }

    @Composable
    fun OperationCard(operation: String, level: String, resourceId: Int) {
        val isDark = isSystemInDarkTheme()
        val context = LocalContext.current
        val bColor = remember { Animatable(generateRandomGradientColor(isDark)) }
        OperationCardTheme {
            val animatedColor = animateColorAsState(
                bColor.value
            )
//        LaunchedEffect(key1 = Unit, block = {
//            while (true) {
//                bColor.animateTo(
//                    generateRandomGradientColor(isDark), tween(
//                        100, easing = LinearEasing
//                    )
//                )
//                delay(1000L)
//            }
//        })
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (icon, card) = createRefs()
                val guideLineIconStart = createGuidelineFromStart(80.dp)
                Card(modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(
                                context,
                                OperationActivity::class.java
                            ).apply {
                                putExtra("operation", operation)
                                putExtra("bColor", bColor.value.toArgb())
                                putExtra("level", 1)
                            })
                    }
                    .constrainAs(card) {
                        this.start.linkTo(guideLineIconStart)
                        this.end.linkTo(parent.end)
                        this.width = Dimension.fillToConstraints
                    }) {
                    ConstraintLayout(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf<Color>(
                                        animatedColor.value,
                                        MaterialTheme.colors.surface
                                    )
                                )
                            )
                            .fillMaxHeight()
                    ) {
                        val (tvTitle, tvLevel) = createRefs()
                        val guideLineEnd = createGuidelineFromEnd(.05f)
                        val guideLineTop = createGuidelineFromTop(.05f)
                        Text(
                            text = operation,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.constrainAs(tvTitle) {
                                this.top.linkTo(guideLineTop)
                                linkTo(icon.end, guideLineEnd, bias = 1f)
                            }
                        )
                        Text(
                            text = level,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.constrainAs(tvLevel) {
                                this.top.linkTo(tvTitle.bottom)
                                this.end.linkTo(guideLineEnd)
                            }
                        )
                    }
                }
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = "Operation Image",
                    modifier = Modifier.constrainAs(icon) {
                        this.top.linkTo(parent.top)
                        this.bottom.linkTo(parent.bottom)
                        this.start.linkTo(guideLineIconStart)
                        this.end.linkTo(guideLineIconStart)
                    })
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HomeScreen()
    }
}
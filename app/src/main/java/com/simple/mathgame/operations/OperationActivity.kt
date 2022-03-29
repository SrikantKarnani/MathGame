package com.simple.mathgame.operations

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LiveData
import com.simple.mathgame.*
import com.simple.mathgame.R
import com.simple.mathgame.data.Level
import com.simple.mathgame.ui.theme.MathGameTheme
import com.simple.mathgame.ui.theme.generateRandomGradientColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.random.Random


@ExperimentalAnimationApi
@AndroidEntryPoint
class OperationActivity : ComponentActivity() {
    private val viewModel by viewModels<OperationViewModel>()
    private var showCorrectDialog by mutableStateOf(false)
    private var showIncorrectDialog by mutableStateOf(false)
    var text by mutableStateOf("")
    var textResult by mutableStateOf("")
    lateinit var operation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
        operation = intent.getStringExtra("operation") ?: AdditionOperation
        viewModel.generateEquation(
            getSignFromOperation(
                operation
            )
        )
    }

    @Composable
    fun MainView() {
        MathGameTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                color = Color(
                    intent.getIntExtra(
                        "bColor",
                        MaterialTheme.colors.background.toArgb()
                    )
                ),
            ) {
                Column {
                    Image(painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .width(64.dp)
                            .padding(16.dp)
                            .height(64.dp)
                            .clickable(true) {
                                finish()
                            })
                    LiveOperationView(
                        viewModel.liveEquation
                    )
                }
            }
        }
    }


    @Composable
    fun LiveOperationView(lEquation: LiveData<Equation?>) {
        val equation by lEquation.observeAsState(null)
        equation?.let { eq -> OperationView(equation = eq) }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun OperationView(equation: Equation) {
        text = ""
        textResult = ""
        val infiniteTransition = rememberInfiniteTransition()
        val offset by infiniteTransition.animateFloat(
            initialValue = -16f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 150
                },
                repeatMode = RepeatMode.Reverse
            )
        )
        val focusManager = LocalFocusManager.current
        val coroutineScope = rememberCoroutineScope()
        var options = emptyList<String>()
        LaunchedEffect(key1 = equation.solution) {
            val optionsJob = async { generateRandomOptions(equation.solution.toString()) }
            options = optionsJob.await()
        }
        val operatorLevel = viewModel.getLevelForOperator(equation.operator)
            ?: Level(equation.operator.toCharArray().get(0).code, 1, 0)
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val (currentProgress, nextProgress, progress,
                optionsCL, operands, operator, divider, tvSolution, tvResult) = createRefs()
            Text(text = "Level ${operatorLevel.level}", modifier = Modifier
                .constrainAs(currentProgress) {
                    this.start.linkTo(parent.start)
                }
                .padding(start = 16.dp), style = MaterialTheme.typography.body1)
            Text(text = "Level ${minOf(10, operatorLevel.level + 1)}", modifier = Modifier
                .constrainAs(nextProgress) {
                    this.end.linkTo(parent.end)
                }
                .padding(end = 16.dp), style = MaterialTheme.typography.body1)
            LinearRoundedProgressIndicator(
                operatorLevel.level.toFloat() / 10,
                modifier = Modifier
                    .constrainAs(progress) {
                        this.top.linkTo(currentProgress.top)
                        this.bottom.linkTo(currentProgress.bottom)
                        this.start.linkTo(currentProgress.end)
                        this.end.linkTo(nextProgress.start)
                        this.width = Dimension.fillToConstraints
                    }
                    .height(16.dp)
                    .padding(start = 16.dp, end = 16.dp),
                color = MaterialTheme.colors.onSurface,
                backgroundColor = MaterialTheme.colors.surface
            )
            val guideLineOperationsStart = createGuidelineFromStart(.45f)
            createVerticalChain(operands, divider, tvSolution, chainStyle = ChainStyle.Packed(0F))
            LazyColumn(reverseLayout = false,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .constrainAs(operands) {
                        this.top.linkTo(progress.bottom)
                        this.bottom.linkTo(divider.top)
                        this.linkTo(guideLineOperationsStart, parent.end, bias = 0f)
                        width = Dimension.preferredWrapContent
                    }
                    .padding(top = 48.dp)) {
                items(equation.operands.size) { message ->
                    OperandView(
                        equation.operands[message].toString(),
                        if (operatorLevel.level > 5) MaterialTheme.typography.h2 else MaterialTheme.typography.h1
                    )
                }
            }
            Image(
                painter = painterResource(id = getImageRes(equation.operator)),
                contentDescription = "Operation Image",
                modifier = Modifier
                    .constrainAs(operator) {
                        this.bottom.linkTo(operands.bottom)
                        this.end.linkTo(operands.start)
                    }
                    .wrapContentWidth()
                    .size(72.dp, 72.dp)
                    .padding(8.dp, 8.dp, 8.dp, 16.dp)
            )
            Divider(
                modifier = Modifier
                    .constrainAs(divider) {
                        this.top.linkTo(operands.bottom)
                        this.bottom.linkTo(tvSolution.top)
                        this.start.linkTo(operands.start)
                        this.end.linkTo(operands.end)
                        this.width = Dimension.fillToConstraints
                    }, thickness = 4.dp,
                color = MaterialTheme.colors.onSurface
            )
            AnimatedVisibility(visible = false) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Justify,
                        textDirection = TextDirection.Rtl
                    )
                        .merge(MaterialTheme.typography.h1),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colors.onSurface
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus(true)
                        if (text == equation.solution.toString()) {
                            coroutineScope.launch {
                                actionOnCorrect()
                            }
                        } else {
                            textResult = "Wrong"
                            coroutineScope.launch {
                                actionOnWrong()
                            }
                        }
                    },
                    modifier = Modifier
                        .constrainAs(tvSolution) {
                            this.top.linkTo(divider.bottom)
                            this.bottom.linkTo(parent.bottom)
                            this.end.linkTo(divider.end)
                        }
                        .wrapContentWidth()
                        .absoluteOffset(
                            x = if (textResult.equals(
                                    "wrong",
                                    true
                                )
                            ) offset.dp else 0.dp
                        )
                        .padding(vertical = 4.dp),
                )
            }
            OptionsGrid(options = options, solution = equation.solution.toString(), modifier =
            Modifier.constrainAs(optionsCL) {
                this.centerHorizontallyTo(parent)
                linkTo(top = divider.bottom, bottom = parent.bottom, bias = .7f)
            })
            Text(text = textResult, modifier = Modifier
                .constrainAs(tvResult) {
                    this.centerHorizontallyTo(parent)
                    this.top.linkTo(optionsCL.bottom)
                }
                .padding(16.dp))
//            CorrectAlertDialog(showCorrectDialog)
//            InCorrectAlertDialog(showIncorrectDialog)
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun OptionsGrid(options: List<String>, solution: String, modifier: Modifier) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(options.size) { optionsIndex ->
                OptionView(
                    option = options[optionsIndex],
                    correctAns = options[optionsIndex] == solution
                )
            }
        }
    }

    @Composable
    fun OptionView(option: String, correctAns: Boolean) {
        val coroutineScope = rememberCoroutineScope()
        val isSystemInDarkMode = isSystemInDarkTheme()
        val bColor = remember { mutableStateOf(generateRandomGradientColor(isSystemInDarkMode)) }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), backgroundColor = bColor.value
        ) {
            AutoResizeText(
                text = option,
                fontSizeRange = FontSizeRange(
                    MaterialTheme.typography.h6.fontSize,
                    MaterialTheme.typography.h2.fontSize
                ),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable {
                        if (correctAns) {
                            coroutineScope.launch {
                                actionOnCorrect()
                            }
                        } else {
                            textResult = "Wrong"
                            coroutineScope.launch {
                                actionOnWrong()
                            }
                        }
                    },
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun AutoResizeText(
        text: String,
        fontSizeRange: FontSizeRange,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = null,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = null,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        style: TextStyle = LocalTextStyle.current,
    ) {
        var fontSizeValue by remember { mutableStateOf(fontSizeRange.max.value) }
        var readyToDraw by remember { mutableStateOf(false) }

        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            style = style,
            fontSize = fontSizeValue.sp,
            onTextLayout = {
                Log.d("AutoFitTV", "onTextLayout")
                if (it.didOverflowHeight && !readyToDraw) {
                    Log.d("AutoFitTV", "Did Overflow height, calculate next font size value")
                    val nextFontSizeValue = fontSizeValue - fontSizeRange.step.value
                    if (nextFontSizeValue <= fontSizeRange.min.value) {
                        // Reached minimum, set minimum font size and it's readToDraw
                        fontSizeValue = fontSizeRange.min.value
                        readyToDraw = true
                    } else {
                        // Text doesn't fit yet and haven't reached minimum text range, keep decreasing
                        fontSizeValue = nextFontSizeValue
                    }
                } else {
                    // Text fits before reaching the minimum, it's readyToDraw
                    readyToDraw = true
                }
            },
            modifier = modifier.drawWithContent { if (readyToDraw) drawContent() }
        )
    }

    data class FontSizeRange(
        val min: TextUnit,
        val max: TextUnit,
        val step: TextUnit = DEFAULT_TEXT_STEP,
    ) {
        init {
            require(min < max) { "min should be less than max, $this" }
            require(step.value > 0) { "step should be greater than 0, $this" }
        }

        companion object {
            private val DEFAULT_TEXT_STEP = 1.sp
        }
    }

    private suspend fun generateRandomOptions(
        correctAns: String
    ): List<String> {
        val ans = correctAns.toDouble()
        val options = mutableSetOf<String>()
        options.add(correctAns)
        coroutineScope {
            launch(Dispatchers.IO) {
                withTimeout(500) {
                    try {
                        while (options.size < 4 && this.isActive) {
                            when {
                                (ans % 10) == ans -> {
                                    options.add(
                                        Random.nextIntInRange(1, (absMaxOf(ans, 1.0) * 4).toInt())
                                            .toString()
                                    )
                                }
                                Random.nextBoolean() -> {
                                    options.add(
                                        (ans + Random.nextIntInRange(
                                            0,
                                            (absMaxOf(
                                                ans,
                                                1.0
                                            ) / (10.0.pow(correctAns.length - 2.0))).toInt()
                                        ) * 10).toInt().toString()
                                    )
                                }
                                else -> {
                                    options.add(
                                        (ans - Random.nextIntInRange(1, ans.toInt())).toInt()
                                            .toString()
                                    )
                                }
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }
        return options.shuffled()
    }

    private suspend fun actionOnWrong() {
        MediaPlayer.create(this, R.raw.gnome_error).apply { start() }
        delay(1500L)
        text = ""
        textResult = ""
    }

    private suspend fun actionOnCorrect() {
        textResult = "Correct"
        MediaPlayer.create(this, R.raw.correct).apply { start() }
        delay(1000L)
        viewModel.generateEquation(
            getSignFromOperation(
                operation
            )
        )
        viewModel.updateLevel(
            (viewModel.getLevelForOperator(getSignFromOperation(operation))
                ?: Level(
                    getSignFromOperation(operation).toCharArray().get(0).code,
                    1,
                    0
                ))
        )
    }

//    @Composable
//    fun CorrectAlertDialog(visible: Boolean) {
//        if (visible) {
//            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.right))
//            AlertDialog(
//                onDismissRequest = { showCorrectDialog = false },
//                confirmButton = {
//                    TextButton(onClick = {
//                        viewModel.generateEquation(
//                            getSignFromOperation(
//                                operation
//                            )
//                        )
//                        viewModel.updateLevel(
//                            (viewModel.getLevelForOperator(getSignFromOperation(operation))
//                                ?: Level(
//                                    getSignFromOperation(operation).toCharArray().get(0).code,
//                                    1,
//                                    0
//                                )).apply {
//                                this.progress = minOf(20, this.progress + 1)
//                                if (this.progress == 20 && this.level < 10) {
//                                    this.level++
//                                    this.progress = 0
//                                }
//                            })
//                        showCorrectDialog = false
//                    }) {
//                        Text(text = "Next")
//                    }
//                },
//                title = {
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        LottieAnimation(
//                            iterations = LottieConstants.IterateForever,
//                            composition = composition,
//                            modifier = Modifier
//                                .size(200.dp),
//                            alignment = Alignment.Center
//                        )
//                    }
//                },
//                text = {
//                    Text(
//                        text = "Great.. Solve more questions to increase your level",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                },
//                shape = RoundedCornerShape(30.dp)
//            )
//        }
//    }
//
//    @Composable
//    fun InCorrectAlertDialog(visible: Boolean) {
//        if (visible) {
//            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wrong))
//            AlertDialog(
//                onDismissRequest = { showIncorrectDialog = false },
//                confirmButton = {
//                    TextButton(onClick = {
//                        text = ""
//                        showIncorrectDialog = false
//                    }) {
//                        Text(text = "Retry")
//                    }
//                },
//                title = {
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        LottieAnimation(
//                            iterations = LottieConstants.IterateForever,
//                            composition = composition,
//                            modifier = Modifier
//                                .size(200.dp),
//                            alignment = Alignment.Center
//                        )
//                    }
//                },
//                text = {
//                    Text(
//                        text = "Oops your answer is incorrect..",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                },
//                shape = RoundedCornerShape(30.dp)
//            )
//        }
//    }

    @Composable
    fun OperandView(operand: String, textStyle: TextStyle) {
        Box(
            contentAlignment = Alignment.CenterEnd, modifier = Modifier.wrapContentSize(),
        ) {
            Text(
                text = operand,
                style = textStyle,
                maxLines = 1,
                softWrap = false
            )
        }
    }


    @Preview
    @Composable
    fun PreviewOperations() {
        MainView()
    }

    @Composable
    fun LinearRoundedProgressIndicator(
        /*@FloatRange(from = 0.0, to = 1.0)*/
        progress: Float,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colors.primary,
        backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
    ) {
        val linearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth
        val linearIndicatorWidth = 240.dp
        Canvas(
            modifier
                .progressSemantics(progress)
                .size(linearIndicatorWidth, linearIndicatorHeight)
                .focusable()
        ) {
            val strokeWidth = size.height
            drawRoundedLinearIndicatorBackground(backgroundColor, strokeWidth)
            drawRoundedLinearIndicator(0f, progress, color, strokeWidth)
        }
    }

    private fun DrawScope.drawRoundedLinearIndicatorBackground(
        color: Color,
        strokeWidth: Float
    ) = drawRoundedLinearIndicator(0f, 1f, color, strokeWidth)

    private fun DrawScope.drawRoundedLinearIndicator(
        startFraction: Float,
        endFraction: Float,
        color: Color,
        strokeWidth: Float
    ) {
        val width = size.width
        val height = size.height
        // Start drawing from the vertical center of the stroke
        val yOffset = height / 2

        val isLtr = layoutDirection == LayoutDirection.Ltr
        val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
        val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

        // Progress line
        drawLine(
            color,
            Offset(barStart, yOffset),
            Offset(barEnd, yOffset),
            strokeWidth,
            StrokeCap.Round
        )
    }
}
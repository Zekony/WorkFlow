package com.finto.utility.sharedComposables

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary,
    currentValue: Float,
    minValue: Int = 0,
    maxValue: Int = 100,
) {
    Box(
        modifier = Modifier.then(modifier)
    ) {
        CanvasCircularSlider(
            primaryColor,
            secondaryColor,
            (currentValue * 100).toInt(),
            minValue,
            maxValue,
        )
    }
}

@Composable
private fun CanvasCircularSlider(
    primaryColor: Color,
    secondaryColor: Color,
    currentValue: Int,
    minValue: Int,
    maxValue: Int,
) {
    val workingValue = (currentValue - minValue) / ((maxValue - minValue) / 100)
    val convertedMaxValue = (maxValue - minValue) / ((maxValue - minValue) / 100)

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }


    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val width = size.width
        val height = size.height
        val circleThickness = width / 40f

        val circleRadiusCanvas = width / 3
        circleCenter = Offset(x = width / 2f, y = height / 2f)

        drawCircle(
            style = Stroke(
                width = circleThickness
            ),
            color = secondaryColor,
            radius = circleRadiusCanvas,
            center = circleCenter
        )

        filledSliderLine(
            workingValue,
            convertedMaxValue,
            circleThickness,
            circleRadiusCanvas,
            primaryColor
        )

        centerText(currentValue, circleCenter)
    }
}

fun DrawScope.centerText(value: Int, circleCenter: Offset) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            "${value}%",
            circleCenter.x,
            circleCenter.y + 3.dp.toPx(),
            Paint().apply {
                textSize = 9.sp.toPx()
                textAlign = Paint.Align.CENTER
                color = Color.White.toArgb()
            }
        )
    }
}

private fun DrawScope.filledSliderLine(
    positionValue: Int,
    convertedMaxValue: Int,
    circleThickness: Float,
    circularRadius: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = 270f,
        sweepAngle = (360f / convertedMaxValue) * positionValue.toFloat(),
        style = Stroke(
            width = circleThickness,
            cap = StrokeCap.Round
        ),
        useCenter = false,
        size = Size(
            width = circularRadius * 2f,
            height = circularRadius * 2f
        ),
        topLeft = Offset(
            size.width / 2f - circularRadius,
            size.height / 2f - circularRadius,
        )
    )
}

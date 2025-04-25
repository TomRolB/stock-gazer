package com.example.stockgazer.util

import java.text.DecimalFormat

private val df = DecimalFormat("#.###")


fun Double.asPercentageString() = df.format(this) + "%"

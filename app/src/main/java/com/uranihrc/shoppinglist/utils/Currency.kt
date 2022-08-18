package com.uranihrc.shoppinglist.utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun formatPrice(price: Float):String{
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.HALF_EVEN
    return df.format(price)
}
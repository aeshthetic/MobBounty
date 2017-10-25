package me.aeshthetic.mobbounty

import net.md_5.bungee.api.chat.TextComponent
import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round(places: Int): Double {
    if (places < 0) throw IllegalArgumentException()
    var bd = BigDecimal(this)
    bd = bd.setScale(places, RoundingMode.HALF_UP)
    return bd.toDouble()
}


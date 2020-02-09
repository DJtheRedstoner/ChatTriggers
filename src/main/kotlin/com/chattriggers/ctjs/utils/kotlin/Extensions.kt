package com.chattriggers.ctjs.utils.kotlin

import com.fasterxml.jackson.core.Version
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.client.renderer.Tessellator

fun ITextComponent.getStyling(): TextStyle =
    //#if MC<=10809
    this.chatStyle

//#else
//$$ this.style
//#endif
fun TextStyle.getClick(): TextClickEvent? =
    //#if MC<=10809
    chatClickEvent
//#else
//$$ clickEvent
//#endif

fun TextStyle.getHover(): TextHoverEvent? =
    //#if MC<=10809
    chatHoverEvent
//#else
//$$ hoverEvent
//#endif

fun Tessellator.getRenderer(): WorldRenderer =
    //#if MC<=10809
    worldRenderer
//#else
//$$ buffer
//#endif

operator fun String.times(times: Number): String {
    val stringBuilder = StringBuilder()

    for (i in 0..times.toInt()) {
        stringBuilder.append(this)
    }

    return stringBuilder.toString()
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

fun String.toVersion(): Version {
    val split = this.split(".").map(String::toInt)
    return Version(split[0], split[1], split[2], null, null, null)
}
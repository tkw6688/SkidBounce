package net.ccbluex.liquidbounce.features.module.modules.skid

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.ListValue

object IqChanger : Module("IqChanger", ModuleCategory.SKID) {

    private val mode by ListValue("Mode", arrayOf("Simple", "Boost", "Reduce"), "Simple")
    private val value by FloatValue("Value", 0F, 0F..1000F) { IqChanger.mode in arrayOf("Simple", "Boost", "Reduce") }

    override val tag
        get() = IqChanger.mode

}
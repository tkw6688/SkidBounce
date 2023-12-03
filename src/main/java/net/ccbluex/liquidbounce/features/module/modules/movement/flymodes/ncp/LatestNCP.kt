package net.ccbluex.liquidbounce.features.module.modules.movement.flymodes.ncp

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.flymodes.FlyMode
import net.ccbluex.liquidbounce.utils.MovementUtils.strafe
import net.ccbluex.liquidbounce.utils.PacketUtils_cs
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly.lncp_addValue_cs
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly.lncp_teleportValue_cs
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly.lncp_timerValue_cs
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook


object LatestNCP : FlyMode("LatestNCP") {
    private var started = false
    private var notUnder = false
    private var clipped = false
    private var offGroundTicks = 0
    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        if (mc.thePlayer.onGround) {
            offGroundTicks = 0
        } else offGroundTicks++
        if (lncp_timerValue_cs.get()) {
            if (!mc.thePlayer.onGround) {
                mc.timer.timerSpeed = 0.4f
            } else {
                mc.timer.timerSpeed = 1.0f
            }
        }
        val bb = mc.thePlayer.entityBoundingBox.offset(0.0, 1.0, 0.0)
        if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty() || started) {
            when (offGroundTicks) {
                0 -> if (notUnder) {
                    if (clipped) {
                        started = true
                        strafe((9.5 + lncp_addValue_cs.get()).toFloat())
                        mc.thePlayer.motionY = 0.42
                        notUnder = false
                    }
                }

                1 -> if (started) strafe((8.0 + lncp_addValue_cs.get()).toFloat())
            }
        } else {
            notUnder = true
            if (clipped) return
            clipped = true
            if (lncp_teleportValue_cs.get()) {
                PacketUtils_cs.sendPacketNoEvent(
                    C06PacketPlayerPosLook(
                        mc.thePlayer.posX,
                        mc.thePlayer.posY,
                        mc.thePlayer.posZ,
                        mc.thePlayer.rotationYaw,
                        mc.thePlayer.rotationPitch,
                        false
                    )
                )
                PacketUtils_cs.sendPacketNoEvent(
                    C06PacketPlayerPosLook(
                        mc.thePlayer.posX,
                        mc.thePlayer.posY - 0.1,
                        mc.thePlayer.posZ,
                        mc.thePlayer.rotationYaw,
                        mc.thePlayer.rotationPitch,
                        false
                    )
                )
                PacketUtils_cs.sendPacketNoEvent(
                    C06PacketPlayerPosLook(
                        mc.thePlayer.posX,
                        mc.thePlayer.posY,
                        mc.thePlayer.posZ,
                        mc.thePlayer.rotationYaw,
                        mc.thePlayer.rotationPitch,
                        false
                    )
                )
            }
        }
        strafe()
    }

    override fun onEnable() {
        notUnder = false
        started = false
        clipped = false
    }
}
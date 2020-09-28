package com.chattriggers.ctjs.mixin

import net.minecraft.client.audio.SoundHandler
import net.minecraft.client.audio.SoundManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(SoundHandler::class)
interface SoundHandlerAccessor {
    @Accessor("sndManager")
    fun getSoundManager(): SoundManager
}

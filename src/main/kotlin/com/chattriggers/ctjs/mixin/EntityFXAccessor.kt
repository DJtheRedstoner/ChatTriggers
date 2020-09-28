package com.chattriggers.ctjs.mixin

import net.minecraft.client.particle.EntityFX
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(EntityFX::class)
interface EntityFXAccessor {
    @Accessor("particleMaxAge")
    fun setMaxAge(maxAge: Int)
}

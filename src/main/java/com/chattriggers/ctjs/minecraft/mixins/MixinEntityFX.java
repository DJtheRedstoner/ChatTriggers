package com.chattriggers.ctjs.minecraft.mixins;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface MixinEntityFX {
    @Accessor
    int getParticleMaxAge();

    @Accessor
    void setParticleMaxAge(int particleMaxAge);
}

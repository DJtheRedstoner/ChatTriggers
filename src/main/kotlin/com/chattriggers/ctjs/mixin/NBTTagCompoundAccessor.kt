package com.chattriggers.ctjs.mixin

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(NBTTagCompound::class)
interface NBTTagCompoundAccessor {
    @Accessor
    fun getTagMap(): Map<String, NBTBase>
}

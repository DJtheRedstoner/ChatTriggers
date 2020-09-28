package com.chattriggers.ctjs.mixin

import net.minecraft.client.gui.GuiScreenBook
import net.minecraft.nbt.NBTTagList
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(GuiScreenBook::class)
interface GuiScreenBookAccessor {
    @Accessor
    fun getBookPages(): NBTTagList

    @Accessor
    fun setBookPages(pages: NBTTagList)

    @Accessor("currPage")
    fun getCurrentPage(): Int

    @Accessor("currPage")
    fun setCurrentPage(page: Int)
}

package com.chattriggers.ctjs.launch.plugin

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.Descriptor

fun injectGuiMainMenu() = inject {
    className = "net/minecraft/client/gui/GuiMainMenu"
    methodName = "drawScreen"
    methodDesc = "(IIF)V"

    at = At(
        InjectionPoint.INVOKE(
            Descriptor(
                "net/minecraftforge/client/ForgeHooksClient",
                "renderMainMenu",
                //#if MC==10809
                "(Lnet/minecraft/client/gui/GuiMainMenu;Lnet/minecraft/client/gui/FontRenderer;II)V"
                //#else
                //$$ "(Lnet/minecraft/client/gui/GuiMainMenu;Lnet/minecraft/client/gui/FontRenderer;IILjava/lang/String;)Ljava/lang/String;"
                //#endif
            )
        )
    )

    methodMaps = mapOf("func_73863_a" to "drawScreen")

    insnList {
        invokeKObjectFunction("com/chattriggers/ctjs/utils/UpdateChecker", "drawUpdateMessage", "()V")
    }
}

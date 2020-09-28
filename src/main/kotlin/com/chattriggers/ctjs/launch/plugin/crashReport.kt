package com.chattriggers.ctjs.launch.plugin

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject

fun injectCrashReport() = inject {
    className = "net/minecraft/crash/CrashReport"
    methodName = "populateEnvironment"
    methodDesc = "()V"
    at = At(InjectionPoint.HEAD)

    //#if MC>=11202
    //$$ fieldMaps = mapOf("systemDetailsCategory" to "field_85061_c")
    //$$ methodMaps = mapOf("func_71504_g" to "populateEnvironment")
    //#else
    fieldMaps = mapOf("theReportCategory" to "field_85061_c")
    methodMaps = mapOf("func_71504_g" to "populateEnvironment")
    //#endif

    insnList {
        invokeKObjectFunction("com/chattriggers/ctjs/launch/AsmUtils", "addCrashSectionCallable", "(L$CRASH_REPORT_CATEGORY;)V") {
            //#if MC>=11202
            //$$ getLocalField(className, "systemDetailsCategory", "L$CRASH_REPORT_CATEGORY;")
            //#else
            getLocalField(className, "theReportCategory", "L$CRASH_REPORT_CATEGORY;")
            //#endif
        }
    }
}

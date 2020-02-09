package com.chattriggers.ctjs.launch.plugin

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*

fun injectNetHandlerPlayClient() = inject {
    className = "net/minecraft/client/network/NetHandlerPlayClient"
    methodName = "addToSendQueue"
    methodDesc = "(L$PACKET;)V"

    at = At(InjectionPoint.HEAD)

    insnList {
        createInstance(CANCELLABLE_EVENT, "()V")
        val event = astore()

        getStatic(TRIGGER_TYPE, "PACKET_SENT", "L$TRIGGER_TYPE;")
        invokeVirtual(TRIGGER_TYPE, "triggerAll", "([Ljava/lang/Object;)V") {
            array(2, "java/lang/Object") {
                aadd {
                    aload(1)
                }

                aadd {
                    load(event)
                }
            }
        }

        load(event)
        invoke(InvokeType.VIRTUAL, CANCELLABLE_EVENT, "isCancelled", "()Z")

        ifClause(JumpCondition.FALSE) {
            methodReturn()
        }
    }
}
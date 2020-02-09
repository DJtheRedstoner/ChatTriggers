package com.chattriggers.ctjs.launch.plugin

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*

fun injectRenderManager() = inject {
    className = "net/minecraft/client/renderer/entity/RenderManager"
    methodName = "doRenderEntity"
    methodDesc = "(L$ENTITY;DDDFFZ)Z"

    at = At(InjectionPoint.HEAD)

    insnList {
        createInstance(CANCELLABLE_EVENT, "()V")
        val event = astore()

        getStatic(TRIGGER_TYPE, "RENDER_ENTITY", "L$TRIGGER_TYPE;")
        invokeVirtual(TRIGGER_TYPE, "triggerAll", "([Ljava/lang/Object;)V") {
            array(4, "java/lang/Object") {
                aadd {
                    createInstance("com/chattriggers/ctjs/minecraft/wrappers/objects/Entity", "(L$ENTITY;)V") {
                        aload(1)
                    }
                }

                aadd {
                    createInstance("org/lwjgl/util/vector/Vector3f", "(FFF)V") {
                        dload(2)
                        d2f()
                        dload(4)
                        d2f()
                        dload(6)
                        d2f()
                    }
                }

                aadd {
                    invokeStatic("java/lang/Float", "valueOf", "(F)Ljava/lang/Float;") {
                        fload(9)
                    }
                }

                aadd {
                    load(event)
                }
            }
        }

        load(event)
        invoke(InvokeType.VIRTUAL, CANCELLABLE_EVENT, "isCancelled", "()Z")

        ifClause(JumpCondition.FALSE) {
            int(0)
            ireturn()
        }
    }
}
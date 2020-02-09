package com.chattriggers.ctjs.launch.plugin

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*

fun injectEffectRenderer() = inject {
    className = EFFECT_RENDERER
    methodName = "spawnEffectParticle"
    methodDesc = "(IDDDDDD[I)L$ENTITY_FX;"

    at = At(
        InjectionPoint.INVOKE(
            Descriptor(
                EFFECT_RENDERER,
                "addEffect",
                "(L$ENTITY_FX;)V"
            )
        )
    )

    insnList {
        val entityFx = astore()

        createInstance(CANCELLABLE_EVENT, "()V")
        val event = astore()

        field(FieldAction.GET_STATIC, TRIGGER_TYPE, "SPAWN_PARTICLE", "L$TRIGGER_TYPE;")

        invoke(InvokeType.VIRTUAL, TRIGGER_TYPE, "triggerAll", "([Ljava/lang/Object;)V") {
            array(3, "java/lang/Object") {
                aadd {
                    createInstance("com/chattriggers/ctjs/minecraft/wrappers/objects/Particle", "(L$ENTITY_FX;)V") {
                        load(entityFx)
                    }
                }

                aadd {
                    invoke(InvokeType.STATIC, "net/minecraft/util/EnumParticleTypes", "getParticleFromId", "(I)Lnet/minecraft/util/EnumParticleTypes;") {
                        iload(1)
                    }
                }

                aadd { load(event) }
            }
        }

        load(event)
        invoke(InvokeType.VIRTUAL, CANCELLABLE_EVENT, "isCancelled", "()Z")

        ifClause(JumpCondition.TRUE) {
            aconst_null()
            areturn()
        }

        load(entityFx)
    }
}
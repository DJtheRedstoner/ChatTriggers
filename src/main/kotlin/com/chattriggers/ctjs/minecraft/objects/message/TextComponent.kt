package com.chattriggers.ctjs.minecraft.objects.message

import com.chattriggers.ctjs.minecraft.libs.ChatLib
import com.chattriggers.ctjs.utils.kotlin.*

//#if MC<=10809
//#else
//$$ import net.minecraft.util.text.*
//$$ import net.minecraft.util.text.event.*
//#if MC>=11602
//$$ import net.minecraft.util.IReorderingProcessor
//#endif
//#endif

@External
class TextComponent(private val component: MCITextComponent) : MCITextComponent by component {
    private var clickAction: MCClickEventAction? = null
    private var clickValue: String? = null
    private var hoverAction: MCHoverEventAction? = null
    private var hoverValue: Any? = null

    init {
        //#if MC>=11202
        //$$ val clickEvent: MCClickEvent? = component.style.clickEvent
        //#else
        val clickEvent: MCClickEvent? = component.chatStyle.chatClickEvent
        //#endif

        if (clickEvent != null) {
            clickAction = clickEvent.action
            clickValue = clickEvent.value
        }

        //#if MC>=11202
        //$$ val hoverEvent: MCHoverEvent? = component.style.hoverEvent
        //#else
        val hoverEvent: MCHoverEvent? = component.chatStyle.chatHoverEvent
        //#endif

        if (hoverEvent != null) {
            hoverAction = hoverEvent.action
            //#if FABRIC
            //$$ hoverValue = hoverEvent.getValue(hoverAction)
            //#elseif MC>=11602
            //$$ hoverValue = hoverEvent.getParameter(hoverAction)
            //#else
            hoverValue = hoverEvent.value
            //#endif
        }
    }

    /**
     * Creates a TextComponent from a string.
     * @param text the text string in the component.
     */
    @JvmOverloads
    constructor(text: String, isFormatted: Boolean = true) : this(fromString(text, isFormatted))

    /**
     * Sets the click action and value of the component.
     * See [TextComponent.setClickAction] for possible click actions.
     * @param action the click action
     * @param value the click value
     */
    fun withClick(action: MCClickEventAction, value: String) = TextComponent(make(
        component.formattedText,
        makeClickEvent(action, value),
        makeHoverEvent()
    ))

    /**
     * @return the current click action
     */
    fun getClickAction(): MCClickEventAction? = this.clickAction

    /**
     * Sets the action to be performed when the component is clicked on.
     * Possible actions include:
     * - open_url
     * - open_file
     * - run_command
     * - suggest_command
     * - change_page
     * @param action the click action
     */
    fun withClickAction(action: MCClickEventAction) = TextComponent(make(
        component.formattedText,
        makeClickEvent(action),
        makeHoverEvent()
    ))

    /**
     * @return the current click value
     */
    fun getClickValue(): String? = this.clickValue

    /**
     * Sets the value to be used by the click action.
     * See [TextComponent.setClickAction] for possible click actions.
     * @param value the click value
     */
    fun withClickValue(value: String) = TextComponent(make(
        component.formattedText,
        makeClickEvent(clickValue = value),
        makeHoverEvent()
    ))

    /**
     * Sets the hover action and value of the component.
     * See [TextComponent.withHoverValue] for possible hover actions.
     * @param action the hover action
     * @param value the hover value
     */
    fun withHover(action: MCHoverEventAction, value: String) = TextComponent(make(
        component.formattedText,
        makeClickEvent(),
        makeHoverEvent(action, value)
    ))

    /**
     * @return the current hover action
     */
    fun getHoverAction(): MCHoverEventAction? = this.hoverAction

    /**
     * Sets the action to be performed when the component is hovered over.
     * Hover action is set to 'show_text' by default.
     * Possible actions include:
     * - show_text
     * - show_achievement
     * - show_item
     * - show_entity
     * @param action the hover action
     */
    fun withHoverAction(action: MCHoverEventAction) = TextComponent(make(
        component.formattedText,
        makeClickEvent(),
        makeHoverEvent(hoverAction = action)
    ))

    /**
     * @return the current hover value
     */
    fun getHoverValue(): Any? = this.hoverValue

    /**
     * Sets the value to be used by the hover action.
     * See [TextComponent.withHoverValue] for possible hover actions.
     * @param value the hover value
     */
    fun withHoverValue(value: String) = TextComponent(make(
        component.formattedText,
        makeClickEvent(),
        makeHoverEvent(hoverValue = value)
    ))

    /**
     * Shows the component in chat as a new [Message]
     */
    fun chat() = Message(this).chat()

    /**
     * Shows the component on the actionbar as a new [Message]
     */
    fun actionBar() = Message(this).actionBar()

    override fun toString() =
        "TextComponent{text:${component.formattedText},hoverAction:$hoverAction,hoverValue:$hoverValue,clickAction:$clickAction,clickValue:$clickValue}"

    private fun makeClickEvent(
        clickAction: MCClickEventAction? = this.clickAction,
        clickValue: String? = this.clickValue
    ): MCClickEvent? {
        if (clickAction == null || clickValue == null)
            return null

        return MCClickEvent(clickAction, clickValue)
    }

    private fun makeHoverEvent(
        hoverAction: MCHoverEventAction? = this.hoverAction,
        hoverValue: Any? = this.hoverValue
    ): MCHoverEvent? {
        if (hoverAction == null || hoverValue == null)
            return null

        return MCHoverEvent(
            hoverAction,
            if (hoverValue is MCITextComponent) hoverValue else fromString(hoverValue.toString())
        )
    }

    companion object {
        @JvmOverloads
        fun fromString(string: String, isFormatted: Boolean = true) =
            MCBaseTextComponent(if (isFormatted) ChatLib.addColor(string) else string)

        private fun make(text: String, clickEvent: MCClickEvent? = null, hoverEvent: MCHoverEvent? = null): MCITextComponent {
            val component = fromString(text)

            if (clickEvent != null) {
                //#if MC>=11202
                //$$ component.style.clickEvent = clickEvent
                //#else
                component.chatStyle.chatClickEvent = clickEvent
                //#endif
            }

            if (hoverEvent != null) {
                //#if MC>=11202
                //$$ component.style.hoverEvent = hoverEvent
                //#else
                component.chatStyle.chatHoverEvent = hoverEvent
                //#endif
            }

            return component
        }
    }
}

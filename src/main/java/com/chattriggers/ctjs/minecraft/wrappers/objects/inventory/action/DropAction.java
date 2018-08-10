package com.chattriggers.ctjs.minecraft.wrappers.objects.inventory.action;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.inventory.ClickType;

@Accessors(chain = true)
public class DropAction extends Action {
    /**
     * Whether the click should act as if control is being held (defaults to false)
     *
     * @param holdingCtrl to hold ctrl or not
     */
    @Setter @Getter
    private boolean holdingCtrl = false;

    public DropAction(int slot, int windowId) {
        super(slot, windowId);
    }

    @Override
    public void complete() {
        doClick(
                holdingCtrl ? 1 : 0,
                ClickType.THROW
        );
    }
}

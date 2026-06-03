package com.droptweaks;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ClientHandler {
    private static final int DOUBLE_CLICK_THRESHOLD_MS = 250;

    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean prevQDown;
    private int qClickCount;
    private long lastQPressTime;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        boolean qDown = Keyboard.isKeyDown(Keyboard.KEY_Q);
        boolean newPress = qDown && !prevQDown;
        long now = System.currentTimeMillis();

        if (newPress) {
            if (qClickCount == 1 && now - lastQPressTime <= DOUBLE_CLICK_THRESHOLD_MS) {
                qClickCount = 2;
            } else {
                qClickCount = 1;
            }
            lastQPressTime = now;
        } else if (!qDown && qClickCount != 0 && now - lastQPressTime > DOUBLE_CLICK_THRESHOLD_MS) {
            qClickCount = 0;
        }

        if (qDown) {
            if (newPress && qClickCount == 2) {
                dropCurrentSlot(true);
            } else if (qClickCount != 2) {
                dropCurrentSlot(false);
            }
        }

        prevQDown = qDown;
    }

    private void dropCurrentSlot(boolean dropAll) {
        if (mc.currentScreen instanceof GuiContainer) {
            GuiContainer gui = (GuiContainer) mc.currentScreen;
            Slot hovered = getHoveredSlot(gui);
            if (hovered != null && hovered.getHasStack()) {
                clickSlot(gui.inventorySlots.windowId, hovered.slotNumber, dropAll ? 1 : 0);
            }
        } else {
            ItemStack item = mc.thePlayer.inventory.getCurrentItem();
            if (item != null) {
                int windowId = mc.thePlayer.openContainer.windowId;
                int slotId = mc.thePlayer.inventory.currentItem + 36;
                clickSlot(windowId, slotId, dropAll ? 1 : 0);
            }
        }
    }

    private void clickSlot(int windowId, int slotId, int button) {
        try {
            mc.playerController.windowClick(windowId, slotId, button, 4, mc.thePlayer);
        } catch (Throwable t) {
            // ignore
        }
    }

    private Slot getHoveredSlot(GuiContainer gui) {
        try {
            java.lang.reflect.Field f;
            try {
                f = GuiContainer.class.getDeclaredField("theSlot");
            } catch (NoSuchFieldException e) {
                f = GuiContainer.class.getDeclaredField("field_147002_h");
            }
            f.setAccessible(true);
            Object o = f.get(gui);
            if (o instanceof Slot) return (Slot) o;
        } catch (Exception e) {
        }
        return null;
    }
}

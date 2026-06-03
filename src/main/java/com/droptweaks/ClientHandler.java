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
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            if (mc.currentScreen instanceof GuiContainer) {
                GuiContainer gui = (GuiContainer) mc.currentScreen;
                Slot hovered = getHoveredSlot(gui);
                if (hovered != null && hovered.getHasStack()) {
                    try {
                        int windowId = gui.inventorySlots.windowId;
                        int slotId = hovered.slotNumber;
                        mc.playerController.windowClick(windowId, slotId, 1, 4, mc.thePlayer);
                    } catch (Throwable t) {
                        // ignore
                    }
                }
            } else {
                ItemStack item = mc.thePlayer.inventory.getCurrentItem();
                if (item != null) {
                    try {
                        int windowId = mc.thePlayer.openContainer.windowId;
                        int slotId = mc.thePlayer.inventory.currentItem + 36;
                        mc.playerController.windowClick(windowId, slotId, 1, 4, mc.thePlayer);
                    } catch (Throwable t) {
                        // ignore
                    }
                }
            }
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

# DropTweaks
DropTweaks is a client-side Minecraft Forge mod for 1.8.9 that enhances item dropping. It lets you drop the item under your mouse in inventory screens, or the currently held hotbar item when not in a GUI, using the normal Q key. A quick double-tap of Q triggers a full-stack drop.  

Every tick, the mod watches for Q presses and distinguishes single vs double taps. If an inventory GUI is open, it drops the hovered slot item; otherwise it drops the currently held hotbar item. A regular press drops one item and a quick double press drops the full stack. The drop action uses Minecraft’s window click logic and ignores any errors silently.

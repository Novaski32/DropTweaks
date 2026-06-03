package com.droptweaks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

@Mod(modid = "droptweaks", name = "DropTweaks", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class DropTweaks {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new ClientHandler());
    }
}

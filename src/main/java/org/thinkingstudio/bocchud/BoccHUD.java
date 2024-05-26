package org.thinkingstudio.bocchud;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.minihud.InitHandler;
import fi.dy.masa.minihud.Reference;
import fi.dy.masa.minihud.gui.GuiConfigs;
import net.minecraftforge.fml.common.Mod;
import org.thinkingstudio.mafglib.util.ForgePlatformUtils;

@Mod(Reference.MOD_ID)
public class BoccHUD {
    public BoccHUD() {
        ForgePlatformUtils.getInstance().getClientModIgnoredServerOnly();
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        ForgePlatformUtils.getInstance().registerModConfigScreen(Reference.MOD_ID, (screen) -> {
            GuiConfigs gui = new GuiConfigs();
            gui.setParent(screen);
            return gui;
        });
    }
}

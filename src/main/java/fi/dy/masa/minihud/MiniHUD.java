package fi.dy.masa.minihud;

import fi.dy.masa.malilib.compat.neoforge.ForgePlatformUtils;
import fi.dy.masa.minihud.gui.GuiConfigs;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.minihud.config.Configs;

@Mod(Reference.MOD_ID)
public class MiniHUD {
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
    public static final String CHANNEL_CARPET_CLIENT = "CarpetClient";

    public MiniHUD() {
        if (FMLLoader.getDist().isClient()) {
            this.onInitializeClient();
        }
    }

    public void onInitializeClient() {
        ForgePlatformUtils.getInstance().getClientModIgnoredServerOnly();
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        ForgePlatformUtils.getInstance().getMod(Reference.MOD_ID).registerModConfigScreen((screen) -> {
            GuiConfigs gui = new GuiConfigs();
            gui.setParent(screen);
            return gui;
        });
    }

    public static void printDebug(String key, Object... args) {
        if (Configs.Generic.DEBUG_MESSAGES.getBooleanValue()) {
            logger.info(key, args);
        }
    }
}

package fi.dy.masa.minihud;

import fi.dy.masa.malilib.compat.forge.ForgePlatformCompat;
import fi.dy.masa.minihud.gui.GuiConfigs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.minihud.config.Configs;

@Mod(Reference.MOD_ID)
public class MiniHUD {
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
    public static final String CHANNEL_CARPET_CLIENT = "CarpetClient";

    public MiniHUD() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onInitializeClient);
    }

    public void onInitializeClient(FMLClientSetupEvent event) {
        ForgePlatformCompat.getInstance().getModClientExtensionPoint();
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        ForgePlatformCompat.getInstance().getMod(Reference.MOD_ID).registerModConfigScreen((screen) -> {
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

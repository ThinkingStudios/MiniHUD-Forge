package fi.dy.masa.minihud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fi.dy.masa.minihud.config.Configs;

public class MiniHUD {
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
    public static final String CHANNEL_CARPET_CLIENT = "CarpetClient";

    public static void printDebug(String key, Object... args) {
        if (Configs.Generic.DEBUG_MESSAGES.getBooleanValue()) {
            logger.info(key, args);
        }
    }
}

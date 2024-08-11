package fi.dy.masa.minihud;

import net.minecraft.MinecraftVersion;
import fi.dy.masa.malilib.util.StringUtils;

public class Reference
{
    public static final String ORIGINAL_ID = "minihud"; // some method need this
    public static final String MOD_ID = "bocchud";
    public static final String MOD_NAME = "BoccHUD";
    public static final String MOD_VERSION = StringUtils.getModVersionString(MOD_ID);
    public static final String MC_VERSION = MinecraftVersion.CURRENT.getName();
    public static final String MOD_TYPE = "neoforge";
    public static final String MOD_STRING = MOD_ID + "-" + MOD_TYPE + "-" + MC_VERSION + "-" + MOD_VERSION;
}

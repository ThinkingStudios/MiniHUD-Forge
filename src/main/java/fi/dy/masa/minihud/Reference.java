package fi.dy.masa.minihud;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.MinecraftVersion;

public class Reference
{
    public static final String MOD_ID = "bocchud";
    public static final String MOD_NAME = "BoccHUD";
    public static final String MOD_VERSION = StringUtils.getModVersionString(MOD_ID);
    public static final String MC_VERSION = MinecraftVersion.CURRENT.getName();
    public static final String MOD_STRING = MOD_ID+"-"+MC_VERSION+"-"+MOD_VERSION;
}

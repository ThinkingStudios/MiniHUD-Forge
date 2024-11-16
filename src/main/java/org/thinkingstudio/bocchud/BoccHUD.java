package org.thinkingstudio.bocchud;

import fi.dy.masa.minihud.MiniHUD;
import fi.dy.masa.minihud.Reference;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(value = Reference.MOD_ID, dist = Dist.CLIENT)
public class BoccHUD {
    public BoccHUD() {
        if (FMLLoader.getDist().isClient()) {
            MiniHUD.onInitialize();
        }
    }
}

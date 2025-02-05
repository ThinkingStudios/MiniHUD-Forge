package org.thinkingstudio.bocchud;

import fi.dy.masa.minihud.MiniHUD;
import fi.dy.masa.minihud.Reference;
import fi.dy.masa.minihud.compat.modmenu.ModMenuImpl;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.thinkingstudio.mafglib.loader.FoxifiedLoader;

@Mod(value = Reference.MOD_ID, dist = Dist.CLIENT)
public class BoccHUD {
    public BoccHUD(ModContainer modContainer) {
        if (FMLLoader.getDist().isClient()) {
            FoxifiedLoader.registerExtensionPoint(modContainer, IConfigScreenFactory.class, new ModMenuImpl().getModConfigScreenFactory());
            MiniHUD.onInitialize();
        }
    }
}

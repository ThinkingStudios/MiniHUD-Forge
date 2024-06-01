package org.thinkingstudio.bocchud;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.minihud.InitHandler;
import fi.dy.masa.minihud.Reference;
import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.gui.GuiConfigs;
import fi.dy.masa.minihud.util.MiscUtils;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import org.thinkingstudio.mafglib.util.ForgePlatformUtils;

import java.util.List;

@Mod(Reference.MOD_ID)
public class BoccHUD {
    public BoccHUD() {
        if (FMLLoader.getDist().isClient()) {
            ForgePlatformUtils.getInstance().getClientModIgnoredServerOnly();
            InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
            ForgePlatformUtils.getInstance().registerModConfigScreen(Reference.MOD_ID, (screen) -> {
                GuiConfigs gui = new GuiConfigs();
                gui.setParent(screen);
                return gui;
            });

            MinecraftForge.EVENT_BUS.<ItemTooltipEvent>addListener(EventPriority.HIGHEST, event -> {
                ItemStack itemStack = event.getItemStack();
                List<Text> list = event.getToolTip();

                if (Configs.Generic.AXOLOTL_TOOLTIPS.getBooleanValue() &&
                        itemStack.getItem() == Items.AXOLOTL_BUCKET)
                {
                    MiscUtils.addAxolotlTooltip(itemStack, list);
                    return;
                }

                if (Configs.Generic.BEE_TOOLTIPS.getBooleanValue() &&
                        itemStack.getItem() instanceof BlockItem &&
                        ((BlockItem) itemStack.getItem()).getBlock() instanceof BeehiveBlock)
                {
                    MiscUtils.addBeeTooltip(itemStack, list);
                }

                if (Configs.Generic.HONEY_TOOLTIPS.getBooleanValue() &&
                        itemStack.getItem() instanceof BlockItem &&
                        ((BlockItem) itemStack.getItem()).getBlock() instanceof BeehiveBlock)
                {
                    MiscUtils.addHoneyTooltip(itemStack, list);
                }
            });
        }
    }
}

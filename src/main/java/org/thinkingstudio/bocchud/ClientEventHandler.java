package org.thinkingstudio.bocchud;

import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.util.MiscUtils;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ClientEventHandler {
    public static void register() {
        MinecraftForge.EVENT_BUS.<ItemTooltipEvent>addListener(EventPriority.HIGHEST, event -> {
            var itemStack = event.getItemStack();
            var list = event.getToolTip();

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

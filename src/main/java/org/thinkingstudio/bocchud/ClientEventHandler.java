package org.thinkingstudio.bocchud;

import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.util.MiscUtils;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.List;

public class ClientEventHandler {
    public static void register() {
        MinecraftForge.EVENT_BUS.<ItemTooltipEvent>addListener(EventPriority.HIGHEST, event -> {
            ItemStack itemStack = event.getItemStack();
            List<Text> list = event.getToolTip();

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

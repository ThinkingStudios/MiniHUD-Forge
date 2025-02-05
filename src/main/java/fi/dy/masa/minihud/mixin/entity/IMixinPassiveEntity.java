package fi.dy.masa.minihud.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.entity.passive.PassiveEntity;

@Mixin(PassiveEntity.class)
public interface IMixinPassiveEntity
{
    @Accessor("breedingAge")
    int minihud_getRealBreedingAge();
}

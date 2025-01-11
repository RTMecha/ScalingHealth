package net.silentchaos512.scalinghealth.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.silentchaos512.scalinghealth.ScalingHealth;
import net.silentchaos512.scalinghealth.utils.ModifierHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LivingEntityHealthCapability implements ILivingEntityData, ICapabilitySerializable<CompoundTag> {
    public static Capability<ILivingEntityData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
    public static ResourceLocation NAME = ScalingHealth.getId("entity_health");

    private static final String NBT_HEALTH = "SHBonusHealth";

    private final LazyOptional<ILivingEntityData> holder = LazyOptional.of(() -> this);

    private float bonusHealth;
    private boolean refreshed = false;

    @Override
    public void addHealth(double hp, LivingEntity livingEntity) {
        bonusHealth += hp;
        ModifierHandler.setMaxHealth(livingEntity, bonusHealth, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public float getBonusHealth() {
        return bonusHealth;
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        if(!refreshed && livingEntity.tickCount > 2){
            refreshed = true;
            ModifierHandler.setMaxHealth(livingEntity, getBonusHealth(), AttributeModifier.Operation.ADDITION);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return INSTANCE.orEmpty(cap, holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat(NBT_HEALTH, bonusHealth);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        bonusHealth = nbt.getFloat(NBT_HEALTH);
    }

    public static boolean canAttachTo(ICapabilityProvider obj) {
        try {
            if (obj.getCapability(INSTANCE).isPresent()) {
                return false;
            }
        } catch (NullPointerException ex) {
            ScalingHealth.LOGGER.error("Failed to get capabilities from {}", obj);
            return false;
        }
        return obj instanceof LivingEntity;
    }
}

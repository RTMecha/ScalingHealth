package net.silentchaos512.scalinghealth.capability;

import net.minecraft.world.entity.LivingEntity;

public interface ILivingEntityData {
    void addHealth(double hp, LivingEntity livingEntity);

    float getBonusHealth();

    void tick(LivingEntity livingEntity);
}
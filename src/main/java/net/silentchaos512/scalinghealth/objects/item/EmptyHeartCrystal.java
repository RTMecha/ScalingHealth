package net.silentchaos512.scalinghealth.objects.item;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.silentchaos512.lib.util.EntityHelper;
import net.silentchaos512.scalinghealth.ScalingHealth;
import net.silentchaos512.scalinghealth.capability.IPlayerData;
import net.silentchaos512.scalinghealth.objects.Registration;
import net.silentchaos512.scalinghealth.utils.config.EnabledFeatures;
import net.silentchaos512.scalinghealth.utils.config.SHItems;
import net.silentchaos512.scalinghealth.utils.config.SHPlayers;
import net.silentchaos512.utils.MathUtils;

public class EmptyHeartCrystal extends StatBoosterItem {
    public EmptyHeartCrystal(Properties properties) {
        super(properties);
    }

    @Override
    protected int getLevelCost(Player player) {
        return SHItems.levelCostToUseHeartCrystal(player);
    }

    @Override
    protected boolean isStatIncreaseAllowed(Player player) {
        return EnabledFeatures.healthCrystalEnabled() &&
                SHPlayers.getPlayerData(player).getBonusHearts(player) > 0;
    }

    @Override
    protected boolean shouldConsume(Player player) {
        return EnabledFeatures.healthCrystalRegenEnabled() &&
                player.getHealth() > SHPlayers.startingHealth();
    }

    @Override
    protected void extraConsumeEffect(Player player) {
        //int current = (int) player.getHealth();
        //double healAmount = SHItems.heartCrystalHpBonusRegen();
        //EntityHelper.heal(player, (float) healAmount, true);
        //int newHealth = (int) player.getHealth();
        //if (!MathUtils.doublesEqual(current + healAmount, newHealth)) {
        //    ScalingHealth.LOGGER.warn("Another mod seems to have canceled healing from a heart container (player {})", player.getName());
        //}
    }

    @Override
    protected void increaseStat(Player player) {
        SHPlayers.getPlayerData(player).resetHeartCrystals(player);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return Registration.HEART_CRYSTAL_PARTICLE.get();
    }

    @Override
    protected SoundEvent getSoundEffect() {
        return Registration.HEART_CRYSTAL_USE.get();
    }

    @Override
    protected boolean shouldConsumeLevels() { return false; }
}

package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class KnockbackEffect extends Effect implements TargetEffect {

    private final double multiplier;
    private final boolean overwriteCurrentVelocity;

    public KnockbackEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        multiplier = configurationSection.getDouble("multiplier", 1.0);
        overwriteCurrentVelocity = configurationSection.getBoolean("overwriteCurrentVelocity", true);
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        Vector knockback = livingEntity.getEyeLocation().getDirection().clone().multiply(multiplier);
        if (overwriteCurrentVelocity) entity.setVelocity(knockback);
        else entity.setVelocity(entity.getVelocity().add(knockback));
        return false;
    }
}

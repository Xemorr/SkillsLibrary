package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
    public boolean useEffect(Entity livingEntity, Entity target) {
        Location location = livingEntity.getLocation();
        if (livingEntity instanceof Player player) {
            location = player.getEyeLocation();
        }
        Vector knockback = location.getDirection().clone().multiply(multiplier);
        if (overwriteCurrentVelocity) target.setVelocity(knockback);
        else target.setVelocity(target.getVelocity().add(knockback));
        return false;
    }
}

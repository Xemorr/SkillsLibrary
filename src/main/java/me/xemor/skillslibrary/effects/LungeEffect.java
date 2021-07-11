package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LungeEffect extends Effect implements EntityEffect, TargetEffect {

    private final double horizontalVelocity;
    private final double verticalVelocity;

    public LungeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        horizontalVelocity = configurationSection.getDouble("horizontalVelocity");
        verticalVelocity = configurationSection.getDouble("verticalVelocity");
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        setVelocity(entity);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        if (entity instanceof LivingEntity) {
            setVelocity((LivingEntity) entity);
        }
        return false;
    }

    public void setVelocity(LivingEntity entity) {
        Vector direction = entity.getEyeLocation().getDirection();
        double x = direction.getX() * horizontalVelocity;
        double z = direction.getZ() * horizontalVelocity;
        double y = direction.getY() * verticalVelocity;
        entity.setVelocity(new Vector(x, y, z));
    }
}

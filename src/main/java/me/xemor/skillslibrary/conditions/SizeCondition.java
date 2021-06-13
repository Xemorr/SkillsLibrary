package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Slime;

public class SizeCondition extends Condition implements EntityCondition, TargetCondition {

    private final int minimumSize;
    private final int maximumSize;

    public SizeCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        minimumSize = configurationSection.getInt("minimumSize", 0);
        maximumSize = configurationSection.getInt("maximumSize", 4);
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return checkCondition(boss);
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return checkCondition(target);
    }

    public boolean checkCondition(Entity entity) {
        if (entity instanceof Slime) {
            Slime slime = (Slime) entity;
            return minimumSize <= slime.getSize() && slime.getSize() <= maximumSize;
        }
        if (entity instanceof Phantom) {
            Phantom phantom = (Phantom) entity;
            return minimumSize <= phantom.getSize() && phantom.getSize() <= maximumSize;
        }
        return false;
    }

}

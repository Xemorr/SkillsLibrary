package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;

public class TamedCondition extends Condition implements TargetCondition {

    private boolean checkOwner;

    public TamedCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        checkOwner = configurationSection.getBoolean("checkOwner", true);
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        if (target instanceof Tameable) {
            Tameable tameable = (Tameable) target;
            if (checkOwner) return skillEntity.equals(tameable.getOwner());
            else return tameable.isTamed();
        }
        return false;
    }

}

package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;

public class TamedCondition extends Condition implements TargetCondition {

    private boolean checkOwner;

    public TamedCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        checkOwner = configurationSection.getBoolean("checkOwner", true);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        if (target instanceof Tameable) {
            Tameable tameable = (Tameable) target;
            if (checkOwner) return entity.equals(tameable.getOwner());
            else return tameable.isTamed();
        }
        return false;
    }

}

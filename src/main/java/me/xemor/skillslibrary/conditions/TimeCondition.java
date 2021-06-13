package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public class TimeCondition extends Condition implements EntityCondition {

    private final long minimumTime;
    private final long maximumTime;

    public TimeCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        minimumTime = configurationSection.getLong("minimumTime", 0);
        maximumTime = configurationSection.getLong("maximumTime", 24000);
    }


    @Override
    public boolean isTrue(LivingEntity livingEntity) {
        long time = livingEntity.getWorld().getTime();
        return  time >= minimumTime && time <= maximumTime;
    }
}

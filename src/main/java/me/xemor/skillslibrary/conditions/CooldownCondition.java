package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

public class CooldownCondition extends Condition implements EntityCondition {

    private final long cooldown;
    private final HashMap<UUID, Long> cooldownEndsMap = new HashMap<>();

    public CooldownCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        cooldown = Math.round(configurationSection.getDouble("cooldown", 10) * 1000);
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        double cooldownEnds = cooldownEndsMap.getOrDefault(boss.getUniqueId(), -1L);
        if (cooldownEnds < System.currentTimeMillis()) {
            cooldownEndsMap.put(boss.getUniqueId(), cooldown + System.currentTimeMillis());
            return true;
        }
        return false;
    }

}

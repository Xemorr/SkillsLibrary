package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class NPCCondition extends Condition implements EntityCondition, TargetCondition {
    private final boolean npc;

    public NPCCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        npc = configurationSection.getBoolean("npc", false);
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return boss.hasMetadata("NPC") == npc;
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return false;
    }
}


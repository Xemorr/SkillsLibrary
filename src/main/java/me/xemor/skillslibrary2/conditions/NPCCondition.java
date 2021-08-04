package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class NPCCondition extends Condition implements EntityCondition, TargetCondition {
    private final boolean npc;

    public NPCCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        npc = configurationSection.getBoolean("isNPC", false);
    }

    @Override
    public boolean isTrue(Entity boss) {
        return boss.hasMetadata("NPC") == npc;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return target.hasMetadata("NPC") == npc;
    }
}


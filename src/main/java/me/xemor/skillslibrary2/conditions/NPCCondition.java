package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class NPCCondition extends Condition implements EntityCondition, TargetCondition {
    private final boolean npc;

    public NPCCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        npc = configurationSection.getBoolean("isNPC", false);
    }

    @Override
    public boolean isTrue(Execution execution, Entity boss) {
        return boss.hasMetadata("NPC") == npc;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> target.hasMetadata("NPC") == npc);
    }
}


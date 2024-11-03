package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SneakCondition extends Condition implements EntityCondition, TargetCondition {

    private final boolean sneak;

    public SneakCondition(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        sneak = configurationSection.getBoolean("sneak", true);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof Player player) {
            return player.isSneaking() == sneak;
        }
        return true;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity otherEntity) {
        return SkillsLibrary.getFoliaHacks().runASAP(otherEntity, () -> isTrue(execution, otherEntity));
    }
}

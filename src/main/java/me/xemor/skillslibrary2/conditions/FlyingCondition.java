package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class FlyingCondition extends Condition implements EntityCondition, TargetCondition {
    public FlyingCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof Player player) {
            return player.isFlying();
        }
        return false;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}

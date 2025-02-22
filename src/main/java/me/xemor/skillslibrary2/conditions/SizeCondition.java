package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Slime;

import java.util.concurrent.CompletableFuture;

public class SizeCondition extends Condition implements EntityCondition, TargetCondition {

    private final int minimumSize;
    private final int maximumSize;

    public SizeCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        minimumSize = configurationSection.getInt("minimumSize", 0);
        maximumSize = configurationSection.getInt("maximumSize", 4);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return checkCondition(entity);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> checkCondition(target));
    }

    public boolean checkCondition(Entity entity) {
        if (entity instanceof Slime slime) {
            return minimumSize <= slime.getSize() && slime.getSize() <= maximumSize;
        }
        if (entity instanceof Phantom phantom) {
            return minimumSize <= phantom.getSize() && phantom.getSize() <= maximumSize;
        }
        return false;
    }

}

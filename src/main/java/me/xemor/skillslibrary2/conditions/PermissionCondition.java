package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class PermissionCondition extends Condition implements EntityCondition, TargetCondition {

    @CompulsoryJsonProperty
    private String permission;

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return entity.hasPermission(permission);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }

}

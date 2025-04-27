package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class NPCCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    @JsonAlias("isNPC")
    private boolean npc = true;

    @Override
    public boolean isTrue(Execution execution, Entity boss) {
        return boss.hasMetadata("NPC") == npc;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> target.hasMetadata("NPC") == npc);
    }
}


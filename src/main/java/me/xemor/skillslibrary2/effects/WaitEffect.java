package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.Duration;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class WaitEffect extends WrapperEffect implements EntityEffect, TargetEffect, ComplexLocationEffect, ItemStackEffect {

    @JsonPropertyWithDefault
    @JsonAlias("delay")
    private Duration ticksDelay = new Duration(20D);

    @Override
    public void useEffect(Execution execution, Entity entity) {
        SkillsLibrary.getFoliaHacks().getScheduling().entitySpecificScheduler(entity).runDelayed(
                () -> handleEffects(execution, entity),
                () -> {},
                ticksDelay.getDurationInTicks().orElse(1L)
        );
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().getScheduling().entitySpecificScheduler(target).runDelayed(
                () -> handleEffects(execution, entity, target),
                () -> {},
                ticksDelay.getDurationInTicks().orElse(1L)
        );
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        SkillsLibrary.getFoliaHacks().getScheduling().regionSpecificScheduler(location).runDelayed(
                () -> handleEffects(execution, entity, location),
                ticksDelay.getDurationInTicks().orElse(1L)
        );
    }

    @Override
    public void useEffect(Execution execution, Entity entity, ItemStack item) {
        SkillsLibrary.getFoliaHacks().getScheduling().entitySpecificScheduler(entity).runDelayed(
                () -> handleEffects(execution, entity, item),
                () -> {},
                ticksDelay.getDurationInTicks().orElse(1L)
        );
    }
}

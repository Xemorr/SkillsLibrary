package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.Duration;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;


/*
    This class was originally written by Creeves
 */
public class TimerEffect extends WrapperEffect implements EntityEffect, TargetEffect, ComplexLocationEffect, ItemStackEffect {

    @JsonPropertyWithDefault
    @JsonAlias("delay")
    private Duration ticksDelay = new Duration(1.0);
    @JsonPropertyWithDefault
    private Duration period = new Duration(1.0);
    @JsonPropertyWithDefault
    @JsonAlias("numberOfRepeats")
    private int repeats = 1;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        AtomicInteger count = new AtomicInteger();
        SkillsLibrary.getFoliaHacks().getScheduling().entitySpecificScheduler(entity)
                .runAtFixedRate(
                        (task) -> {
                            handleEffects(execution, entity);
                            if (count.addAndGet(1) >= repeats) {
                                task.cancel();
                            }
                        },
                        () -> {},
                        ticksDelay.getDurationInTicks().orElse(1L),
                        period.getDurationInTicks().orElse(1L)
                );
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        AtomicInteger count = new AtomicInteger();
        SkillsLibrary.getFoliaHacks().getScheduling().entitySpecificScheduler(target)
                .runAtFixedRate(
                        (task) -> {
                            handleEffects(execution, entity, target);
                            if (count.addAndGet(1) >= repeats) {
                                task.cancel();
                            }
                        },
                        () -> {},
                        ticksDelay.getDurationInTicks().orElse(1L),
                        period.getDurationInTicks().orElse(1L)
                );
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        AtomicInteger count = new AtomicInteger();
        SkillsLibrary.getFoliaHacks().getScheduling().regionSpecificScheduler(location)
                .runAtFixedRate(
                        (task) -> {
                            handleEffects(execution, entity, location);
                            if (count.addAndGet(1) >= repeats) {
                                task.cancel();
                            }
                        },
                        ticksDelay.getDurationInTicks().orElse(1L),
                        period.getDurationInTicks().orElse(1L)
                );
    }

    @Override
    public void useEffect(Execution execution, Entity entity, ItemStack item) {
        AtomicInteger count = new AtomicInteger();
        SkillsLibrary.getFoliaHacks().getScheduling().entitySpecificScheduler(entity)
                .runAtFixedRate(
                        (task) -> {
                            handleEffects(execution, entity, item);
                            if (count.addAndGet(1) >= repeats) {
                                task.cancel();
                            }
                        },
                        () -> {},
                        ticksDelay.getDurationInTicks().orElse(1L),
                        period.getDurationInTicks().orElse(1L)
                );
    }
}
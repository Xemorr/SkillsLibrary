package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.Duration;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


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
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                handleEffects(execution, entity);
                if (++count >= repeats) {
                    this.cancel();
                }
            }
        }.runTaskTimer(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L), period.getDurationInTicks().orElse(1L));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                handleEffects(execution, entity, target);
                if (++count >= repeats) {
                    this.cancel();
                }
            }
        }.runTaskTimer(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L), period.getDurationInTicks().orElse(1L));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                handleEffects(execution, entity, location);
                if (++count >= repeats) {
                    this.cancel();
                }
            }
        }.runTaskTimer(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L), period.getDurationInTicks().orElse(1L));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, ItemStack item) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                handleEffects(execution, entity, item);
                if (++count >= repeats) {
                    this.cancel();
                }
            }
        }.runTaskTimer(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L), period.getDurationInTicks().orElse(1L));
    }
}
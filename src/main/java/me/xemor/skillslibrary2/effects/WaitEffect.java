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

public class WaitEffect extends WrapperEffect implements EntityEffect, TargetEffect, ComplexLocationEffect, ItemStackEffect {

    @JsonPropertyWithDefault
    @JsonAlias("delay")
    private Duration ticksDelay = new Duration(20D);

    @Override
    public void useEffect(Execution execution, Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(execution, entity);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(execution, entity, target);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(execution, entity, location);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, ItemStack item) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(execution, entity, item);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay.getDurationInTicks().orElse(1L));
    }
}

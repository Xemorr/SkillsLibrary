package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitEffect extends WrapperEffect implements EntityEffect, TargetEffect, BlockEffect {

    private final long ticksDelay;

    public WaitEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ticksDelay = Math.round(configurationSection.getDouble("delay", 1) * 20);
    }

    @Override
    public boolean useEffect(Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(entity);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(entity, target);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Block block) {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleEffects(entity, block);
            }
        };
        return false;
    }
}

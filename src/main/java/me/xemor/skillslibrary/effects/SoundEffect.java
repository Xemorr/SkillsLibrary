package me.xemor.skillslibrary.effects;

import me.xemor.configurationdata.SoundData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class SoundEffect extends Effect implements EntityEffect, TargetEffect, BlockEffect {
    private final SoundData soundData;

    public SoundEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection soundSection = configurationSection.getConfigurationSection("sound");
        soundData = new SoundData(soundSection);
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        playSound(entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        playSound(entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity entity, Block block) {
        playSound(block.getLocation());
        return false;
    }

    private void playSound(Location location) {
        location.getWorld().playSound(location, soundData.getSound(), soundData.getVolume(), soundData.getPitch());
    }
}

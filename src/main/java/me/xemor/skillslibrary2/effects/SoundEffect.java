package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.SoundData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class SoundEffect extends Effect implements EntityEffect, TargetEffect, ComplexLocationEffect {

    private final SoundData soundData;

    public SoundEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection soundSection = configurationSection.getConfigurationSection("sound");
        soundData = new SoundData(soundSection);
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        playSound(entity.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity, Entity target) {
        playSound(target.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        playSound(location);
    }

    private void playSound(Location location) {
        location.getWorld().playSound(location, soundData.getSound(), soundData.getVolume(), soundData.getPitch());
    }
}

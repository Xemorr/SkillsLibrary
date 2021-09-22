package me.xemor.skillslibrary2.triggers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.stream.Collectors;

public class PotionEffectTriggerData extends TriggerData {

    private Set<PotionEffectType> types;

    public PotionEffectTriggerData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        types = configurationSection.getStringList("effects").stream().map(PotionEffectType::getByName).collect(Collectors.toSet());
    }

    public boolean inSet(PotionEffectType effectType) {
        return types.isEmpty() || types.contains(effectType);
    }

}

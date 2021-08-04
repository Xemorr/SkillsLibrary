package me.xemor.skillslibrary2.triggers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Set;
import java.util.stream.Collectors;

public class DamageData extends TriggerData {

    private Set<EntityDamageEvent.DamageCause> damageCauses;

    public DamageData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        damageCauses = configurationSection.getStringList("causes").stream().map(String::toUpperCase).map(EntityDamageEvent.DamageCause::valueOf).collect(Collectors.toSet());
    }

    public Set<EntityDamageEvent.DamageCause> getDamageCauses() {
        return damageCauses;
    }


}

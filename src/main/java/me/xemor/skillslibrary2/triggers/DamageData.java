package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Set;
import java.util.stream.Collectors;

public class DamageData extends TriggerData {

    private SetData<EntityDamageEvent.DamageCause> damageCauses;

    public DamageData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        damageCauses = new SetData<>(EntityDamageEvent.DamageCause.class, "causes", configurationSection);
    }

    public SetData<EntityDamageEvent.DamageCause> getDamageCauses() {
        return damageCauses;
    }


}

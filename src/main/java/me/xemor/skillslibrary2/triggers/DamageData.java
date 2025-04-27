package me.xemor.skillslibrary2.triggers;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageData extends TriggerData {

    @JsonPropertyWithDefault
    @JsonAlias("causes")
    private SetData<EntityDamageEvent.DamageCause> damageCauses;

    public SetData<EntityDamageEvent.DamageCause> getDamageCauses() {
        return damageCauses;
    }


}

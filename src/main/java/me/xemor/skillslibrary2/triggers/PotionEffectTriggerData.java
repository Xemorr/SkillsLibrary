package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.stream.Collectors;

public class PotionEffectTriggerData extends TriggerData {

    @JsonPropertyWithDefault
    private SetData<PotionEffectType> types = new SetData<>();
    @JsonPropertyWithDefault
    private SetData<EntityPotionEffectEvent.Cause> causes;
    @JsonPropertyWithDefault
    private SetData<EntityPotionEffectEvent.Action> actions;

    public boolean potionInSet(PotionEffectType effectType) {
        return types.inSet(effectType);
    }

    public boolean causeInSet(EntityPotionEffectEvent.Cause cause) {
        return causes.inSet(cause);
    }

    public boolean actionInSet(EntityPotionEffectEvent.Action action) {
        return actions.inSet(action);
    }
}

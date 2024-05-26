package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.stream.Collectors;

public class PotionEffectTriggerData extends TriggerData {

    private Set<PotionEffectType> types;
    private SetData<EntityPotionEffectEvent.Cause> causes;
    private SetData<EntityPotionEffectEvent.Action> actions;

    public PotionEffectTriggerData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        types = configurationSection.getStringList("effects").stream().map(PotionEffectType::getByName).collect(Collectors.toSet());
        causes = new SetData<>(EntityPotionEffectEvent.Cause.class, "causes", configurationSection);
        actions = new SetData<>(EntityPotionEffectEvent.Action.class, "actions", configurationSection);
    }

    public boolean potionInSet(PotionEffectType effectType) {
        return types.isEmpty() || types.contains(effectType);
    }

    public boolean causeInSet(EntityPotionEffectEvent.Cause cause) {
        return causes.inSet(cause);
    }

    public boolean actionInSet(EntityPotionEffectEvent.Action action) {
        return actions.inSet(action);
    }
}

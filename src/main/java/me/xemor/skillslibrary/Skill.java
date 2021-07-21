package me.xemor.skillslibrary;

import me.xemor.skillslibrary.effects.*;
import me.xemor.skillslibrary.triggers.TriggerData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public class Skill {

    private final TriggerData trigger;
    private final EffectList effects;

    public Skill(ConfigurationSection skillSection) {
        ConfigurationSection triggerSection = skillSection.getConfigurationSection("trigger");
        if (triggerSection == null) {
            SkillsLibrary.getInstance().getLogger().severe("You have not added a trigger section to your skill at " + skillSection.getCurrentPath());
        }
        int triggerType = triggerSection.getInt("type");
        trigger = TriggerData.create(triggerType, triggerSection);
        ConfigurationSection effectsSection = skillSection.getConfigurationSection("effects");
        effects = new EffectList(effectsSection);
    }

    public boolean handleEffects(LivingEntity entity, Object... objects) {
        if (trigger.getConditions().areConditionsTrue(entity, objects)) {
            return effects.handleEffects(entity, objects);
        }
        return false;
    }

    public TriggerData getTriggerData() {
        return trigger;
    }

    public EffectList getEffects() {
        return effects;
    }
}

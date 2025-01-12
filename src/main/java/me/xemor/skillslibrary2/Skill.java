package me.xemor.skillslibrary2;

import me.xemor.skillslibrary2.effects.*;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.triggers.Trigger;
import me.xemor.skillslibrary2.triggers.TriggerData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class Skill {

    private final TriggerData trigger;
    private final EffectList effects;

    public Skill(ConfigurationSection skillSection) {
        ConfigurationSection triggerSection = skillSection.getConfigurationSection("trigger");
        if (triggerSection == null) {
            SkillsLibrary.getInstance().getLogger().severe("You have not added a trigger section to your skill at " + skillSection.getCurrentPath());
        }
        int triggerType = Trigger.getTrigger(triggerSection.getString("type", "COMBAT"));
        if (triggerType == -1) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid trigger for your skill at " + skillSection.getCurrentPath() + ".trigger.type");
        }
        trigger = TriggerData.create(triggerType, triggerSection);
        ConfigurationSection effectsSection = skillSection.getConfigurationSection("effects");
        if (effectsSection != null) {
            effects = new EffectList(effectsSection);
        }
        else {
            effects = new EffectList();
        }
    }

    public boolean handleEffects(Entity entity, Object... objects) {
        Execution execution = new Execution();
        trigger.getConditions().ANDConditions(execution, entity, false, objects).thenAccept((b) -> {
            if (b) effects.handleEffects(execution, entity, objects);
        });
        return execution.isCancelled();
    }

    public TriggerData getTriggerData() {
        return trigger;
    }

    public EffectList getEffects() {
        return effects;
    }
}

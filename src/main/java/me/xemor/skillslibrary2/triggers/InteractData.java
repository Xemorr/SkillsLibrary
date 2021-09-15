package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.Action;

import java.util.List;
import java.util.stream.Collectors;

public class InteractData extends TriggerData {

    private final SetData<Action> actions;

    public InteractData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        actions = new SetData<>(Action.class, "actions", configurationSection);
    }

    public boolean hasAction(Action action) {
        return actions.inSet(action);
    }

}

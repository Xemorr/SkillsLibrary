package me.xemor.skillslibrary2.triggers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.Action;

import java.util.List;
import java.util.stream.Collectors;

public class InteractData extends TriggerData {

    private final List<Action> actions;

    public InteractData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        actions = configurationSection.getStringList("actions").stream().map(String::toUpperCase).map(Action::valueOf).collect(Collectors.toList());
    }

    public boolean hasAction(Action action) {
        return actions.contains(action);
    }

}

package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.Action;

public class InteractData extends TriggerData {

    @JsonPropertyWithDefault
    private SetData<Action> actions;

    public boolean hasAction(Action action) {
        return actions.inSet(action);
    }

}

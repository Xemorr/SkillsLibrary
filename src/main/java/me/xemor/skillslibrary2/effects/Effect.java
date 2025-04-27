package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class Effect {

    private Mode mode = Mode.ALL;

    public Effect() {}

    public Effect setMode(Mode mode) {
        if (mode == null) mode = Mode.ALL;
        if (!supports(mode)) {
            SkillsLibrary.getInstance().getLogger().severe(this.getClass().getSimpleName() + " does not support " + mode.name() + ". Please change the mode");
        }
        this.mode = mode;
        return this;
    }

    public Effect(int effect, ConfigurationSection configurationSection) {
        try {
            mode = Mode.valueOf(configurationSection.getString("mode", "ALL"));
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid mode! " + configurationSection.getCurrentPath() + ".mode");
        }
    }

    private boolean supports(Mode mode) {
        return switch (mode) {
            case ALL -> true;
            case SELF -> this instanceof EntityEffect;
            case OTHER -> this instanceof TargetEffect;
            case LOCATION -> this instanceof ComplexLocationEffect || this instanceof LocationEffect;
            default -> false;
        };
    }

    public Mode getMode() {
        return mode;
    }

}

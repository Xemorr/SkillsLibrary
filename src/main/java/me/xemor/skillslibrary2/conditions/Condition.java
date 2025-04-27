package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.effects.*;
import org.jetbrains.annotations.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class Condition {

    private Mode mode = Mode.ALL;

    @JsonPropertyWithDefault
    @JsonAlias("else")
    private EffectList otherwise;

    public Condition() {}

    @NotNull
    public EffectList getOtherwise() {
        if (otherwise == null) return EffectList.emptyEffectsList();
        return otherwise;
    }

    public Condition(int condition, Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public Condition setMode(Mode mode) {
        if (mode == null) mode = Mode.ALL;
        if (!supports(mode)) {
            SkillsLibrary.getInstance().getLogger().severe(this.getClass().getSimpleName() + " does not support " + mode.name() + ". Please change the mode");
        }
        this.mode = mode;
        return this;
    }

    private boolean supports(Mode mode) {
        return switch (mode) {
            case ALL -> true;
            case SELF -> this instanceof EntityCondition;
            case OTHER -> this instanceof TargetCondition;
            case LOCATION -> this instanceof LocationCondition;
            default -> false;
        };
    }

}

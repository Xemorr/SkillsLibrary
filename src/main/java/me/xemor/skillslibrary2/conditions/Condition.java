package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.effects.EffectList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class Condition {

    @JsonPropertyWithDefault
    private Mode mode = Mode.ALL;

    @JsonPropertyWithDefault
    @JsonAlias("else")
    private EffectList otherwise;

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

}

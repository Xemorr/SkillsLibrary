package me.xemor.skillslibrary2;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.xemor.foliahacks.FoliaHacks;
import me.xemor.skillslibrary2.conditions.Conditions;
import me.xemor.skillslibrary2.effects.Effects;
import me.xemor.skillslibrary2.triggers.Trigger;
import me.xemor.skillslibrary2.triggers.Triggers;
import org.bukkit.plugin.java.JavaPlugin;
import space.arim.morepaperlib.scheduling.GracefulScheduling;

public final class SkillsLibrary extends JavaPlugin {

    private static SkillsLibrary skillsLibrary;
    private static SkillsManager skillsManager;
    private static FoliaHacks foliaHacks;

    @Override
    public void onEnable() {
        skillsLibrary = this;
        skillsManager = new SkillsManager();
        foliaHacks = new FoliaHacks(this);
        this.getServer().getPluginManager().registerEvents(new Triggers(), this);
    }

    public static FoliaHacks getFoliaHacks() {
        return foliaHacks;
    }

    public ObjectMapper setupObjectMapper(ObjectMapper mapper) {
        mapper.registerSubtypes(Trigger.getNamedSubTypes());
        mapper.registerSubtypes(Effects.getNamedSubTypes());
        mapper.registerSubtypes(Conditions.getNamedSubTypes());
        return mapper;
    }

    public static GracefulScheduling getScheduling() {
        return foliaHacks.getScheduling();
    }

    public static SkillsLibrary getInstance() {
        return skillsLibrary;
    }

    public static SkillsManager getSkillsManager() {
        return skillsManager;
    }

}

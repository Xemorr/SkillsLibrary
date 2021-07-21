package me.xemor.skillslibrary;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkillsLibrary extends JavaPlugin {

    private static SkillsLibrary skillsLibrary;
    private static BukkitAudiences bukkitAudiences;
    private static SkillsManager skillsManager;

    @Override
    public void onEnable() {
        skillsLibrary = this;
        bukkitAudiences = BukkitAudiences.create(this);
        skillsManager = new SkillsManager();
    }

    public static SkillsLibrary getInstance() {
        return skillsLibrary;
    }

    public static BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    public static SkillsManager getSkillsManager() {
        return skillsManager;
    }

}

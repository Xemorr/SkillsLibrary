package me.xemor.skillslibrary2;

import me.xemor.skillslibrary2.triggers.TriggerId;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class SkillsManager {

    private final Set<UUID> loopEntities = new HashSet<>();
    private final HashMap<String, RegisteredSkills> map = new HashMap<>();

    public void registerSkill(Skill skill, Plugin plugin) {
        RegisteredSkills registeredSkills = map.get(plugin.getName());
        if (registeredSkills == null) {
            registeredSkills = new RegisteredSkills();
            map.put(plugin.getName(), registeredSkills);
        }
        registeredSkills.registerSkill(skill);
    }

    public void unregisterAllSkills(Plugin plugin) {
        map.remove(plugin.getName());
    }

    public Collection<Skill> getSkills(TriggerId triggerID) {
        return map.values().stream().flatMap(registeredSkills -> registeredSkills.getSkills(triggerID).stream()).collect(Collectors.toList());
    }

    public Set<UUID> getLoopEntities() {
        return loopEntities;
    }

    public void addLoopEntity(UUID uuid) {
        loopEntities.add(uuid);
    }

    public void removeLoopEntity(UUID uuid) {
        loopEntities.remove(uuid);
    }

}

package me.xemor.skillslibrary;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class SkillsManager {

    private final Set<UUID> loopEntities = new HashSet<>();

    private final Multimap<Integer, Skill> triggerToSkill = HashMultimap.create();

    public void registerSkill(Skill skill) {
        triggerToSkill.put(skill.getTriggerData().getTrigger(), skill);
    }

    public Collection<Skill> getSkills(int triggerID) {
        return triggerToSkill.get(triggerID);
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

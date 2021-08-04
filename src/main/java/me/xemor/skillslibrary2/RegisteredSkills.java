package me.xemor.skillslibrary2;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public class RegisteredSkills {

    private final Multimap<Integer, Skill> triggerToSkill = HashMultimap.create();

    public void registerSkill(Skill skill) {
        triggerToSkill.put(skill.getTriggerData().getTrigger(), skill);
    }

    public Collection<Skill> getSkills(int triggerID) {
        return triggerToSkill.get(triggerID);
    }

}

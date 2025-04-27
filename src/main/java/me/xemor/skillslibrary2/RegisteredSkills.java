package me.xemor.skillslibrary2;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.xemor.skillslibrary2.triggers.TriggerId;

import java.util.Collection;

public class RegisteredSkills {

    private final Multimap<TriggerId, Skill> triggerToSkill = HashMultimap.create();

    public void registerSkill(Skill skill) {
        triggerToSkill.put(skill.getTriggerData().getTriggerId(), skill);
    }

    public Collection<Skill> getSkills(TriggerId triggerID) {
        return triggerToSkill.get(triggerID);
    }

}

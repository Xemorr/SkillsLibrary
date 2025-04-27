package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Slime;

public class ResizeEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    /**
     * Slime#setSize is a daft method. It sets the maximum health of the slime to n^2, movement speed to 0.1n + 0.2 and attack damage to n.
     * If the slime is alive, then the health is also set to the maximum health, which is often unintended behaviour in a boss fight.
     * Hence, the retainHealth attribute setting it back.
     */
    @JsonPropertyWithDefault
    private boolean retainHealth = true;

    @Override
    public void useEffect(Execution execution, Entity boss) {
        if (boss instanceof Slime slime) {
            double health = slime.getHealth();
            double maxHealth = slime.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
            slime.setSize((int) Math.round(changeValue(execution, slime.getSize())));
            if (retainHealth) {
                slime.getAttribute(Attribute.MAX_HEALTH).setBaseValue(maxHealth);
                if (health > maxHealth) {
                    health = maxHealth;
                }
                slime.setHealth(health);
            }
        }
        else if (boss instanceof Phantom phantom) {
            phantom.setSize((int) Math.round(changeValue(execution, phantom.getSize())));
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (target instanceof Slime slime) {
            slime.setSize((int) Math.round(changeValue(execution, slime.getSize())));
        }
        else if (target instanceof Phantom phantom) {
            phantom.setSize((int) Math.round(changeValue(execution, phantom.getSize())));
        }
    }

}

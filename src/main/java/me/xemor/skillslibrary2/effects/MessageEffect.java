package me.xemor.skillslibrary2.effects;

import de.themoep.minedown.adventure.MineDown;
import me.xemor.skillslibrary2.SkillsLibrary;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MessageEffect extends Effect implements EntityEffect, TargetEffect {

    private final String message;

    public MessageEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.message = configurationSection.getString("message");
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        sendMessage(target);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        sendMessage(entity);
        return false;
    }

    public void sendMessage(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            Audience audience = SkillsLibrary.getBukkitAudiences().player(player);
            Component component = new MineDown(message).replaceFirst(true).replace("player", player.getName()).toComponent();
            audience.sendMessage(component);
        }
    }
}

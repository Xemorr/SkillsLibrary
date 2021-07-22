package me.xemor.skillslibrary.effects;

import de.themoep.minedown.adventure.MineDown;
import me.xemor.skillslibrary.SkillsLibrary;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MessageEffect extends Effect implements EntityEffect, TargetEffect {

    private final String message;

    public MessageEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.message = configurationSection.getString("message");
    }

    @Override
    public boolean useEffect(LivingEntity entity, Entity target) {
        sendMessage(entity);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
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

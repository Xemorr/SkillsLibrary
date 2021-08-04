package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandEffect extends Effect implements EntityEffect, TargetEffect {

    private Executor executor;
    private String command;

    public CommandEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String executorStr = configurationSection.getString("executor", "CONSOLE").toUpperCase();
        try {
            executor = Executor.valueOf(executorStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid command executor! " + configurationSection.getCurrentPath() + ".executor");
        }
    }

    @Override
    public boolean useEffect(Entity entity) {
        String command = parse(entity, null);
        execute(command, entity);
        return false;
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        String command = parse(livingEntity, target);
        execute(command, livingEntity);
        return false;
    }

    public void execute(String command, Entity entity) {
        if (entity instanceof Player) {
            if (executor == Executor.PLAYER) {
                Bukkit.dispatchCommand(entity, command);
                return;
            }
        }
        if (executor == Executor.CONSOLE) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public String parse(Entity entity1, Entity entity2) {
        String copy = command;
        if (entity1 instanceof Player) {
            Player player = (Player) entity1;
            copy.replaceAll("%self_name%", player.getName());
        }
        else if (entity2 instanceof Player) {
            copy.replace("%target_name%", entity2.getName());
        }
        return copy;
    }

    /**
     * An enum representing who will execute the command.
     * TODO Implement an OP executor with minimal danger of server crashing mid-instruction.
     */
    private enum Executor {
        CONSOLE, PLAYER;
    }

}

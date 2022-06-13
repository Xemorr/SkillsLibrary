package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandEffect extends Effect implements EntityEffect, TargetEffect {

    private Executor executor;
    private List<String> commands;

    public CommandEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String executorStr = configurationSection.getString("executor", "CONSOLE").toUpperCase();
        commands = configurationSection.getStringList("commands");
        try {
            executor = Executor.valueOf(executorStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid command executor! " + configurationSection.getCurrentPath() + ".executor");
        }
    }

    @Override
    public boolean useEffect(Entity entity) {
        for (String command : commands) {
            command = parse(command, entity, null);
            execute(command, entity);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        for (String command : commands) {
            command = parse(command, entity, target);
            execute(command, entity);
        }
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

    public String parse(String command, Entity entity1, Entity entity2) {
        if (entity1 instanceof Player) {
            Player player = (Player) entity1;
            command.replaceAll("%self_name%", player.getName());
        }
        else if (entity2 instanceof Player) {
            command.replace("%target_name%", entity2.getName());
        }
        return command;
    }

    /**
     * An enum representing who will execute the command.
     * TODO Implement an OP executor with minimal danger of server crashing mid-instruction.
     */
    private enum Executor {
        CONSOLE, PLAYER;
    }

}

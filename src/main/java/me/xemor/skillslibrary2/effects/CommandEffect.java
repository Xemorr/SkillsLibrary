package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.ExpressiveMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private Executor executor = Executor.CONSOLE;
    @JsonPropertyWithDefault
    private List<ExpressiveMessage> commands = Collections.emptyList();

    @Override
    public void useEffect(Execution execution, Entity entity) {
        for (ExpressiveMessage command : commands) {
            String parsedCommand = parse(command, execution, entity, null);
            execute(parsedCommand, entity);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        for (ExpressiveMessage command : commands) {
            String parsedCommand = parse(command, execution, entity, target);
            execute(parsedCommand, entity);
        }
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

    public String parse(ExpressiveMessage command, Execution execution, Entity entity, Entity target) {
        String parsedCommand = command.result(execution, entity, target);
        if (entity instanceof Player player) {
            parsedCommand = parsedCommand.replace("%self_name%", player.getName());
        }
        if (target instanceof Player player) {
            parsedCommand = parsedCommand.replace("%other_name%", player.getName());
            parsedCommand = parsedCommand.replace("%target_name%", player.getName());
        }
        return parsedCommand;
    }

    /**
     * An enum representing who will execute the command.
     * TODO Implement an OP executor with minimal danger of server crashing mid-instruction.
     */
    private enum Executor {
        CONSOLE, PLAYER;
    }

}

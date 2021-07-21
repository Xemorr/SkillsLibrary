package me.xemor.skillslibrary.triggers;

import me.xemor.skillslibrary.Skill;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Triggers implements Listener {

    public Triggers() {
        AtomicInteger tick = new AtomicInteger();
        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<Skill> skillDatas = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("LOOP"));
                for (Skill skill : skillDatas) {
                    LoopData loopData = (LoopData) skill.getTriggerData();
                    if (tick.get() % loopData.getPeriod() == 0) {
                        for (UUID uuid : SkillsLibrary.getSkillsManager().getLoopEntities()) {
                            Entity entity = Bukkit.getEntity(uuid);
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                skill.handleEffects(livingEntity);
                            }
                        }
                    }
                }
                tick.addAndGet(1);
            }
        }.runTaskTimer(SkillsLibrary.getInstance(), 1L, 1L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        SkillsLibrary.getSkillsManager().addLoopEntity(e.getPlayer().getUniqueId());
        handleSkills(Trigger.getTrigger("PLAYERJOIN"), e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        SkillsLibrary.getSkillsManager().removeLoopEntity(e.getPlayer().getUniqueId());
        handleSkills(Trigger.getTrigger("PLAYERQUIT"), e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Collection<Skill> skills = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("INTERACT"));
        boolean cancel = false;
        for (Skill skill : skills) {
            InteractData interactData = (InteractData) skill.getTriggerData();
            if (interactData.hasAction(e.getAction())) {
                boolean cancelled = skill.handleEffects(player);
                if (cancelled) cancel = true;
            }
        }
        if (cancel) e.setCancelled(cancel);
    }

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        if (e.getTo() == null) return;
        double difference = e.getTo().clone().subtract(e.getFrom()).getY();
        if (difference >= 0.33319999363422426 && difference <= 0.3332) { //the second statement must be there to support geyser bedrock players
            handleSkills(Trigger.getTrigger("PLAYERJUMP"), e.getPlayer());
        }
    }

    @EventHandler
    public void onGlide(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            boolean result = handleSkills(Trigger.getTrigger("TOGGLEGLIDE"), livingEntity);
            if (result) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getDamager();
            boolean cancel = handleSkills(Trigger.getTrigger("DAMAGEDENTITY"), livingEntity, e.getEntity());
            if (cancel) e.setCancelled(true);
        }
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            boolean cancel = handleSkills(Trigger.getTrigger("DAMAGEDBYENTITY"), livingEntity, e.getDamager());
            if (cancel) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Player player = e.getPlayer();
            handleSkills(Trigger.getTrigger("SNEAK"), player);
        }
    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getTarget() != null) {
            e.setCancelled(handleSkills(Trigger.getTrigger("TARGET"), (LivingEntity) e.getEntity(), e.getTarget()));
        }
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof LivingEntity) {
            e.setCancelled(handleSkills(Trigger.getTrigger("VEHICLE"), (LivingEntity) e.getEntered(), e.getVehicle()));
        }
    }

    public boolean handleSkills(int trigger, @Nullable LivingEntity entity, Object... objects) {
        Collection<Skill> skills = SkillsLibrary.getSkillsManager().getSkills(trigger);
        boolean cancel = false;
        for (Skill skill : skills) {
            boolean cancelled = skill.handleEffects(entity, objects);
            if (cancelled) {
                cancel = true;
            }
        }
        return cancel;
    }


}

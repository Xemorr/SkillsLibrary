package me.xemor.skillslibrary2.triggers;

import com.google.common.collect.Iterators;
import me.xemor.skillslibrary2.Skill;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
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

    @EventHandler(ignoreCancelled = true)
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

    @EventHandler(ignoreCancelled = true)
    public void onJump(PlayerMoveEvent e) {
        if (e.getTo() == null) return;
        double difference = e.getTo().clone().subtract(e.getFrom()).getY();
        if (difference >= 0.33319999363422426 && difference <= 0.3332) { //the second statement must be there to support geyser bedrock players
            handleSkills(Trigger.getTrigger("PLAYERJUMP"), e.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onGlide(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            boolean result = handleSkills(Trigger.getTrigger("TOGGLEGLIDE"), livingEntity);
            if (result) e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getDamager();
            boolean cancel = handleSkills(Trigger.getTrigger("DAMAGEDENTITY"), livingEntity, e.getEntity());
            cancel |= handleSkills(Trigger.getTrigger("COMBAT"), livingEntity, e.getEntity());
            if (cancel) e.setCancelled(true);
        }
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            boolean cancel = handleSkills(Trigger.getTrigger("DAMAGEDBYENTITY"), livingEntity, e.getDamager());
            cancel |= handleSkills(Trigger.getTrigger("COMBAT"), livingEntity, e.getDamager());
            if (cancel) e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void damagedByProjectile(EntityDamageByEntityEvent e) {
        Collection<Skill> damagedByProjectileSkills = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("DAMAGEDBYPROJECTILE"));
        Collection<Skill> projectileCombatSkills = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("PROJECTILECOMBAT"));
        Iterator<Skill> skills = Iterators.concat(damagedByProjectileSkills.iterator(), projectileCombatSkills.iterator());
        boolean cancel = false;
        while (skills.hasNext()) {
            Skill skill = skills.next();
            ProjectileData projectileData = (ProjectileData) skill.getTriggerData();
            Entity damager = null;
            if (e.getDamager() instanceof Projectile) {
                ProjectileSource source = ((Projectile) e.getDamager()).getShooter();
                if (source instanceof Entity) {
                    damager = (Entity) source;
                }
            }
            else if (!projectileData.onlyProjectiles()) {
                damager = e.getDamager();
            }
            if (damager == null) continue;
            cancel |= skill.handleEffects(e.getEntity(), damager);
        }
        e.setCancelled(cancel);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void damagedWithProjectile(EntityDamageByEntityEvent e) {
        Collection<Skill> damagedByProjectileSkills = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("DAMAGEDENTITYWITHPROJECTILE"));
        Collection<Skill> projectileCombatSkills = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("PROJECTILECOMBAT"));
        Iterator<Skill> skills = Iterators.concat(damagedByProjectileSkills.iterator(), projectileCombatSkills.iterator());
        boolean cancel = false;
        while (skills.hasNext()) {
            Skill skill = skills.next();
            ProjectileData projectileData = (ProjectileData) skill.getTriggerData();
            Entity damager = null;
            if (e.getDamager() instanceof Projectile) {
                ProjectileSource source = ((Projectile) e.getDamager()).getShooter();
                if (source instanceof Entity) {
                    damager = (Entity) source;
                }
            }
            else if (!projectileData.onlyProjectiles()) {
                damager = e.getDamager();
            }
            if (damager == null) continue;
            cancel |= skill.handleEffects(damager, e.getEntity());
        }
        e.setCancelled(cancel);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {
        boolean cancel = false;
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            Collection<Skill> skills = SkillsLibrary.getSkillsManager().getSkills(Trigger.getTrigger("DAMAGED"));
            for (Skill skill : skills) {
                DamageData damageData = (DamageData) skill.getTriggerData();
                if (damageData.getDamageCauses().size() == 0 || damageData.getDamageCauses().contains(e.getCause())) {
                    if (skill.handleEffects(livingEntity)) cancel = true;
                }
            }
        }
        e.setCancelled(cancel);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSneak(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Player player = e.getPlayer();
            handleSkills(Trigger.getTrigger("SNEAK"), player);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onTarget(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() != null) {
            e.setCancelled(handleSkills(Trigger.getTrigger("TARGET"), e.getEntity(), e.getTarget()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof LivingEntity) {
            boolean cancel = handleSkills(Trigger.getTrigger("ENTERVEHICLE"), e.getEntered(), e.getVehicle());
            cancel |= handleSkills(Trigger.getTrigger("VEHICLE"), e.getEntered(), e.getVehicle());
            e.setCancelled(cancel);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleExit(VehicleExitEvent e) {
        e.setCancelled(handleSkills(Trigger.getTrigger("EXITVEHICLE"), e.getExited(), e.getVehicle()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        if (projectile.getShooter() instanceof LivingEntity) {
            LivingEntity shooter = (LivingEntity) projectile.getShooter();
            e.setCancelled(handleSkills(Trigger.getTrigger("LAUNCHPROJECTILE"), shooter, projectile));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if (projectile.getShooter() instanceof LivingEntity) {
            LivingEntity shooter = (LivingEntity) projectile.getShooter();
            e.setCancelled(handleSkills(Trigger.getTrigger("PROJECTILEHIT"), shooter, projectile));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e) {
        handleSkills(Trigger.getTrigger("DEATH"), e.getEntity(), e.getEntity().getKiller());
    }


    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        e.setCancelled(handleSkills(Trigger.getTrigger("SPAWN"), entity));
    }


    @EventHandler
    public void onTame(EntityTameEvent e) {
        if (e.getOwner() instanceof Entity) {
            handleSkills(Trigger.getTrigger("TAME"), (Entity) e.getOwner(), e.getEntity());
        }
    }

    public boolean handleSkills(int trigger, @Nullable Entity entity, Object... objects) {
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

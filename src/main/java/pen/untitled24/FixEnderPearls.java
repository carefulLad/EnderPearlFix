package pen.untitled24;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

public class FixEnderPearls extends JavaPlugin implements Listener {

    private final HashSet<UUID> damagedEntities = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPearlHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof EnderPearl pearl) {
            if (event.getHitEntity() != null) {
                Entity hitEntity = event.getHitEntity();
                Vector velocity = hitEntity.getVelocity();
                
                // Calculate the apex of the entity's movement
                double apexY = hitEntity.getLocation().getY() + (velocity.getY() * velocity.getY()) / (2 * 0.08); // Assuming standard gravity
                
                // If the entity is rising or has been recently damaged, teleport player to apex
                if (velocity.getY() > 0 || damagedEntities.contains(hitEntity.getUniqueId())) {
                    Player shooter = (Player) pearl.getShooter();
                    if (shooter != null) {
                        shooter.teleport(hitEntity.getLocation().add(0, apexY - hitEntity.getLocation().getY(), 0), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        damagedEntities.add(event.getEntity().getUniqueId());
        Bukkit.getScheduler().runTaskLater(this, () -> damagedEntities.remove(event.getEntity().getUniqueId()), 20L); // Clear after 1 second
    }
}

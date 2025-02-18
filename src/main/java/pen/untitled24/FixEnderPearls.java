package pen.untitled24;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class FixEnderPearls extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPearlHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof EnderPearl pearl) {
            if (pearl.getShooter() instanceof Player player) {
                Entity hitEntity = event.getHitEntity();

                if (hitEntity != null) { // Pearl hit an entity
                    Location teleportLocation = hitEntity.getLocation();


                    teleportLocation.setY(teleportLocation.getY() + 1); // Avoid suffocation

                    player.teleport(new Location(teleportLocation.getWorld(), teleportLocation.getX(), teleportLocation.getY(), teleportLocation.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    player.setVelocity(new Vector(0, player.getVelocity().getY(), 0));
                    player.getWorld().playSound(teleportLocation, "entity.enderman.teleport", 1.0f, 1.0f);
                    pearl.remove(); // Remove the pearl entity
                }
            }
        }
    }
}


package dev.vedcodee.it.hologram.event;

import dev.vedcodee.it.factory.HologramFactory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitHologram implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) && !(event.getDamager() instanceof ArmorStand)) return;
        if(HologramFactory.HOLOGRAMS_CACHE.contains((ArmorStand) event.getDamager()))
            event.setCancelled(true);

    }

}

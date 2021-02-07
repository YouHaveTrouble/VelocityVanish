package eu.endermite.velocityvanish.spigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class VanishListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerVanishTrue(de.myzelyam.api.vanish.PlayerHideEvent event) {
        ChannelHandler.sendToProxy(
                event.getPlayer(),
                "velocity:vanish-true",
                "uuid:"+event.getPlayer().getUniqueId().toString()
        );
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerVanishFalse(de.myzelyam.api.vanish.PlayerShowEvent event) {
        ChannelHandler.sendToProxy(
                event.getPlayer(),
                "velocity:vanish-false",
                "uuid:"+event.getPlayer().getUniqueId().toString()
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        ChannelHandler.sendToProxy(
                event.getPlayer(),
                "velocity:vanish-check",
                "uuid:"+event.getPlayer().getUniqueId().toString()
        );
    }



}

package eu.endermite.velocityvanish.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.ServerPing;
import org.slf4j.Logger;
import java.util.UUID;

public class VelocityVanish {

    private static VelocityVanish plugin;
    private static ProxyServer server;
    private static Logger logger;
    private VanishedPlayers vanishedPlayers;
    private static MinecraftChannelIdentifier vanishCheckChannel;

    @Inject
    public VelocityVanish(ProxyServer server, Logger logger) {
        VelocityVanish.server = server;
        VelocityVanish.logger = logger;
        VelocityVanish.plugin = this;

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        vanishedPlayers = new VanishedPlayers();

        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("velocity", "vanish-true"));
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("velocity", "vanish-false"));
        VelocityVanish.vanishCheckChannel = MinecraftChannelIdentifier.create("velocity", "vanish-check");
        server.getChannelRegistrar().register(vanishCheckChannel);

    }

    @Subscribe
    public void onPluginMesageRecieve(com.velocitypowered.api.event.connection.PluginMessageEvent event) {
        ServerConnection connection = (ServerConnection) event.getSource();
        Player player = connection.getPlayer();
        switch (event.getIdentifier().getId()) {
            case "velocity:vanish-true": {
                UUID uuid = player.getUniqueId();
                vanishedPlayers.setPlayer(uuid, true);
                break;
            }
            case "velocity:vanish-false": {
                UUID uuid = player.getUniqueId();
                vanishedPlayers.removePlayer(uuid);
                break;
            }
            case "velocity:vanish-check": {
                UUID uuid = player.getUniqueId();

                boolean vanishStatus = false;
                if (vanishedPlayers.isVanished(uuid)) {
                    vanishedPlayers.setPlayer(uuid, true);
                    vanishStatus = true;
                }
                String data = String.valueOf(vanishStatus);
                connection.sendPluginMessage(VelocityVanish.vanishCheckChannel, data.getBytes());
                break;
            }
        }
    }

    @Subscribe(order = PostOrder.LAST)
    public void onVanishedPlayerLeave(com.velocitypowered.api.event.connection.DisconnectEvent event) {
        if (vanishedPlayers.isVanished(event.getPlayer().getUniqueId())) {
            vanishedPlayers.setPlayer(event.getPlayer().getUniqueId(), false);
        }
    }

    @Subscribe(order = PostOrder.LAST)
    public void onVanishedPlayerJoin(com.velocitypowered.api.event.connection.PostLoginEvent event) {
        if (vanishedPlayers.isVanished(event.getPlayer().getUniqueId()))
            vanishedPlayers.setPlayer(event.getPlayer().getUniqueId(), true);
    }


    @Subscribe(order = PostOrder.LAST)
    public void onProxyPing(com.velocitypowered.api.event.proxy.ProxyPingEvent event) {
        ServerPing ping = event.getPing();
        ping = ping.asBuilder().onlinePlayers(VelocityVanish.server.getPlayerCount() - vanishedPlayers.getHowManyPlayersVanished()).build();
        event.setPing(ping);
    }

}

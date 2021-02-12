package eu.endermite.velocityvanish.spigot;

import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        InputStream targetStream = new ByteArrayInputStream(message);
        String result = new BufferedReader(new InputStreamReader(targetStream)).lines()
                .parallel().collect(Collectors.joining());

        if (result.contains("true") && !VanishAPI.isInvisible(player)) {
            VanishAPI.hidePlayer(player);
        } else if (result.contains("false") && VanishAPI.isInvisible(player)) {
            VanishAPI.showPlayer(player);
        }
    }
}

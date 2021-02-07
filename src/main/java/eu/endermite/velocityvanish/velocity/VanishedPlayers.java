package eu.endermite.velocityvanish.velocity;

import java.util.HashMap;
import java.util.UUID;

public class VanishedPlayers {

    private final HashMap<UUID, Boolean> players = new HashMap<>();

    public int getHowManyPlayersVanished() {
        int number = 0;
       for (boolean vanished : players.values()) {
           if (vanished)
               number++;
       }
       return number;
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    public void setPlayer(UUID uuid, boolean state) {
        players.put(uuid, state);
    }

    public boolean isVanished(UUID uuid) {
        try {
            boolean test = players.get(uuid);
            if (test) {
                return true;
            } else {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
}

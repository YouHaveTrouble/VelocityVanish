package eu.endermite.velocityvanish.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VelocityVanish extends JavaPlugin {

    private static VelocityVanish plugin;

    @Override
    public void onEnable() {

        if (!(Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish"))) {
            getLogger().severe("Supervanish/PremiumVanish not detected! Disabling!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;
        /*
        Using multiple channels. Reasoning for this decision is to not use streams to read data, but just grab an uuid
        from the connection data and acting on it.
         */
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "velocity:vanish-true");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "velocity:vanish-false");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "velocity:vanish-check");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "velocity:vanish-check", new MessageListener());
        getServer().getPluginManager().registerEvents(new VanishListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "velocity:vanish-true");
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "velocity:vanish-false");
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "velocity:vanish-check");
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this, "velocity:vanish-check");
    }
    
    public static VelocityVanish getInstance() {
        return plugin;
    }

}

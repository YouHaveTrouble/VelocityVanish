<h1>VelocityVanish</h1>

<p>This spigot+velocity plugin will allow you to integrate SuperVanish or PremiumVanish with Velocity.</p> 
<p>The only thing it will not handle is removing join message for the player that was not vanished on the server last time they were there. It also won't keep the vanish statuses between proxy restarts and will default to making players visible.</p>

<h2>How to use</h2>
<p>Install the plugin on your velocity proxy and on the bukkit based server. Restart both of them. You're done.</p>

<h2>How it works</h2>
<p>Plugin uses something called Plugin Messaging channels to relay the information about vanish state.</p>

<h2>Ending notes</h2>
<p>This is only minimal viable product. This means it will not fit on any server, you have to have join messages hidden, as plugin message can't be sent before player actually joins.</p>
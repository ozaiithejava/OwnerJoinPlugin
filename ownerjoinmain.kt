import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class OwnerJoinPlugin : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        saveDefaultConfig()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val playerName = event.player.name
        val owners = config.getStringList("owners")

        if (owners.contains(playerName)) {
            val location = event.player.location
            sendOwnerMessage(playerName)
            spawnFireworks(location)
            strikeLightning(location)
        }
    }

    private fun sendOwnerMessage(playerName: String) {
        val message = "Owner $playerName has joined the game!"
        Bukkit.broadcastMessage(message)
    }

    private fun spawnFireworks(location: Location) {
        val firework = location.world.spawnEntity(location, EntityType.FIREWORK) as Firework
        val effect = FireworkEffect.builder().withColor(org.bukkit.Color.RED).flicker(true).build()
        firework.fireworkMeta.addEffect(effect)
        firework.fireworkMeta.power = 2
    }

    private fun strikeLightning(location: Location) {
        location.world.strikeLightning(location)
    }
}


package me.aeshthetic.mobbounty

import java.util.logging.Logger
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onDisable() {
        log.info("[${description.name}] Disabled Version ${description.version}")
    }

    override fun onEnable() {
        if (!setupEconomy()) {
            log.severe("[${description.name} - Disabled due to no vault dependency found!")
            server.pluginManager.disablePlugin(this)
            return
        }

        server.pluginManager.registerEvents(KillListener(), this)
    }

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return false
        economy = rsp.provider
        return economy != null
    }

    companion object {
        private val log = Logger.getLogger("Minecraft")
        var economy: Economy? = null
            private set
    }
}

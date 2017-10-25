package me.aeshthetic.mobbounty

import me.aeshthetic.mobbounty.Main.Companion.economy
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.EntityType
import org.bukkit.entity.Monster
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class KillListener : Listener {
    @EventHandler
    fun onMobKill(event: EntityDeathEvent) {
        val player: Player?
        if (event.entity !is Monster)
            return

        val victim = event.entity
        when (victim.killer) {
            null -> return
            !is Player -> return
            else -> player = victim.killer
        }

        val tierOne = listOf<EntityType>(EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SPIDER, EntityType.CAVE_SPIDER,EntityType.ZOMBIE_VILLAGER)
        val tierTwo = listOf<EntityType>(EntityType.SKELETON, EntityType.ENDERMAN, EntityType.PIG_ZOMBIE, EntityType.WITCH, EntityType.BLAZE)
        val tierThree = listOf<EntityType>(EntityType.WITHER_SKELETON, EntityType.ELDER_GUARDIAN, EntityType.GUARDIAN, EntityType.HUSK, EntityType.ILLUSIONER, EntityType.VINDICATOR, EntityType.VEX, EntityType.STRAY, EntityType.WITHER)

        val multiplier = when (event.entity.type) {
            in tierOne -> 1.25
            in tierTwo -> 1.5
            in tierThree -> 2.0
            else -> 1.0
        }

        val reward = (Math.random() * multiplier).round(2)

        val deposit = economy!!.depositPlayer(player, reward)
        if (deposit.transactionSuccess()) {
            val rewardText = TextComponent("$$reward").apply {
                color = ChatColor.RED
            }

            val mobName = TextComponent(event.entity.name).apply {
                color = ChatColor.GOLD
                isBold = true
            }

            val message = TextComponent("You received ").apply {
                addExtra(rewardText)
                addExtra(TextComponent(" from killing a "))
                addExtra(mobName)
                color = ChatColor.GREEN
            }

            player.spigot().sendMessage(message)


        } else {
            player.sendMessage("An error occurred: ${deposit.errorMessage}")
        }
    }


}
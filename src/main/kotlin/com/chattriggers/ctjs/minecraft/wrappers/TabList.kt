package com.chattriggers.ctjs.minecraft.wrappers

import com.chattriggers.ctjs.minecraft.objects.message.Message
import com.chattriggers.ctjs.utils.kotlin.External
import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import net.minecraft.client.network.play.NetworkPlayerInfo
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.GameType
import java.util.*

@External
object TabList {
    private val playerComparator = Ordering.from(PlayerComparator())

    /**
     * Gets names set in scoreboard objectives
     *
     * @return The formatted names
     */
    @JvmStatic
    fun getNamesByObjectives(): List<String> {
        return try {
            val scoreboard = World.getWorld()?.scoreboard ?: return emptyList()
            val sidebarObjective = scoreboard.getObjectiveInDisplaySlot(0) ?: return emptyList()
            val scores = scoreboard.getSortedScores(sidebarObjective)

            scores.map {
                val team = scoreboard.getPlayersTeam(it.playerName)
                ScorePlayerTeam.formatMemberName(team, StringTextComponent(it.playerName)).unformattedComponentText
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // @JvmStatic
    // fun getNames(): List<String> {
    //     if (Client.getTabGui() == null) return listOf()
    //
    //     val playerList = playerComparator.sortedCopy(Client.getMinecraft().player.sendQueue.playerInfoMap)
    //
    //     return playerList.map {
    //         Client.getTabGui()!!.getPlayerName(it)
    //     }
    // }

    /**
     * Gets all names in tabs without formatting
     *
     * @return the unformatted names
     */
    @JvmStatic
    fun getUnformattedNames(): List<String> {
        if (Player.getPlayer() == null) return listOf()

        val tab = Ordering.from(PlayerComparator())
        val conn = Client.getConnection()
        val list = tab.sortedCopy(conn?.playerInfoMap ?: return emptyList())

        return list.map {
            it.gameProfile.name
        }
    }

    @JvmStatic
    fun getHeaderMessage() = Client.getTabGui()?.header?.let(::Message)

    @JvmStatic
    fun getHeader() = Client.getTabGui()?.header?.formattedText

    @JvmStatic
    fun setHeader(header: Any) {
        when (header) {
            is String -> Client.getTabGui()?.setHeader(Message(header).getChatMessage())
            is Message -> Client.getTabGui()?.setHeader(header.getChatMessage())
            is ITextComponent -> Client.getTabGui()?.setHeader(header)
        }
    }

    @JvmStatic
    fun getFooterMessage() = Client.getTabGui()?.footer?.let(::Message)

    @JvmStatic
    fun getFooter() = Client.getTabGui()?.footer?.formattedText

    @JvmStatic
    fun setFooter(footer: Any) {
        when (footer) {
            is String -> Client.getTabGui()?.setHeader(Message(footer).getChatMessage())
            is Message -> Client.getTabGui()?.setHeader(footer.getChatMessage())
            is ITextComponent -> Client.getTabGui()?.setHeader(footer)
        }
    }

    internal class PlayerComparator internal constructor() : Comparator<NetworkPlayerInfo> {
        override fun compare(playerOne: NetworkPlayerInfo, playerTwo: NetworkPlayerInfo): Int {
            val teamOne = playerOne.playerTeam
            val teamTwo = playerTwo.playerTeam

            return ComparisonChain
                .start()
                .compareTrueFirst(
                    playerOne.gameType != GameType.SPECTATOR,
                    playerTwo.gameType != GameType.SPECTATOR
                ).compare(
                    teamOne?.name ?: "",
                    teamTwo?.name ?: ""
                ).compare(
                    playerOne.gameProfile.name,
                    playerTwo.gameProfile.name
                ).result()
        }
    }
}
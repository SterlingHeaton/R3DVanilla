package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import com.redslounge.r3dvanilla.models.enums.RedMessages;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("slimechunks")
public class SlimeChunkCommand extends BaseCommand
{
    @Default
    @CommandCompletion("@nothing")
    public void onSlimeChunks(Player player)
    {
        if(!player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.SLIMECHUNK, RedMessages.SLIMECHUNK_WRONG_DIMENSION_ERROR, ""));
            return;
        }

        Chunk[][] chunks = new Chunk[9][9];
        Chunk originChunk = player.getLocation().getChunk();

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                chunks[j][i] = player.getWorld().getChunkAt((originChunk.getX() - 4) + i, (originChunk.getZ() - 4) + j);
            }
        }

        player.sendMessage(Utils.getCommandReply(ChatTags.SLIMECHUNK, RedMessages.SLIMECHUNK_SUCCESS, ""));

        for(int i = 0; i < chunks.length; i++)
        {
            TextComponent chunkList = new TextComponent();

            for(int j = 0; j < chunks[i].length; j++)
            {
                Chunk targetChunk = chunks[i][j];
                chunkList.addExtra(buildSlimePart(targetChunk.isSlimeChunk(), targetChunk.equals(originChunk), targetChunk.getX(), targetChunk.getZ()));
            }

            switch(i)
            {
                case 0: chunkList.addExtra("   N"); break;
                case 1: chunkList.addExtra(" W   E"); break;
                case 2: chunkList.addExtra("   S"); break;
                case 3: chunkList.addExtra(Utils.color("&7&o Note: This uses Chunk Coordinates instead of")); break;
                case 4: chunkList.addExtra(Utils.color("&7&o Block Coordinates. Hover over to see.")); break;
                case 6: chunkList.addExtra(Utils.color(" &a▒&f/&2▒ &f= Slime Chunk")); break;
                case 7: chunkList.addExtra(Utils.color(" &c▒&f/&4▒ &f= Non-Slime Chunk")); break;
                case 8: chunkList.addExtra(Utils.color(" &2▒&f/&4▒ &f= You Are Here")); break;
                default: break;
            }

            player.spigot().sendMessage(chunkList);
        }
    }

    private TextComponent buildSlimePart(boolean isSlime, boolean isOrigin, int chunkX, int chunkZ)
    {
        TextComponent message = new TextComponent("");

        if(isSlime)
        {
            if(isOrigin)
            {
                message.setText(Utils.color("&2▒"));
            }
            else
            {
                message.setText(Utils.color("&a▒"));
            }
        }
        else
        {
            if(isOrigin)
            {
                message.setText(Utils.color("&4▒"));
            }
            else
            {
                message.setText(Utils.color("&c▒"));
            }
        }

        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Chunk Coordinate: \n" + chunkX + ", " + chunkZ)));

        return message;
    }
}

package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import org.bukkit.entity.Player;

@CommandAlias("logout")
public class LogoutCommand extends BaseCommand
{
    @Default
    @CommandCompletion("@nothing")
    public void onLogout(Player player)
    {
        player.kickPlayer(Utils.color("&aSuccessfully logged out."));
    }
}

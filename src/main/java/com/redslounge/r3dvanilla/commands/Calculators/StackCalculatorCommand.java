package com.redslounge.r3dvanilla.commands.Calculators;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import org.bukkit.entity.Player;

@CommandAlias("stackcalculator|stackcalc")
public class StackCalculatorCommand extends BaseCommand
{
    @Default
    @CommandCompletion("1|16|64 @nothing")
    public void onStackCalculator(Player player, int stack, int amount)
    {
        if(amount <= 0)
        {
            player.sendMessage(Utils.color("&cInput needs to be above 0."));
            return;
        }

        int items = amount % 64;
        amount = amount / 64;
        int stacks = amount % 27;
        amount = amount / 27;

        player.sendMessage(Utils.color("&aShulker Boxes: " + amount));
        player.sendMessage(Utils.color("&aStacks: " + stacks));
        player.sendMessage(Utils.color("&aItems: " + items));
    }
}

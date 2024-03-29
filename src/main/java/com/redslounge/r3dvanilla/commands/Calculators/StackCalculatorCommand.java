package com.redslounge.r3dvanilla.commands.Calculators;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import com.redslounge.r3dvanilla.models.enums.RedMessages;
import org.bukkit.entity.Player;

/**
 * Command to calculate the amount of stacks from an amount of items.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("stackcalculator|stackcalc")
public class StackCalculatorCommand extends BaseCommand
{
    /**
     * Main command to calculate the amount of stacks from a given amount of items.
     *
     * @param player Player that issued the command.
     * @param stack  Max amount that is in the stack.
     * @param amount Amount of items you want to convert.
     */
    @Default
    @CommandCompletion("1|16|64 @nothing")
    public void onStackCalculator(Player player, int stack, int amount)
    {
        if(amount <= 0)
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.STACK_CALCULATOR, RedMessages.STACK_CALC_INPUT_ABOVE_0, ""));
            return;
        }

        int totalItems = amount;

        // Calculate the amount of items that will be left over.
        int items = amount % stack;
        amount = amount / stack;

        // Calculate the amount of stacks.
        int stacks = amount % 27;
        amount = amount / 27;

        // Send the player a message on how many shulker boxes, stacks, and items are in the item amount inputed.
        player.sendMessage(Utils.color(ChatTags.STACK_CALCULATOR.getTag() + " &7" + totalItems + " &ais equal to:"));
        player.sendMessage(Utils.color(ChatTags.STACK_CALCULATOR.getTag() + " &aShulker Boxes: &6" + amount));
        player.sendMessage(Utils.color(ChatTags.STACK_CALCULATOR.getTag() + " &aStacks: &6" + stacks));
        player.sendMessage(Utils.color(ChatTags.STACK_CALCULATOR.getTag() + " &aItems: &6" + items));
    }
}

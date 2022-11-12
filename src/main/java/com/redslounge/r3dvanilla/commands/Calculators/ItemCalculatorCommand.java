package com.redslounge.r3dvanilla.commands.Calculators;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import org.bukkit.entity.Player;

/**
 * This command is for calculating how many items there are in a specified amount of inventories.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("itemcalculator|itemcalc")
public class ItemCalculatorCommand extends BaseCommand
{
    /**
     * Main command to calculate the amount of items from a stack based calculation string.
     *
     * @param player      The player who issued the command.
     * @param stackAmount The max amount of items that are in the stack.
     * @param calculation A string of numbers and set of letters that describes an inventory filled with a specified item.
     */
    @Default
    @CommandCompletion("1|16|64 @nothing")
    public void onItemCalculator(Player player, int stackAmount, String calculation)
    {
        if(calculation.isEmpty())
        {
            player.sendMessage(Utils.color(ChatTags.ITEM_CALCULATOR.getTag() + "&cDidn't input a calculation for the calculator."));
            return;
        }

        // Split he main calculation string to figure out the different inputs.
        String[] calculationParts = calculation.split("\\+");
        int total = 0;

        // Main loop to calculate the amount of items.
        for(String calculationPart : calculationParts)
        {
            // Series of if statements to see what inventories were inputed.
            // sb = shulker box
            // dc = double chest
            // sc = single chest
            // st = stack
            // else -> items
            if(calculationPart.contains("sb"))
            {
                int amount = Integer.parseInt(calculationPart.replace("sb", ""));
                total += amount * stackAmount * 27;
            }
            else if(calculationPart.contains("dc"))
            {
                int amount = Integer.parseInt(calculationPart.replace("dc", ""));
                total += amount * stackAmount * 54;
            }
            else if(calculationPart.contains("sc"))
            {
                int amount = Integer.parseInt(calculationPart.replace("sc", ""));
                total += amount * stackAmount * 27;
            }
            else if(calculationPart.contains("st"))
            {
                int amount = Integer.parseInt(calculationPart.replace("st", ""));
                total += amount * stackAmount;
            }
            else
            {
                try
                {
                    int amount = Integer.parseInt(calculationPart);
                    total += amount;
                }
                catch(NumberFormatException e)
                {
                    player.sendMessage(Utils.color(ChatTags.ITEM_CALCULATOR.getTag() + "&cInvalid format."));
                    return;
                }
            }
        }

        // Send the player the item amount.
        player.sendMessage(Utils.color(ChatTags.ITEM_CALCULATOR.getTag() + "&7" + calculation + " &ais equal to: &6" + total));
    }
}

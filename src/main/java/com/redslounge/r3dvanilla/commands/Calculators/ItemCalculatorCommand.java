package com.redslounge.r3dvanilla.commands.Calculators;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import org.bukkit.entity.Player;

@CommandAlias("itemcalculator|itemcalc")
public class ItemCalculatorCommand extends BaseCommand
{
    @Default
    @CommandCompletion("1|16|64 @nothing")
    public void onItemCalculator(Player player, int stackAmount, String calculation)
    {
        DataManager dataManager = DataManager.getInstance();

        if(calculation.isEmpty())
        {
            player.sendMessage(Utils.color(dataManager.getItemCalculatorTag() + "&cDidn't input a calculation for the calculator."));
            return;
        }

        String[] calculationParts = calculation.split("\\+");
        int total = 0;

        for(String calculationPart : calculationParts)
        {
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
                    player.sendMessage(Utils.color(dataManager.getItemCalculatorTag() + "&cInvalid format."));
                    return;
                }
            }
        }
        player.sendMessage(Utils.color(dataManager.getItemCalculatorTag() + "&7" + calculation + " &ais equal to: &6" + total));
    }
}

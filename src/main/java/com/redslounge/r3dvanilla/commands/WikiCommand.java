package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

@CommandAlias("wiki")
public class WikiCommand extends BaseCommand
{
    @Default
    @CommandCompletion("@nothing")
    public void onWikiCommand(Player player, String[] args)
    {
        String chatTag = "&8[&6Wiki&8] ";

        if(args.length == 0)
        {
            player.sendMessage(Utils.color(chatTag + "&cYou didn't add a search term."));
            return;
        }

        String url = "https://minecraft.fandom.com/wiki/Special:Search?fulltext=1&query=";
        String search = Utils.buildMessage(args, 0);

        Document document;
        try
        {
            document = Jsoup.connect(url + search).get();
        }
        catch(IOException e)
        {
            player.sendMessage(Utils.color(chatTag + "&cSomething went wrong, please report this to @Sterling#9999"));
            System.out.println(e);
            return;
        }

        Elements elements = document.select("ul.unified-search__results li");

        if(elements.isEmpty())
        {
            player.sendMessage(Utils.color(chatTag + "&cDidn't find any results, was it spelled right?"));
            return;
        }

        String resultName = elements.get(0).getElementsByClass("unified-search__result__title").get(0).text();
        String resultUrl = elements.get(0).getElementsByClass("unified-search__result__title").get(0).attr("href");

        TextComponent result = new TextComponent(Utils.color(chatTag + "&aFound an article titled: &6" + resultName + " &7&o(click)"));
        result.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, resultUrl));

        player.spigot().sendMessage(result);
    }
}

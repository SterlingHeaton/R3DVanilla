package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import com.redslounge.r3dvanilla.models.enums.RedMessages;
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
        if(args.length == 0)
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.WIKI, RedMessages.WIKI_NO_SEARCH_TERM_ERROR, ""));
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
            player.sendMessage(Utils.getCommandReply(ChatTags.WIKI, RedMessages.UNKNOWN_ERROR, ""));
            System.out.println(e);
            return;
        }

        Elements elements = document.select("ul.unified-search__results li");

        if(elements.isEmpty())
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.WIKI, RedMessages.WIKI_NO_RESULTS_ERROR, ""));
            return;
        }

        for(int i = 0; i < elements.size() && i < 3; i++)
        {
            String resultName = elements.get(i).getElementsByClass("unified-search__result__title").get(0).text();
            String resultUrl = elements.get(i).getElementsByClass("unified-search__result__title").get(0).attr("href");

            TextComponent result = new TextComponent(Utils.color(ChatTags.WIKI.getTag() + " &aFound an article titled: &6" + resultName + " &7&o(click)"));
            result.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, resultUrl));

            player.spigot().sendMessage(result);
        }
    }
}

package com.redslounge.r3dvanilla.models.enums;

public enum ChatTags
{
    MESSAGES("&8[&6Messages&8]"),
    REPLY("&8[&6Reply&8]"),
    MESSAGE_PING("&8[&6Message Ping&8]"),
    PORTAL_CALCULATOR("&8[&6Portal&8]"),
    ITEM_CALCULATOR("&8[&6ItemCalc&8]"),
    STACK_CALCULATOR("&8[&6StackCalc&8]"),
    WIKI("&8[&6Wiki&8]"),
    SLEEP("&8[&6Sleep&8]"),
    NOTES("&8[&6Notes&8]"),
    SLIMECHUNK("&8[&6Slime Chunks&8]");

    private final String tag;

    ChatTags(String tag)
    {
        this.tag = tag;
    }

    public String getTag()
    {
        return tag;
    }
}

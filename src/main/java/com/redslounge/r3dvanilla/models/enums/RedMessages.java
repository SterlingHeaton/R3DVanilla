package com.redslounge.r3dvanilla.models.enums;

public enum RedMessages
{
    // Item Calculator
    ITEM_CALC_NO_INPUT_ERROR("&cDidn't input a calculation for the calculator."),
    ITEM_CALC_INVALID_FORMAT_ERROR("&cInvalid format."),

    // Portal Calculator
    PORTAL_OVERWORLD_SUCCESS("&2Overworld &aportal location:"),
    PORTAL_NETHER_SUCCESS("&4Nether &aportal location:"),
    PORTAL_NETHER_HUB("&aNether Hub Location:"),
    PORTAL_WRONG_DIMENSION_ERROR("&cThis command can only be used in the Overworld or Nether!"),

    // Stack Calculator
    STACK_CALC_INPUT_ABOVE_0("&cInput needs to be above 0."),

    // Message Ping
    PING_SOUND_ERROR("&cThat isn't a valid sound name!"),
    PING_PITCH_ERROR("&cPitch must be between 0-2!"),
    PING_SUCCESS("&aUpdated sound!"),

    // Private Message


    // Reply
    REPLY_NO_REPLIER_ERROR("&cNo one has messaged you!"),

    // AFK


    // Logout


    // Notes
    NOTES_SYNTAX_ERROR("&cInvalid syntax: &7&o/note <add | delete | view>"),
    NOTES_MAX_NOTES_ERROR("&cYou have run out of room for more notes."),
    NOTES_ADD_SUCCESS("&aSuccessfully added your note!"),
    NOTES_ADD_ERROR("&cFailed to add your note, try again and if it still fails let @Sterling#9999 know."),
    NOTES_UNKNOWN_NOTE_ID_ERROR("&cThat note number isn't associated with any of your notes."),
    NOTES_DEL_SUCCESS("&aSuccessfully deleted your note!"),
    NOTES_DEL_ERROR("&cFailed to delete your note, try again and if it still fails let @Sterling#9999 know."),
    NOTES_NO_NOTES_ERROR("&cNo notes to display."),
    NOTES_PERSONAL("&aPersonal Notes:"),

    // Sleep
    SLEEP_NONE_ERROR("&cNo one is sleeping you sussy baka."),
    SLEEP_MAJORITY_VOTE("&cMajority vote enabled, can't cancel sleep."),

    // Slime chunk
    SLIMECHUNK_WRONG_DIMENSION_ERROR("&cYou can only use this command in the overworld."),
    SLIMECHUNK_SUCCESS("&aHere are the slime chunks found near you."),

    // Wiki
    WIKI_NO_SEARCH_TERM_ERROR("&cYou didn't add a search term."),
    WIKI_NO_RESULTS_ERROR("&cDidn't find any results, was it spelled right?"),

    // Other
    ENABLED("&aEnabled!"),
    DISABLED("&cDisabled!"),
    UNKNOWN_ERROR("&cSomething went wrong, please go to the link and create a new issue -> Bug Report, and fill out the form. https://github.com/SterlingHeaton/R3DVanilla/issues"),
    PLAYER_MISSPELLED_ERROR("&cPlayer offline or misspelled!"),
    PLAYER_OFFLINE_ERROR("&cPlayer isn't online!");

    private final String message;

    RedMessages(String message)
    {
        this.message = message;
    }

    public String message()
    {
        return message;
    }
}

package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.Plugin;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EndermanEvent implements Listener
{
    private Plugin plugin;

    public EndermanEvent(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityGrief(EntityChangeBlockEvent event)
    {
        if(event.getEntity() instanceof Enderman)
        {
            event.setCancelled(true);
        }
    }
}

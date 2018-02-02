package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.main.Vanilla;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EndermanEvent implements Listener
{
    private Vanilla plugin;

    public EndermanEvent(Vanilla pl)
    {
        plugin = pl;
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

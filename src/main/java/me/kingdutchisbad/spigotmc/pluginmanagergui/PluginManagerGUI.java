package me.kingdutchisbad.spigotmc.pluginmanagergui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

public final class PluginManagerGUI extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        getCommand("inventorytest").setExecutor(new InventoryTestClass());
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
    }
}

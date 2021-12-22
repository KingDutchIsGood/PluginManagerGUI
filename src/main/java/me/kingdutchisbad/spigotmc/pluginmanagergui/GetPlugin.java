package me.kingdutchisbad.spigotmc.pluginmanagergui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class GetPlugin
{
    private static String jar;

    public GetPlugin(String jar)
    {
        GetPlugin.jar = jar;
    }
    public  Plugin getPluginJAR()
    {
        return Bukkit.getPluginManager().getPlugin(jar);
    }
    public  FileConfiguration getPluginConfig()
    {
        return getPluginJAR().getConfig();
    }
}

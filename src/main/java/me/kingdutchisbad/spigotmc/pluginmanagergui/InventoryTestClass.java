package me.kingdutchisbad.spigotmc.pluginmanagergui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/*
All of this code is just proof of concept and written very badly
 */

public class InventoryTestClass implements CommandExecutor
{

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) return false;
        Player player = ((Player) sender).getPlayer();
        MainMenu mainMenu = new MainMenu();
        Menu menu = mainMenu.createMenu();
        int i = 10;

        for(Plugin plugin : Bukkit.getPluginManager().getPlugins())
        {
          Slot  slot = menu.getSlot(i);
          AtomicReference<Boolean> isOn = new AtomicReference<Boolean>(plugin.getServer().getPluginManager().isPluginEnabled(plugin));
          ItemStack  itemStack = new ItemStack(Material.GREEN_WOOL);
          ItemMeta  itemMeta = itemStack.getItemMeta();
          itemMeta.setDisplayName(plugin.getName());
          itemMeta.setLore(Collections.singletonList(plugin.getDescription().getFullName().toString()));
          itemStack.setItemMeta(itemMeta);
          slot.setItem(itemStack);
          i++;
          mainMenu.addWhiteBorder(menu);
          mainMenu.displayMenu(player, menu);
          menu.update();
          slot.setClickHandler((playerThing, info) -> {
                /* Creates a menu and checks if plugin is on or not (Todo) */



                PluginOptionsMenu pluginOptionsMenu = new PluginOptionsMenu();
                Menu pluginOptionSubMenu = pluginOptionsMenu.createMenu(plugin.getDescription().getDescription() , true);
                Slot slotPluginOptionsSubMenu = pluginOptionSubMenu.getSlot(10);
                ItemStack woolOn = new ItemStack(Material.GREEN_WOOL);
                ItemStack woolOff = new ItemStack(Material.RED_WOOL);
                ItemMeta woolOnItemMeta = woolOn.getItemMeta();
                ItemMeta woolOffItemMeta = woolOff.getItemMeta();
                woolOnItemMeta.setDisplayName(ChatColor.GREEN+plugin.getName());
                woolOn.setItemMeta(woolOnItemMeta);

                woolOffItemMeta.setDisplayName(ChatColor.RED+plugin.getName());
                woolOff.setItemMeta(woolOffItemMeta);

                Slot slotForBack = pluginOptionSubMenu.getSlot(43);
                ItemStack slotForBackItemStack = new ItemStack(Material.BARRIER);
                ItemMeta slotForBackItemMeta = slotForBackItemStack.getItemMeta();
                slotForBackItemMeta.setDisplayName(ChatColor.RED+"Back");
                slotForBackItemStack.setItemMeta(slotForBackItemMeta);
                slotForBack.setItem(slotForBackItemStack);

                Slot slotToSeeConfig = pluginOptionSubMenu.getSlot(11);
                ItemStack slotToSeeConfigItemStack = new ItemStack(Material.PAPER);
                ItemMeta slotToSeeConfigItemMeta = slotToSeeConfigItemStack.getItemMeta();
                slotToSeeConfigItemMeta.setDisplayName("Access Config");
                slotToSeeConfigItemStack.setItemMeta(slotToSeeConfigItemMeta);
                slotToSeeConfig.setItem(slotToSeeConfigItemStack);

                pluginOptionsMenu.addBorder(pluginOptionSubMenu, isOn.get());

                if(isOn.get())
                {
                    slotPluginOptionsSubMenu.setItem(woolOn);
                } else
                {
                    slotPluginOptionsSubMenu.setItem(woolOff);
                }

                pluginOptionsMenu.displayMenu(playerThing, pluginOptionSubMenu);
                pluginOptionSubMenu.update();

                slotToSeeConfig.setClickHandler((playerSlotToSeeConfig, slotToSeeConfigInformation) ->
                {
                    MenuYML menuYML = new MenuYML();
                    Menu actualMenuYML = menuYML.createMenu(plugin.getName());
                    Slot everythingInConfigButInLore = actualMenuYML.getSlot(10);
                    ItemStack itemStackToShowTheThing = new ItemStack(Material.PAPER);
                    ItemMeta itemMetaToShowTheThing = itemStackToShowTheThing.getItemMeta();
                    itemMetaToShowTheThing.setDisplayName(ChatColor.YELLOW+"Click to print config.yml in chat or to show config below ");
                    List<String> list = new ArrayList<>();
                    everythingInConfigButInLore.setClickHandler((playerSlotToPrintConfig , infoSlotToSeeConfig) ->
                    {
                        for (String s : plugin.getConfig().getKeys(true))
                        {
                            if(!plugin.getConfig().get(s).toString().contains("MemorySection"))
                            {
                                list.add(ChatColor.GRAY + s + ": " +plugin.getConfig().get(s).toString());
                                playerSlotToSeeConfig.sendMessage(ChatColor.GRAY + s + ": " +plugin.getConfig().get(s).toString());
                            }
                        }
                        itemMetaToShowTheThing.setLore(list);
                        itemStackToShowTheThing.setItemMeta(itemMetaToShowTheThing);
                        everythingInConfigButInLore.setItem(itemStackToShowTheThing);
                        menuYML.addBorder(actualMenuYML);
                        menuYML.displayMenu(playerSlotToSeeConfig, actualMenuYML);

                    });

                    itemMetaToShowTheThing.setLore(list);
                    itemStackToShowTheThing.setItemMeta(itemMetaToShowTheThing);
                    everythingInConfigButInLore.setItem(itemStackToShowTheThing);
                    menuYML.addBorder(actualMenuYML);
                    menuYML.displayMenu(playerSlotToSeeConfig, actualMenuYML);

                });

                slotForBack.setClickHandler(((player1, clickInformation) ->
                {
                    mainMenu.displayMenu(player1 , menu);
                }));

                slotPluginOptionsSubMenu.setClickHandler((playerThing2, info2) ->
                {
                    if(isOn.get())
                    {
                        plugin.getPluginLoader().disablePlugin(plugin);
                        slotPluginOptionsSubMenu.setItem(woolOff);
                        isOn.set(false);
                        pluginOptionsMenu.addBorder(pluginOptionSubMenu, isOn.get());
                        playerThing2.sendMessage(ChatColor.RED+"PluginManager: Disabled plugin " + plugin.getName());

                    }else
                    {
                        plugin.getPluginLoader().enablePlugin(plugin);
                        slotPluginOptionsSubMenu.setItem(woolOn);
                        isOn.set(true);
                        pluginOptionsMenu.addBorder(pluginOptionSubMenu, isOn.get());
                        playerThing2.sendMessage(ChatColor.GREEN+"PluginManager: Enabled plugin " + plugin.getName());
                    }
                    pluginOptionSubMenu.update();
                });
            });
        }

        // Testing

        return true;
    }
}

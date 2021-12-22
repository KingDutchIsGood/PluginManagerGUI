package me.kingdutchisbad.spigotmc.pluginmanagergui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.Collections;
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
        if (args.length == 0 )  return false;
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
                Menu menu1 = pluginOptionsMenu.createMenu(plugin.getDescription().getDescription() , true);
                Slot slot1 = menu1.getSlot(10);
                ItemStack woolOn = new ItemStack(Material.GREEN_WOOL);
                ItemStack woolOff = new ItemStack(Material.RED_WOOL);
                ItemMeta woolOnItemMeta = woolOn.getItemMeta();
                ItemMeta woolOffItemMeta = woolOff.getItemMeta();

                woolOnItemMeta.setDisplayName(ChatColor.GREEN+plugin.getName());
                woolOn.setItemMeta(woolOnItemMeta);

                woolOffItemMeta.setDisplayName(ChatColor.RED+plugin.getName());
                woolOff.setItemMeta(woolOffItemMeta);
                pluginOptionsMenu.addBorder(menu1, isOn.get());
                if(isOn.get())
                {
                    slot1.setItem(woolOn);
                } else
                {
                    slot1.setItem(woolOff);
                }
                pluginOptionsMenu.displayMenu(playerThing, menu1);


                Slot slotForBack = menu1.getSlot(43);
                ItemStack itemStack1 = new ItemStack(Material.BARRIER);
                ItemMeta itemMeta1 = itemStack1.getItemMeta();
                itemMeta1.setDisplayName(ChatColor.RED+"Back");
                itemStack1.setItemMeta(itemMeta1);
                slotForBack.setItem(itemStack1);
                menu1.update();
              slotForBack.setClickHandler(((player1, clickInformation) ->
                {
                    mainMenu.displayMenu(player1 , menu);
                }));


                slot1.setClickHandler((playerThing2, info2) ->
                {
                    if(isOn.get())
                    {
                        plugin.getPluginLoader().disablePlugin(plugin);
                        slot1.setItem(woolOff);
                        isOn.set(false);
                        pluginOptionsMenu.addBorder(menu1, isOn.get());
                        playerThing2.sendMessage(ChatColor.RED+"PluginManager: Disabled plugin " + plugin.getName());

                    }else
                    {
                        plugin.getPluginLoader().enablePlugin(plugin);
                        slot1.setItem(woolOn);
                        isOn.set(true);
                        pluginOptionsMenu.addBorder(menu1, isOn.get());
                        playerThing2.sendMessage(ChatColor.GREEN+"PluginManager: Enabled plugin " + plugin.getName());
                    }
                    menu1.update();
                });
            });
        }

        // Testing

        return true;
    }
}

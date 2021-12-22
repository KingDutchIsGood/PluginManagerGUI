package me.kingdutchisbad.spigotmc.pluginmanagergui;

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
        Menu menu = createMenu();
        Slot slot = menu.getSlot(12);
        ItemStack itemStack = new ItemStack(Material.GREEN_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        // Testing
        assert itemMeta != null;
        Plugin pluginJAR = new GetPlugin (args[0]).getPluginJAR();
        itemMeta.setDisplayName(pluginJAR.getName());
        itemMeta.setLore(Collections.singletonList(pluginJAR.getDescription().getFullName().toString()));
        itemStack.setItemMeta(itemMeta);
        slot.setItem(itemStack);
        addWhiteBorder(menu);
        displayMenu(player, menu);
        menu.update();
        AtomicReference<Boolean> isOn = new AtomicReference<>(true);
        slot.setClickHandler((playerThing, info) -> {
            playerThing.sendMessage("You clicked the slot at index " + info.getClickedSlot().getIndex());
            pluginJAR.getPluginLoader().disablePlugin(pluginJAR);
            if(isOn.get())
            {
                playerThing.sendMessage("Disabled Plugin");
                isOn.set(false);
            }
            else
            {
                pluginJAR.getPluginLoader().enablePlugin(pluginJAR);
                playerThing.sendMessage("Enabled Plugin");
                isOn.set(true);
            }
        });
        return true;
    }

    public Menu createMenu()
    {
        return ChestMenu.builder(4)
                .title("All the plugins you have currently")
                .build();
    }

    public void displayMenu(Player player, Menu menu)
    {

        menu.open(player);
    }

    public void addWhiteBorder(Menu menu)
    {
        Mask mask = BinaryMask.builder(menu)
                .item(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
                .pattern("111111111") // First row
                .pattern("100000001") // Second row
                .pattern("100000001") // Third row
                .pattern("111111111").build(); // Forth row
        mask.apply(menu);
    }

}

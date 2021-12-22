package me.kingdutchisbad.spigotmc.pluginmanagergui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

public class MainMenu
{
    public Menu createMenu()
    {
        return ChestMenu.builder(6)
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
                .pattern("100000001") // Forth row
                .pattern("100000001") // Fifth row
                .pattern("111111111").build(); // Sixth row
        mask.apply(menu);
    }
}

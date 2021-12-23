package me.kingdutchisbad.spigotmc.pluginmanagergui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

public class MenuYML
{
    public Menu createMenu(String pluginName)
    {
        return ChestMenu.builder(6)
                .title(ChatColor.BLACK + pluginName)
                .build();
    }

    public void displayMenu(Player player, Menu menu)
    {
        menu.open(player);
    }

    public void addBorder(Menu menu)
    {
        Mask mask;
        mask = BinaryMask.builder(menu)
                    .item(new ItemStack(Material.RED_STAINED_GLASS_PANE))
                    .pattern("111111111") // First row
                    .pattern("100000001") // Second row
                    .pattern("100000001") // Third row
                    .pattern("100000001") // Forth row
                    .pattern("100000001") // Fifth row
                    .pattern("111111111").build(); //Sixth Row

        mask.apply(menu);
    }
}

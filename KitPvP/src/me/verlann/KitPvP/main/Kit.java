package me.verlann.KitPvP.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Kit implements CommandExecutor, Listener {

	public Kit(Main main) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("beast")) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command!");
				return true;
			}
			Player player = (Player) sender;
			openGUI(player);
			return true;
		}
		return false;
	}
	
	public void openGUI(Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Kit GUI");
		
		ItemStack item = new ItemStack(Material.BEACON);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.BLUE + "Center");
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GOLD + "Click to spawn!");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		inv.setItem(4, item);
		
		player.openInventory(inv);
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if(!ChatColor.stripColor(e.getView().getTitle().toString()).equalsIgnoreCase("Kit GUI")) return;
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getItemMeta() == null) return;
		if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Center")) {
			Player player = (Player) e.getWhoClicked();
			player.teleport(new Location(player.getWorld(), -2.5, 4, -3.5));
			e.setCancelled(true);
			player.closeInventory();
			player.getInventory().clear();
			kitbeast(player);
			return;
		}
		return;
		
	}
	
	public void kitbeast(Player p) {
		
        p.getInventory().clear();
        ItemStack beasthelmet = new ItemStack(Material.DIAMOND_HELMET);
        beasthelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack beastchestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        beastchestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        ItemStack beastleggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        beastleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack beastboots = new ItemStack(Material.DIAMOND_BOOTS);
        beastboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        p.getInventory().setHelmet(beasthelmet);
        p.getInventory().setChestplate(beastchestplate);
        p.getInventory().setLeggings(beastleggings);
        p.getInventory().setBoots(beastboots);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.sendMessage(ChatColor.AQUA + "Teleporting to center...");
		
	}
	
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}

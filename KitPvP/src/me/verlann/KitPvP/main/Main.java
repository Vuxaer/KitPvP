package me.verlann.KitPvP.main;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements CommandExecutor, Listener{
	
	@Override
	public void onEnable() {
		
		PluginManager pm = this.getServer().getPluginManager();	
		pm.registerEvents(new Kit(this), this);
		this.getServer().getPluginManager().registerEvents(this, this);
		
		this.getCommand("beast").setExecutor(new Kit(this));
		
		this.saveDefaultConfig();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//Stats player
		if (!(sender instanceof Player)) {
    		sender.sendMessage("Players only!");
    		return true;
    	}
    	Player p = (Player) sender;
    	if (cmd.getName().equalsIgnoreCase("stats")) {
    		
    		if (args.length == 1) {
    			Player t = p.getServer().getPlayer(args[0]);
    			String uuid = t.getUniqueId().toString();
    			
    			if (!getConfig().contains("Players." + uuid)) {
    				p.sendMessage("That player has never logged on to the server!");
    				return true;
    			}
    			
    			int kills = getConfig().getInt("Players. " + uuid + ".Kills");
    			int deaths = getConfig().getInt("Players. " + uuid + ".Deaths");
    			p.sendMessage("--------------------------\n" + ChatColor.AQUA + ChatColor.BOLD + "(Stats) " + t.getName() + ":\n" + ChatColor.RESET + "Kills: " + kills + "\n" + "Deaths: " + deaths + "\n--------------------------");
    			return true;
    			
    		}
    		
    	}
    	return true;
    }
	
    //Stats    
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	String uuid = p.getUniqueId().toString();
    	if(!getConfig().contains("Players." + uuid)) {
    		getConfig().set("Players." + uuid + ".Kills", 0);
    		getConfig().set("Players." + uuid + ".Deaths", 0);
    		saveConfig();
    	}
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
    	Player p = e.getEntity();
    	if(p.getKiller() instanceof Player) {
    		Player k = p.getKiller();
    		String pUUID = p.getUniqueId().toString();
    		String kUUID = k.getUniqueId().toString();
    		int kills = getConfig().getInt("Players." + kUUID + ".Kills");
    		int deaths = getConfig().getInt("Players." + pUUID + ".Deaths"); 
    		
    		getConfig().set("Players." + kUUID + ".Kills", kills +1);
    		getConfig().set("Players." + pUUID + ".Deaths", deaths +1);
    		saveConfig();
    		
        	e.getDrops().clear();
            e.setDeathMessage("" + ChatColor.AQUA + ChatColor.BOLD + p.getName() + ChatColor.RESET + " was killed by " + ChatColor.AQUA + ChatColor.BOLD + k.getName() + ChatColor.RESET + " (" + ChatColor.RED + Math.round((k.getHealth() / 2) * 100.0) / 100.0 + ChatColor.RESET + " hp)");
    	}
    }
    
	
    @EventHandler
    public void Respawn(PlayerRespawnEvent e) {
    	Player p = e.getPlayer();
    	p.setHealth(20);
    	p.getInventory().clear();
        p.teleport(new Location(p.getWorld(), 5.5, 44, 54.5));
    }
    
    public String color(String msg ) {
    	return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
  

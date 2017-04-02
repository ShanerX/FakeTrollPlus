package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Invlock implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Invlock(FakeTrollPlus instance) {		
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("inventory-lock.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.invlock")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /invlock <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			if (FakeTrollPlus.inventoryLock.contains(target.getUniqueId())) {
				sender.sendMessage(ChatColor.GOLD + "You have removed the lock on " + ChatColor.RED + target_name + ChatColor.GOLD + "'s inventory.");
				FakeTrollPlus.inventoryLock.remove(target.getUniqueId());
				return true;
			}
			sender.sendMessage(ChatColor.GOLD + "You have locked " + ChatColor.RED + target_name + ChatColor.GOLD + " out of their inventory.");
			FakeTrollPlus.inventoryLock.add(target.getUniqueId());

		return true;
	}

}
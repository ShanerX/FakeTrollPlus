package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Murder implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Murder(FakeTrollPlus instance) {
		plugin = instance;		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-murder")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.murder")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /murder <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			target.getGameMode();
			if (GameMode.CREATIVE != null) {
				target.setGameMode(GameMode.SURVIVAL);
				target.damage(20.0D);
				target.setGameMode(GameMode.CREATIVE);
			}
			else {
				target.damage(20.0D);
			}
			sender.sendMessage(ChatColor.GOLD + "Why did you do this to " + target_name + "?");

		return true;
	}

}
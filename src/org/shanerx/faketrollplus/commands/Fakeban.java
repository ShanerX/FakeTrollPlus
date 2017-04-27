package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakeban implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakeban(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("fake-ban.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.fakeban")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakeban <target> <reason>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			String reason = args[1];
			for (int i = 2; i < args.length; i++) {
				reason = reason + " " + args[i];
			}
			sender.sendMessage(ChatColor.GOLD + "Fake-banned " + target_name + " for \"" + reason + "\"");
			String banMessage = FakeTrollPlus.col(this.plugin.getConfig().getString("fake-ban.ban-message").replace("{REASON}", reason));
			target.kickPlayer(banMessage);
			Player staff = (Player)sender;
			String staff_name = staff.getDisplayName();
			if (this.plugin.getConfig().getBoolean("fake-ban.do-broadcast")) {
				String fakebanMessage = FakeTrollPlus.col(this.plugin.getConfig().getString("fake-ban.broadcast-msg"));
				fakebanMessage = fakebanMessage.replace("{PLAYER}", target.getName());
				fakebanMessage = fakebanMessage.replace("{STAFF}", staff_name);
				fakebanMessage = fakebanMessage.replace("{REASON}", reason);
				Bukkit.broadcastMessage(fakebanMessage);
			}

		return true;
	}

}
/*
 *     Copyright 2016-2017 ShanerX @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class Fakegod implements CommandExecutor {

	FakeTrollPlus plugin;

	public Fakegod(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.getBool("enable-fake-god")) {
			sender.sendMessage(Message.getString("message-for-disabled-cmds"));
			return false;
		}
		if (!sender.hasPermission("faketroll.fakegod")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return false;
		}
		if (args.length != 2) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /fakegod <target> <on|off>");
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		if (!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /fakegod <target> <on|off>");
			return true;
		}
		if (args[1].equalsIgnoreCase("on")) {
			target.sendMessage(ChatColor.GOLD + "God mode " + ChatColor.RED + "enabled" + ChatColor.GOLD + ".");
		} else {
			target.sendMessage(ChatColor.GOLD + "God mode " + ChatColor.RED + "disabled" + ChatColor.GOLD + ".");
		}
		return true;
	}

}
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

public class Hurt implements CommandExecutor {

	FakeTrollPlus plugin;

	public Hurt(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.hurt", Message.getBool("enable-hurt"), () -> args.length < 2 && !isNumber(args[1]))) {
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		final String target_name = target.getName();
		final int hp_dmg = Integer.parseInt(args[1]);
		final int dmg = hp_dmg / 2;
		if (target.getHealth() - hp_dmg <= 0.0D) {
			target.setHealth(0.0D);
		} else {
			target.setHealth(target.getHealth() - hp_dmg);
		}
		sender.sendMessage(ChatColor.GOLD + "Applied a " + dmg + " heart(s) damage to " + target_name + "!");
		return true;
	}
	
	private static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
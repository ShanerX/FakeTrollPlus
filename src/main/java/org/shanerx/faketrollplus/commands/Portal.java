/*
 *     Copyright 2016-2017 SparklingComet @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shanerx.faketrollplus.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Portal implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Portal(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.portal", Message.getBool("portal.enable"), () -> args.length != 1)) {
			return false;
		}
		
		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		String world = target.getLocation().getWorld().getName();
		if (world.contains("_the_end")) {
			sender.sendMessage(Message.PREFIX + Message.getString("portal.already-there").replace("%player%", target.getName()));
			return false;
		} else if (world.contains("_nether")) {
			world = world.replace("_nether", "");
		}
		
		World end = plugin.getServer().getWorld(world + "_the_end");
		final Location spawn = end.getHighestBlockAt(end.getSpawnLocation()).getLocation();
		target.teleport(spawn);
		
		sender.sendMessage(Message.PREFIX + Message.getString("portal.warped").replace("%player%", target.getName()));
		return true;
	}

}
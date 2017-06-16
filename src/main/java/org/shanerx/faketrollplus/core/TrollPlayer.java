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
package org.shanerx.faketrollplus.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.shanerx.faketrollplus.FakeTrollPlus;
import static org.shanerx.faketrollplus.core.TrollEffect.*;

@SuppressWarnings("unchecked")
public class TrollPlayer {

	private FakeTrollPlus ftp;
	private UUID uuid;

	TrollPlayer(UUID uuid, FakeTrollPlus plugin) {
		this.uuid = uuid;
		ftp = plugin;
	}

	TrollPlayer(OfflinePlayer op, FakeTrollPlus plugin) {
		this(op.getUniqueId(), plugin);
	}

	TrollPlayer(Player p, FakeTrollPlus plugin) {
		this(p.getUniqueId(), plugin);
	}

	/**
	 * Gets the {@link java.util.UUID} of the player associated with the
	 * TrollPlayer instance.
	 * 
	 * @return the UUID.
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Gets the instance {@link org.bukkit.OfflinePlayer} associated with the
	 * TrollPlayer instance.
	 * 
	 * @return the {@link org.bukkit.OfflinePlayer}.
	 */
	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	/**
	 * Gets the instance {@link org.bukkit.entity.Player} associated with the
	 * TrollPlayer instance.
	 *
	 * If the player is offline, the value {@code null} is returned.
	 *
	 * @return the {@link org.bukkit.entity.Player}.
	 */
	public Player getPlayer() {
		Player p = Bukkit.getPlayer(uuid);
		return p;
	}

	/**
	 * Gets the name of the player.
	 * 
	 * @return the name of the player.
	 */
	public String getName() {
		return getOfflinePlayer().getName();
	}

	/**
	 * Gets the {@link org.json.simple.JSONObject} holding all the information
	 * about the player, which is collected by the plugin.
	 * 
	 * @return the {@link org.json.simple.JSONObject} with the user's data.
	 */
	public JSONObject getUserInfo() {
		JSONObject root = ftp.getUserCache().getJSONConfiguration();
		JSONObject userInfo = (JSONObject) root.get(uuid.toString());
		return userInfo == null ? new JSONObject() : userInfo;
	}

	/**
	 * Checks whether the player can be trolled. <br>
	 * The trolling player needs to have proper permission.
	 * 
	 * @param sender the CommandSender.
	 *
	 * @return {@code true} if the player can be trolled.
	 */
	public boolean canTroll(CommandSender sender) {
		if (!ftp.getConfig().getBoolean("exempt-admins")) {
			return true;
		} else if (sender instanceof ConsoleCommandSender && ftp.getConfig().getBoolean("console-bypass")) {
			return true;
		}
		return !Bukkit.getPlayer(uuid).hasPermission("faketroll.exempt");
	}

	/**
	 * Checks whether the player is physically frozen.
	 * 
	 * @return {@code true} if frozen.
	 */
	public boolean isFrozen() {
		return get(FREEZE);
	}

	/**
	 * Checks whether the trolled player's chat messages will be changed to gibberish.
	 * 
	 * @return {@code true} if effect is enabled.
	 */
	public boolean chatIsGibberish() {
		return get(GIBBERISH);
	}

	/**
	 * Checks whether the player's inventory is locked.
	 * 
	 * @return {@code true} if yes.
	 */
	public boolean invIsLocked() {
		return get(INVENTORY_LOCK);
	}

	/**
	 * Checks whether the player can pickup items.
	 * 
	 * @return {@code false} if the No-Pickup effect is
	 *         <strong>enabled</strong>.
	 */
	public boolean canPickup() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return !root.containsKey(uuid.toString()) || (boolean) userInfo.get("canPickup");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Checks whether the player will be poisoned by any item they consume.
	 * 
	 * @return {@code true} if yes.
	 */
	public boolean hasBadfoodEffect() {
		return get(BADFOOD);
	}

	/**
	 * Checks whether mining a block will generate an explosion.
	 * 
	 * @return {@code true} if the player is affected.
	 */
	public boolean hasExplodeMinedBlocksEffect() {
		return get(EXPLODE_BLOCKS);
	}

	/**
	 * Checks whether the player is blacklisted by the plugin.
	 * 
	 * @return {@code true} if the player is blacklisted.
	 */
	public boolean isBlacklisted() {
		return get(BLACKLISTED);
	}
	
	/**
	 * Checks whether the player's chat is frozen.
	 *
	 * @return {@code true} if the player's chat is frozen.
	 */
	public boolean chatIsFrozen() {
		return get(FREEZE_CHAT);
	}

	/**
	 * Sets the player's freeze-status.
	 * 
	 * @param isFrozen
	 *            whether or not the player should freeze.
	 */
	public void setFrozen(boolean isFrozen) {
		set(FREEZE, isFrozen);
	}

	/**
	 * Adds or removes the Gibberish effect to/from the player.
	 * 
	 * @param chatIsGibberish
	 *            whether the player should talk gibberish.
	 */
	public void setGibberishChat(boolean chatIsGibberish) {
		set(GIBBERISH, chatIsGibberish);
	}

	/**
	 * Sets the lock-state on the player's inventory.
	 * 
	 * @param invIsLocked
	 *            whether the player should be locked out or not.
	 */
	public void setInvLocked(boolean invIsLocked) {
		set(INVENTORY_LOCK, invIsLocked);
	}

	/**
	 * Sets the state of the No-Pickup effect.
	 * 
	 * @param canPickup
	 *            whether the player should be able to pickup dropped items.
	 */
	public void setPickup(boolean canPickup) {
		set(NO_PICKUP, canPickup);
	}

	/**
	 * Adds or lifts the Badfood effect to/from the player.
	 * 
	 * @param hasBadfoodEffect
	 *            whether the player should be poisoned by food.
	 */
	public void setBadfoodEffect(boolean hasBadfoodEffect) {
		set(BADFOOD, hasBadfoodEffect);
	}

	/**
	 * Adds or lift the effect to/from the player.
	 * 
	 * @param hasExplodeMinedBlocksEffect
	 *            {@code true} if the blocks should explode.
	 */
	public void setExplodeMinedBlocksEffect(boolean hasExplodeMinedBlocksEffect) {
		set(EXPLODE_BLOCKS, hasExplodeMinedBlocksEffect);
	}

	/**
	 * Adds or removes a player's UUID from the blacklist.
	 * 
	 * @param isBlacklisted
	 *            whether or not to blacklist the player.
	 */
	public void setBlacklisted(boolean isBlacklisted) {
		set(BLACKLISTED, isBlacklisted);
	}
	
	/**
	 * Adds or lifts the freeze-chat effect to/from the player.
	 *
	 * @param chatIsFrozen
	 *            whether or not to freeze the chat.
	 */
	public void freezeChat(boolean chatIsFrozen) {
		set(FREEZE_CHAT, chatIsFrozen);
	}
	
	/**
	 * Returns a set of effects that are currently active on the player.
	 * @return all active effects
	 */
	public Set<TrollEffect> activeEffects() {
		Set<TrollEffect> active = new HashSet<>();
		if (hasBadfoodEffect()) {
			active.add(BADFOOD);
		}
		if (!canPickup()) {
			active.add(NO_PICKUP);
		}
		if (isFrozen()) {
			active.add(FREEZE);
		}
		if (chatIsGibberish()) {
			active.add(GIBBERISH);
		}
		if (invIsLocked()) {
			active.add(INVENTORY_LOCK);
		}
		if (hasExplodeMinedBlocksEffect()) {
			active.add(EXPLODE_BLOCKS);
		}
		if (isBlacklisted()) {
			active.add(BLACKLISTED);
		}
		if (chatIsFrozen()) {
			active.add(FREEZE_CHAT);
		}
		return active;
	}
	
	private boolean get(TrollEffect effect) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get(effect.config());
		} catch (NullPointerException npe) {
			return false;
		}
	}
	
	private void set(TrollEffect effect, boolean enable) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put(effect.config(), enable);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
/*
 *
 *      Copyright (c) 2016-2017 SparklingComet @ http://shanerx.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.shanerx.faketrollplus.core.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.shanerx.faketrollplus.FakeTrollPlus;

import java.util.UUID;

public interface UserCache {

    FakeTrollPlus getPlugin();
    
    JSONObject getJSONConfiguration();
    
    JSONObject getPlayerInfo(UUID uuid);
    
    JSONObject getPlayerInfo(OfflinePlayer op);
    
    JSONObject getPlayerInfo(Player p);
    
    TrollPlayer getTrollPlayer(UUID uuid);
    
    TrollPlayer getTrollPlayer(OfflinePlayer op);
    
    TrollPlayer getTrollPlayer(Player p);
    
    void update(JSONObject root);
    
}

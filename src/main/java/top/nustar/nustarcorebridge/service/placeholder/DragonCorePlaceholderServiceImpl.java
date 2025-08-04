/*
 *    NuStarCoreBridge
 *    Copyright (C) 2025  NuStar
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.nustar.nustarcorebridge.service.placeholder;

import eos.moe.dragoncore.network.PacketSender;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;

@Service
@DependsOn(classes = "eos.moe.dragoncore.DragonCore")
@SuppressWarnings("unused")
public class DragonCorePlaceholderServiceImpl implements PlaceholderService {
    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        PacketSender.sendSyncPlaceholder(player, Collections.singletonMap(placeholder, value));
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        PacketSender.sendSyncPlaceholder(player, placeholderMap);
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        Map<String, String> placeholderMap = new HashMap<>();
        for (Pair<String, String> pair : pairs) {
            placeholderMap.put(pair.getFirst(), pair.getSecond());
        }
        PacketSender.sendSyncPlaceholder(player, placeholderMap);
    }

    @Override
    public void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        PacketSender.sendDeletePlaceholderCache(player, placeholder, startsWith);
    }

    @Override
    public void removePlaceholders(Player player, String... placeholder) {
        for (String s : placeholder) {
            PacketSender.sendDeletePlaceholderCache(player, s, false);
        }
    }
}

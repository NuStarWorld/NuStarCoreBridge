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

import java.util.Map;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;
import yslelf.cloudpick.bukkit.api.PacketSender;

/**
 * @author : NuStar Date : 2025/7/23 01:29 Website : <a href="https://www.nustar.top">nustar's web</a> Github : <a
 *     href="https://github.com/nustarworld">nustar's github</a> QQ : 3318029085
 */
@Service
@DependsOn(classes = "yslelf.cloudpick.bukkit.CloudPick")
@SuppressWarnings("unused")
public class CloudPickPlaceholderServiceImpl implements PlaceholderService {
    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        PacketSender.sendCustomData(player, placeholder, value);
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        for (Map.Entry<String, String> placeholderEntry : placeholderMap.entrySet()) {
            PacketSender.sendCustomData(player, placeholderEntry.getKey(), placeholderEntry.getValue());
        }
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        for (Pair<String, String> pair : pairs) {
            PacketSender.sendCustomData(player, pair.getFirst(), pair.getSecond());
        }
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

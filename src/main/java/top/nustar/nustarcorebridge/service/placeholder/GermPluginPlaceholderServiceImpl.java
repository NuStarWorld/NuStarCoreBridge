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

import com.germ.germplugin.api.GermPacketAPI;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.PacketExecutorService;
import top.nustar.nustarcorebridge.api.service.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.Map;

@Service
@DependsOn(classes = "com.germ.germplugin.GermPlugin")
@SuppressWarnings("unused")
public class GermPluginPlaceholderServiceImpl implements PlaceholderService {

    private volatile PacketExecutorService packetExecutorService;

    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        packetExecutorService.submitAsyncTask(() -> GermPacketAPI.sendPlaceholder(player, placeholder, value));
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        packetExecutorService.submitAsyncTask(() -> placeholderMap.forEach((placeholder, value) -> GermPacketAPI.sendPlaceholder(player, placeholder, value)));
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        packetExecutorService.submitAsyncTask(() -> {
            for (Pair<String, String> pair : pairs) {
                GermPacketAPI.sendPlaceholder(player, pair.getFirst(), pair.getSecond());
            }
        });
    }

    @Override
    public void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        packetExecutorService.submitAsyncTask(() -> {
            if (startsWith) {
                GermPacketAPI.removePlaceholderIfContain(player, placeholder);
            } else {
                GermPacketAPI.removePlaceholder(player, placeholder);
            }
        });
    }

    @Override
    public void removePlaceholders(Player player, String... placeholder) {
        packetExecutorService.submitAsyncTask(() -> {
            for (String s : placeholder) {
                GermPacketAPI.removePlaceholder(player, s);
            }
        });
    }

    @Autowired
    public void setPacketExecutorService(PacketExecutorService packetExecutorService) {
        this.packetExecutorService = packetExecutorService;
    }
}

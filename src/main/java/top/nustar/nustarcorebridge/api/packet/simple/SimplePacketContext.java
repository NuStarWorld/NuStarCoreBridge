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

package top.nustar.nustarcorebridge.api.packet.simple;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.packet.sender.PacketSender;

/**
 * @author : NuStar Date : 2025/7/23 23:00 Website : <a href="https://www.nustar.top">nustar's web</a> Github : <a
 *     href="https://github.com/nustarworld">nustar's github</a> QQ : 3318029085
 */
@RequiredArgsConstructor
@Getter
public class SimplePacketContext<P> implements PacketContext<P> {
    private final PacketSender<P> packetSender;

    @NotNull
    private final List<String> arguments;

    @NotNull
    private final Map<String, Object> argMap;

    @Override
    public Map<String, Object> getArgMap() {
        return new ConcurrentHashMap<>(argMap);
    }

    @Override
    public Map<String, Object> getUnSafeArgMap() {
        return argMap;
    }
}

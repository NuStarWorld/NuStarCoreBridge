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

package top.nustar.nustarcorebridge.api.packet;

import top.nustar.nustarcorebridge.api.packet.context.PacketContext;

import java.util.Map;

public interface PacketEventBus {

    /**
     * 添加发包处理器
     * @param packetProcessors 发包处理器列表
     */
    @SuppressWarnings("unchecked")
    void addPacketProcessors(Class<? extends PacketProcessor>... packetProcessors);
    PacketEventBus addPacketProcessors(Class<? extends PacketProcessor> packetProcessors);

    void post(PacketContext<?> packetContext, String packetName, String handleName, Map<String, Object> argsMap);
}

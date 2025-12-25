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

import top.nustar.nustarcorebridge.api.packet.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketName;

import java.lang.reflect.Method;

public interface PacketProcessor {
    default String getPacketName() {
        PacketName packetName = this.getClass().getAnnotation(PacketName.class);
        return packetName.value();
    }

    default String getHandlerName(Method method) {
        return method.getAnnotation(PacketHandler.class).value();
    }
}

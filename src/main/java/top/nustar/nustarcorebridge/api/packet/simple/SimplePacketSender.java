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

import java.util.UUID;
import top.nustar.nustarcorebridge.api.packet.sender.PacketSender;

/**
 * @author NuStar<br>
 * @since 2025/12/25 23:38<br>
 */
public class SimplePacketSender<P> implements PacketSender<P> {
    @Override
    public P getSender() {
        return null;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public UUID getUid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public String getName() {
        return "Console";
    }
}

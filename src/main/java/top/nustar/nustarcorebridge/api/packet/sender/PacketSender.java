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

package top.nustar.nustarcorebridge.api.packet.sender;

import java.util.UUID;
import team.idealstate.sugar.validate.annotation.NotNull;

@SuppressWarnings("unused")
public interface PacketSender<T> {

    T getSender();

    /**
     * 获得发送者 UUID
     *
     * @return UUID
     */
    @NotNull
    UUID getUid();

    /**
     * 获得发送者的名称
     *
     * @return 名称
     */
    @NotNull
    String getName();

    /**
     * 向发送者发送一条消息
     *
     * @param message 发送的消息
     */
    void sendMessage(String message);

    /**
     * 是否为管理员
     *
     * @return 是否
     */
    boolean isOp();
}

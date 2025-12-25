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

package top.nustar.nustarcorebridge.api.packet.registry;

import top.nustar.nustarcorebridge.api.packet.context.PacketContext;

import java.util.Map;

/**
 * @author NuStar<br>
 * @since 2025/11/21 23:19<br>
 */
@SuppressWarnings("unused")
public interface PacketProcessorRegistry {

    /** 获得发包名称 */
    String getPacketName();

    /** 获得注册的所有方法列表，返回的是原始表副本 */
    Map<String, HandlerRegistry> getHandlerRegistry();

    /** 获得注册的所有方法列表，返回的是原始表 */
    Map<String, HandlerRegistry> getUnsafeHandlerRegistry();

    /**
     * 执行发包逻辑
     *
     * @param handlerName 方法名
     * @param packetContext 发包上下文
     * @param argMap 参数表
     */
    void invoke(String handlerName, PacketContext<?> packetContext, Map<String, Object> argMap);
}

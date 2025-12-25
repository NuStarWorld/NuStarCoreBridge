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

import java.lang.reflect.Parameter;
import java.util.Map;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;

/**
 * @author NuStar<br>
 * @since 2025/11/21 23:20<br>
 */
public interface HandlerRegistry {

    /** 获得方法的所有参数表，返回的是原始表的副本 */
    Map<PacketArgument, Parameter> getParameters();

    /** 获得方法的所有参数表，返回的是原始表 */
    Map<PacketArgument, Parameter> getUnsafeParameters();

    /**
     * 执行方法
     *
     * @param packetContext 发包上下文
     * @param argMap 参数表
     */
    void invoke(PacketContext<?> packetContext, Map<String, Object> argMap);
}

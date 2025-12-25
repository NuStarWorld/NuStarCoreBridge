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

package top.nustar.nustarcorebridge.api.packet.context;

import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.packet.sender.PacketSender;
import top.nustar.nustarcorebridge.api.packet.simple.SimplePacketContext;

import java.util.List;
import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/7/23 22:59
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@SuppressWarnings("unused")
public interface PacketContext<P> {

    /**
     * 包的发送者
     * @return 发送者
     */
    @NotNull
    PacketSender<P> getPacketSender();

    /**
     * 未处理的参数列表
     * @return 参数列表
     */
    @NotNull
    List<String> getArguments();

    /**
     * 经过插件协议处理后的参数表
     * @return 参数表
     */
    @NotNull
    Map<String, Object> getArgMap();

    /**
     * 经过插件协议处理后的原始参数表
     * @return 原始参数表
     */
    @NotNull
    Map<String, Object> getUnSafeArgMap();

    @NotNull
    static PacketContext<?> of(PacketSender<?> sender, @NotNull List<String> argument, @NotNull Map<String, Object> argMap) {
        return new SimplePacketContext<>(sender, argument, argMap);
    }
}

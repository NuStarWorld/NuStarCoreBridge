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

package top.nustar.nustarcorebridge.converter;

import java.util.Optional;
import java.util.UUID;
import top.nustar.nustarcorebridge.api.context.PacketContext;
import top.nustar.nustarcorebridge.api.converter.ArgumentConverter;

/**
 * @author : NuStar Date : 2025/7/24 00:46 Website : <a href="https://www.nustar.top">nustar's web</a> Github : <a
 *     href="https://github.com/nustarworld">nustar's github</a> QQ : 3318029085
 */
@SuppressWarnings("unused")
public class UidConverter implements ArgumentConverter {
    @Override
    public Optional<Object> convert(PacketContext context, String value) {
        try {
            return Optional.of(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}

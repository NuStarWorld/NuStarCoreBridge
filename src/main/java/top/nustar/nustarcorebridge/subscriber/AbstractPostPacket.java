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

package top.nustar.nustarcorebridge.subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : NuStar Date : 2025/7/24 02:09 Website : <a href="https://www.nustar.top">nustar's web</a> Github : <a
 *     href="https://github.com/nustarworld">nustar's github</a> QQ : 3318029085
 */
public abstract class AbstractPostPacket {

    public Map<String, String> getArgs(List<String> dataList) {
        Map<String, String> argsMap = new HashMap<>();
        List<String> dataClone = new ArrayList<>(dataList);
        dataClone.remove(0);
        if (!dataClone.isEmpty()) {
            for (String arg : dataClone) {
                String[] split = arg.split("=", -1);
                if (split.length != 2) continue;
                argsMap.put(split[0], split[1]);
            }
        }
        return argsMap;
    }
}

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

package top.nustar.nustarcorebridge;

import team.idealstate.minecraft.next.spigot.api.SpigotPlugin;
import team.idealstate.sugar.banner.Banner;
import team.idealstate.sugar.logging.Log;
import team.idealstate.sugar.next.context.Context;
import team.idealstate.sugar.next.context.annotation.feature.RegisterProperty;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;

@RegisterProperty(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false")
public class NuStarCoreBridge extends SpigotPlugin {
    @Override
    public void onInitialize(Context context) {}

    @Override
    public void onInitialized(Context context) {}

    @Override
    public void onLoad(Context context) {}

    @Override
    public void onLoaded(Context context) {}

    @Override
    public void onEnable(Context context) {
        Banner.lines(getClass()).forEach(Log::info);
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisable(Context context) {}

    @Override
    public void onDisabled(Context context) {}

    @Override
    public void onDestroy(Context context) {}

    @Override
    public void onDestroyed(Context context) {}
}

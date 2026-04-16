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

package top.nustar.nustarcorebridge.service;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import team.idealstate.sugar.logging.Log;
import team.idealstate.sugar.next.context.Context;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.aware.ContextAware;
import top.nustar.nustarcorebridge.api.service.PacketExecutorService;

/**
 * @author NuStar<br>
 * @since 2025/12/26 22:38<br>
 */
@Service
@SuppressWarnings("unused")
public class BukkitPacketExecutorService implements PacketExecutorService, ContextAware {

    private volatile Context context;

    @Override
    public void submitAsyncTask(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) context.getHolder(), () -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                Log.error(e);
            }
        });
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}

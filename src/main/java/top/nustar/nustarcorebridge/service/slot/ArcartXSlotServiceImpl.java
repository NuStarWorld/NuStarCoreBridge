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

package top.nustar.nustarcorebridge.service.slot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import priv.seventeen.artist.arcartx.api.ArcartXAPI;
import priv.seventeen.artist.arcartx.core.entity.data.ArcartXPlayer;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.SlotService;

/**
 * @author : NuStar
 * Date : 2025/7/23 21:55
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Service
@DependsOn(classes = "priv.seventeen.artist.arcartx.ArcartX")
@SuppressWarnings("unused")
public class ArcartXSlotServiceImpl implements SlotService {
    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        ArcartXPlayer arcartXPlayer = ArcartXAPI.getEntityManager().getPlayer(player);
        if (arcartXPlayer == null) throw new NullPointerException("ArcartXPlayer is null");
        arcartXPlayer.setSlotItemStackOnlyClient(identifier, item);
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        ArcartXPlayer arcartXPlayer = ArcartXAPI.getEntityManager().getPlayer(player);
        if (arcartXPlayer == null) throw new NullPointerException("ArcartXPlayer is null");
        return arcartXPlayer.getSlotItemStack(identifier);
    }
}

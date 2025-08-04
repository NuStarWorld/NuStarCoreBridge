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

import eos.moe.dragoncore.api.SlotAPI;
import eos.moe.dragoncore.network.PacketSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.SlotService;

@Service
@DependsOn(classes = "eos.moe.dragoncore.DragonCore")
@SuppressWarnings("unused")
public class DragonCoreSlotServiceImpl implements SlotService {

    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        PacketSender.putClientSlotItem(player, identifier, item);
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        return SlotAPI.getCacheSlotItem(player, identifier);
    }
}

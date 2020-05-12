/*
 * Copyright (c) 2019 HRZN LTD
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hrznstudio.galacticraft.screen;

import alexiil.mc.lib.attributes.item.compat.InventoryFixedWrapper;
import com.hrznstudio.galacticraft.block.entity.OxygenCollectorBlockEntity;
import com.hrznstudio.galacticraft.container.slot.ChargeSlot;
import net.fabricmc.fabric.api.container.ContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

/**
 * @author <a href="https://github.com/StellarHorizons">StellarHorizons</a>
 */
public class OxygenCollectorScreenHandler extends MachineScreenHandler<OxygenCollectorBlockEntity> {
    public static final ContainerFactory<ScreenHandler> FACTORY = createFactory(OxygenCollectorBlockEntity.class, OxygenCollectorScreenHandler::new);
    public final Property status = Property.create();
    public final Property oxygen = Property.create();
    public final Property lastCollectAmount = Property.create();

    public OxygenCollectorScreenHandler(int syncId, PlayerEntity playerEntity, OxygenCollectorBlockEntity blockEntity) {
        super(syncId, playerEntity, blockEntity);
        Inventory inventory = new InventoryFixedWrapper(blockEntity.getInventory()) {
            @Override
            public boolean canPlayerUse(PlayerEntity player) {
                return OxygenCollectorScreenHandler.this.canUse(player);
            }
        };
        addProperty(status);
        addProperty(oxygen);
        addProperty(lastCollectAmount);

        // Charging slot
        this.addSlot(new ChargeSlot(inventory, 0, 20, 70));

        // Player inventory slots
        int playerInvYOffset = 99;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerEntity.inventory, j + i * 9 + 9, 8 + j * 18, playerInvYOffset + i * 18));
            }
        }

        // Hotbar slots
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerEntity.inventory, i, 8 + i * 18, playerInvYOffset + 58));
        }
    }

    @Override
    public void sendContentUpdates() {
        status.set(blockEntity.status.ordinal());
        oxygen.set(blockEntity.getOxygen().getCurrentEnergy());
        lastCollectAmount.set(blockEntity.collectionAmount);
        super.sendContentUpdates();
    }

    @Override
    public void setProperty(int id, int value) {
        super.setProperty(id, value);
        blockEntity.status = OxygenCollectorBlockEntity.OxygenCollectorStatus.get(status.get());
    }
}
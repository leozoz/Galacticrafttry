package com.hrznstudio.galacticraft.entity.moonvillager;

import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class T1RocketEntity extends Entity {
    private int fuel;

    public T1RocketEntity(EntityType<T1RocketEntity> type, World world_1) {
        super(type, world_1);
    }

    @Override
    protected void initDataTracker() {
//        dataTracker.startTracking();
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.fuel = Math.max(tag.getInt("Fuel"), 0);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putInt("Fuel", Math.max(this.fuel, 0));
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void handleFallDamage(float float_1, float float_2) {
        super.handleFallDamage(float_1, float_2);
    }

    @Override
    public void tick() {
//        System.out.println("TICK! " + this.y);
//        double velY = Math.min(1, this.getVelocity().y + 1);

//        this.setVelocity(this.getVelocity().x, velY, this.getVelocity().z);
//        this.velocityModified = true;
//        this.velocityDirty = true;
        this.setPosition(this.x, this.y + 1, this.z);

        if (this.y >= 15) {
            this.world.createExplosion(this, this.x, this.y + (double) (this.getHeight() / 16.0F), this.z, 2.0F, Explosion.DestructionType.BREAK);
            this.remove();
        }

        super.tick();
    }
}
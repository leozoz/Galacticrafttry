package io.github.teamgalacticraft.galacticraft.world.dimension;

import io.github.teamgalacticraft.galacticraft.api.world.dimension.LowGravityDimension;
import io.github.teamgalacticraft.galacticraft.api.world.dimension.Oxygenless;
import io.github.teamgalacticraft.galacticraft.world.biome.GalacticraftBiomes;
import io.github.teamgalacticraft.galacticraft.world.gen.chunk.GalacticraftChunkGeneratorTypes;
import io.github.teamgalacticraft.galacticraft.world.gen.chunk.MoonChunkGeneratorConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.chunk.ChunkPos;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

/**
 * @author <a href="https://github.com/teamgalacticraft">TeamGalacticraft</a>
 */
public class MoonDimension extends Dimension implements LowGravityDimension, Oxygenless {

    public MoonDimension(World worldIn, DimensionType typeIn) {
        super(worldIn, typeIn);
    }

    @Override
    public int getMoonPhase(long long_1) {
        return 2;
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }

    @Override
    public boolean isNether() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float v, float v1) {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public BlockPos getForcedSpawnPoint() {
        return new BlockPos(0, 100, 0);
    }

    public ChunkGenerator<?> createChunkGenerator() {
        MoonChunkGeneratorConfig moonChunkGeneratorConfig = GalacticraftChunkGeneratorTypes.MOON.createSettings();
        return ChunkGeneratorType.SURFACE.create(this.world, BiomeSourceType.FIXED.applyConfig(BiomeSourceType.FIXED.getConfig().setBiome(GalacticraftBiomes.MOON)), moonChunkGeneratorConfig);
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean b) {
        return new BlockPos(0, 100, 0);
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int i, int i1, boolean b) {
        return new BlockPos(0, 100, 0);
    }

    @Override
    public float getSkyAngle(long l1, float l2) {
        int i = (int) (l1 % 24000L);
        float f = ((float) i + (float) l1) / 24000.0F - 0.25F;
        if (f < 0.0F) {
            ++f;
        }

        if (f > 1.0F) {
            --f;
        }

        float g = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
        f = f + (g - f) / 3.0F;
        return f;
    }

    public boolean hasVisibleSky() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public float[] getBackgroundColor(float l1, float f2) {
        return new float[]{0, 0, 0, 0};
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getCloudHeight() {
        return -1000.0F;
    }

    public boolean canPlayersSleep() {
        return false;
    }

    public boolean shouldRenderFog(int l1, int f2) {
        return false;
    }

    public DimensionType getType() {
        return GalacticraftDimensions.MOON;
    }
}

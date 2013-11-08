package net.minecraft.server;

import net.canarymod.api.world.DimensionType;

import java.io.File;

public class AnvilSaveHandler extends SaveHandler {

    public AnvilSaveHandler(File file1, String s0, boolean flag0, DimensionType type) {
        super(file1, s0, flag0, type);
    }

    public IChunkLoader a(WorldProvider worldprovider) {
        // CanaryMod changed the whole thing since we have recollection of the world type we're serving.
        // This means we can spare us the checks for instanceof generator
        // And just put together the proper save path
        return new AnvilChunkLoader(new File(getWorldBaseDir(), getBaseName() + "/" + getBaseName() + "_" + this.type.getName()));
    }

    public void a(WorldInfo worldinfo, NBTTagCompound nbttagcompound) {
        worldinfo.e(19133);
        super.a(worldinfo, nbttagcompound);
    }

    public void a() {
        try {
            ThreadedFileIOBase.a.a();
        }
        catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }

        RegionFileCache.a();
    }
}

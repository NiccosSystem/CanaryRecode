package net.minecraft.server;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.RedstoneChangeHook;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block {

    private String a;

    protected BlockBasePressurePlate(int i0, String s0, Material material) {
        super(i0, material);
        this.a = s0;
        this.a(CreativeTabs.d);
        this.b(true);
        this.c_(this.d(15));
    }

    public void a(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        this.c_(iblockaccess.h(i0, i1, i2));
    }

    protected void c_(int i0) {
        boolean flag0 = this.c(i0) > 0;
        float f0 = 0.0625F;

        if (flag0) {
            this.a(f0, 0.0F, f0, 1.0F - f0, 0.03125F, 1.0F - f0);
        }
        else {
            this.a(f0, 0.0F, f0, 1.0F - f0, 0.0625F, 1.0F - f0);
        }
    }

    public int a(World world) {
        return 20;
    }

    public AxisAlignedBB b(World world, int i0, int i1, int i2) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        return true;
    }

    public boolean c(World world, int i0, int i1, int i2) {
        return world.w(i0, i1 - 1, i2) || BlockFence.n_(world.a(i0, i1 - 1, i2));
    }

    public void a(World world, int i0, int i1, int i2, int i3) {
        boolean flag0 = false;

        if (!world.w(i0, i1 - 1, i2) && !BlockFence.n_(world.a(i0, i1 - 1, i2))) {
            flag0 = true;
        }

        if (flag0) {
            this.c(world, i0, i1, i2, world.h(i0, i1, i2), 0);
            world.i(i0, i1, i2);
        }
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        if (!world.I) {
            int i3 = this.c(world.h(i0, i1, i2));

            if (i3 > 0) {
                this.b(world, i0, i1, i2, i3);
            }
        }
    }

    public void a(World world, int i0, int i1, int i2, Entity entity) {
        if (!world.I) {
            int i3 = this.c(world.h(i0, i1, i2));

            if (i3 == 0) {
                this.b(world, i0, i1, i2, i3);
            }
        }
    }

    protected void b(World world, int i0, int i1, int i2, int i3) {
        int i4 = this.e(world, i0, i1, i2);

        // CanaryMod: RedstoneChange
        if (i3 != i4) {
            RedstoneChangeHook hook = (RedstoneChangeHook)new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), i3, i4).call();
            if (hook.isCanceled()) {
                i4 = this.d(hook.getOldLevel());
            }
        }
        //

        boolean flag0 = i3 > 0;
        boolean flag1 = i4 > 0;

        if (i3 != i4) {
            world.b(i0, i1, i2, this.d(i4), 2);
            this.b_(world, i0, i1, i2);
            world.g(i0, i1, i2, i0, i1, i2);
        }

        if (!flag1 && flag0) {
            world.a((double)i0 + 0.5D, (double)i1 + 0.1D, (double)i2 + 0.5D, "random.click", 0.3F, 0.5F);
        }
        else if (flag1 && !flag0) {
            world.a((double)i0 + 0.5D, (double)i1 + 0.1D, (double)i2 + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (flag1) {
            world.a(i0, i1, i2, this.cF, this.a(world));
        }
    }

    protected AxisAlignedBB a(int i0, int i1, int i2) {
        float f0 = 0.125F;

        return AxisAlignedBB.a().a((double)((float)i0 + f0), (double)i1, (double)((float)i2 + f0), (double)((float)(i0 + 1) - f0), (double)i1 + 0.25D, (double)((float)(i2 + 1) - f0));
    }

    public void a(World world, int i0, int i1, int i2, int i3, int i4) {
        // CanaryMod: RedstoneChange; block destructions
        int oldLvl = this.c(i4);
        if (oldLvl > 0) {
            new RedstoneChangeHook(new CanaryBlock((short)this.cF, (short)i3, i0, i1, i2, world.getCanaryWorld()), oldLvl, 0).call();
        }
        //

        if (this.c(i4) > 0) {
            this.b_(world, i0, i1, i2);
        }

        super.a(world, i0, i1, i2, i3, i4);
    }

    protected void b_(World world, int i0, int i1, int i2) {
        world.f(i0, i1, i2, this.cF);
        world.f(i0, i1 - 1, i2, this.cF);
    }

    public int b(IBlockAccess iblockaccess, int i0, int i1, int i2, int i3) {
        return this.c(iblockaccess.h(i0, i1, i2));
    }

    public int c(IBlockAccess iblockaccess, int i0, int i1, int i2, int i3) {
        return i3 == 1 ? this.c(iblockaccess.h(i0, i1, i2)) : 0;
    }

    public boolean f() {
        return true;
    }

    public void g() {
        float f0 = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;

        this.a(0.5F - f0, 0.5F - f1, 0.5F - f2, 0.5F + f0, 0.5F + f1, 0.5F + f2);
    }

    public int h() {
        return 1;
    }

    protected abstract int e(World world, int i0, int i1, int i2);

    protected abstract int c(int i0);

    protected abstract int d(int i0);
}

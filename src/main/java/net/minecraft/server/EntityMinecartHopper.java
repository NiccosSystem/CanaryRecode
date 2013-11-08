package net.minecraft.server;

import net.canarymod.api.entity.vehicle.CanaryHopperMinecart;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements Hopper {

    private boolean a = true;
    public int b = -1; // CanaryMod: private -> public

    public EntityMinecartHopper(World world) {
        super(world);
        this.entity = new CanaryHopperMinecart(this); // Wrap entity
    }

    public EntityMinecartHopper(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryHopperMinecart(this); // Wrap entity
    }

    public int l() {
        return 5;
    }

    public Block n() {
        return Block.cv;
    }

    public int r() {
        return 1;
    }

    public int j_() {
        return 5;
    }

    public boolean c(EntityPlayer entityplayer) {
        if (!this.q.I) {
            entityplayer.a(this);
        }

        return true;
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
        boolean flag1 = !flag0;

        if (flag1 != this.u()) {
            this.f(flag1);
        }
    }

    public boolean u() {
        return this.a;
    }

    public void f(boolean flag0) {
        this.a = flag0;
    }

    public World az() {
        return this.q;
    }

    public double aA() {
        return this.u;
    }

    public double aB() {
        return this.v;
    }

    public double aC() {
        return this.w;
    }

    public void l_() {
        super.l_();
        if (!this.q.I && this.T() && this.u()) {
            --this.b;
            if (!this.aE()) {
                this.l(0);
                if (this.aD()) {
                    this.l(4);
                    this.e();
                }
            }
        }
    }

    public boolean aD() {
        if (TileEntityHopper.a((Hopper)this)) {
            return true;
        }
        else {
            List list = this.q.a(EntityItem.class, this.E.b(0.25D, 0.0D, 0.25D), IEntitySelector.a);

            if (list.size() > 0) {
                TileEntityHopper.a((IInventory)this, (EntityItem)list.get(0));
            }

            return false;
        }
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        this.a(Block.cv.cF, 1, 0.0F);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("TransferCooldown", this.b);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.b = nbttagcompound.e("TransferCooldown");
    }

    public void l(int i0) {
        this.b = i0;
    }

    public boolean aE() {
        return this.b > 0;
    }
}

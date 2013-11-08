package net.minecraft.server;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.player.FoodExhaustionHook;
import net.canarymod.hook.player.FoodLevelHook;
import net.canarymod.hook.player.FoodSaturationHook;

public class FoodStats {

    private int a = 20;
    private float b = 5.0F;
    private float c;
    private int d = 0;
    private int e = 20;
    // CanaryMod: entity reference
    private EntityPlayer entityplayer;

    public FoodStats(EntityPlayer entityplayer) {
        this.entityplayer = entityplayer;
    }

    public void a(int i0, float f0) {
        // CanaryMod: FoodLevelHook
        FoodLevelHook lvl = (FoodLevelHook)new FoodLevelHook(((EntityPlayerMP)entityplayer).getPlayer(), this.a, Math.min(i0 + this.a, 20)).call();
        this.a = Math.min(Math.max(lvl.getNewValue(), 0), 20);
        //

        // CanaryMod: FoodSaturationHook
        FoodSaturationHook sat = (FoodSaturationHook)new FoodSaturationHook(((EntityPlayerMP)entityplayer).getPlayer(), this.b, Math.min(this.b + (float)i0 * f0 * 2.0F, (float)this.a)).call();
        this.b = Math.min(Math.max(sat.getNewValue(), 0.0F), (float)this.a);
        //
    }

    public void a(ItemFood itemfood) {
        this.a(itemfood.g(), itemfood.i());
    }

    public void a(EntityPlayer entityplayer) {
        int i0 = entityplayer.q.r;

        this.e = this.a;
        if (this.c > 4.0F) {
            // CanaryMod: FoodExhaustionHook
            FoodExhaustionHook exh = (FoodExhaustionHook)new FoodExhaustionHook(((EntityPlayerMP)entityplayer).getPlayer(), this.c, this.c - 4.0F).call();
            this.c = exh.getNewValue();
            //
            if (this.b > 0.0F) {
                // CanaryMod: FoodSaturationHook
                FoodSaturationHook sat = (FoodSaturationHook)new FoodSaturationHook(((EntityPlayerMP)entityplayer).getPlayer(), this.b, Math.max(this.b - 1.0F, 0.0F)).call();
                this.b = Math.max(Math.min(sat.getNewValue(), (float)this.a), 0.0F);
                //
            }
            else if (i0 > 0) {
                // CanaryMod: FoodLevelHook
                FoodLevelHook lvl = (FoodLevelHook)new FoodLevelHook(((EntityPlayerMP)entityplayer).getPlayer(), this.a, Math.max(this.a - 1, 0)).call();
                this.a = Math.max(Math.min(lvl.getNewValue(), 20), 0);
                //
            }
        }

        if (entityplayer.q.O().b("naturalRegeneration") && this.a >= 18 && entityplayer.bJ()) {
            ++this.d;
            if (this.d >= 80) {
                entityplayer.f(1.0F);
                this.a(3.0F);
                this.d = 0;
            }
        }
        else if (this.a <= 0) {
            ++this.d;
            if (this.d >= 80) {
                if (entityplayer.aN() > 10.0F || i0 >= 3 || entityplayer.aN() > 1.0F && i0 >= 2) {
                    // CanaryMod: DamageHook (starve)
                    DamageHook dmg = (DamageHook)new DamageHook(null, entityplayer.getCanaryEntity(), new CanaryDamageSource(DamageSource.f), 1.0F).call();
                    if (!dmg.isCanceled()) {
                        entityplayer.a((((CanaryDamageSource)dmg.getDamageSource()).getHandle()), dmg.getDamageDealt());
                    }
                    //
                }

                this.d = 0;
            }
        }
        else {
            this.d = 0;
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.b("foodLevel")) {
            this.a = nbttagcompound.e("foodLevel");
            this.d = nbttagcompound.e("foodTickTimer");
            this.b = nbttagcompound.g("foodSaturationLevel");
            this.c = nbttagcompound.g("foodExhaustionLevel");
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("foodLevel", this.a);
        nbttagcompound.a("foodTickTimer", this.d);
        nbttagcompound.a("foodSaturationLevel", this.b);
        nbttagcompound.a("foodExhaustionLevel", this.c);
    }

    public int a() {
        return this.a;
    }

    public boolean c() {
        return this.a < 20;
    }

    public void a(float f0) {
        // CanaryMod: FoodExhaustionHook
        FoodExhaustionHook exh = (FoodExhaustionHook)new FoodExhaustionHook(((EntityPlayerMP)entityplayer).getPlayer(), this.c, Math.min(this.c + f0, 40.0F)).call();
        this.c = exh.getNewValue();
        //
    }

    public float e() {
        return this.b;
    }

    // CanaryMod

    /**
     * Set the exhaustion level, overriding the old value
     *
     * @param f
     */
    public void setExhaustionLevel(float f) {
        this.c = Math.min(f, 40f);
    }

    public float getExhaustionLevel() {
        return this.c;
    }

    public void setFoodLevel(int food) {
        this.a = Math.min(food, 20);
    }
}

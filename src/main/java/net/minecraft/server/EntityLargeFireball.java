package net.minecraft.server;

import net.canarymod.api.entity.CanaryLargeFireball;
import net.canarymod.hook.entity.ProjectileHitHook;

public class EntityLargeFireball extends EntityFireball {

    public int e = 1;

    public EntityLargeFireball(World world) {
        super(world);
        this.entity = new CanaryLargeFireball(this); // CanaryMod: Wrap entity
    }

    public EntityLargeFireball(World world, EntityLivingBase entitylivingbase, double d0, double d1, double d2) {
        super(world, entitylivingbase, d0, d1, d2);
        this.entity = new CanaryLargeFireball(this); // CanaryMod: Wrap entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.q.I) {
            // CanaryMod: ProjectileHitHook
            ProjectileHitHook hook = (ProjectileHitHook)new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
            if (!hook.isCanceled()) { //
                if (movingobjectposition.g != null) {
                    movingobjectposition.g.a(DamageSource.a((EntityFireball)this, this.a), 6);
                }

                this.q.a((Entity)null, this.u, this.v, this.w, (float)this.e, true, this.q.O().b("mobGriefing"));
                this.x();
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ExplosionPower", this.e);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("ExplosionPower")) {
            this.e = nbttagcompound.e("ExplosionPower");
        }
    }
}

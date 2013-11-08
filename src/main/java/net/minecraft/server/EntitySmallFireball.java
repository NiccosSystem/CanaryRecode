package net.minecraft.server;

import net.canarymod.api.entity.CanarySmallFireball;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;

public class EntitySmallFireball extends EntityFireball {

    public EntitySmallFireball(World world) {
        super(world);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanarySmallFireball(this); // CanaryMod: wrap entity
    }

    public EntitySmallFireball(World world, EntityLivingBase entitylivingbase, double d0, double d1, double d2) {
        super(world, entitylivingbase, d0, d1, d2);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanarySmallFireball(this); // CanaryMod: wrap entity
    }

    public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(world, d0, d1, d2, d3, d4, d5);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanarySmallFireball(this); // CanaryMod: wrap entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.q.I) {
            // CanaryMod: ProjectileHit
            ProjectileHitHook hook = (ProjectileHitHook)new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
            if (!hook.isCanceled()) { //
                if (movingobjectposition.g != null) {
                    if (!movingobjectposition.g.F() && movingobjectposition.g.a(DamageSource.a((EntityFireball)this, this.a), 5)) {
                        movingobjectposition.g.d(5);
                    }
                }
                else {
                    int i0 = movingobjectposition.b;
                    int i1 = movingobjectposition.c;
                    int i2 = movingobjectposition.d;

                    switch (movingobjectposition.e) {
                        case 0:
                            --i1;
                            break;

                        case 1:
                            ++i1;
                            break;

                        case 2:
                            --i2;
                            break;

                        case 3:
                            ++i2;
                            break;

                        case 4:
                            --i0;
                            break;

                        case 5:
                            ++i0;
                    }

                    if (this.q.c(i0, i1, i2)) {
                        // CanaryMod: IgnitionHook
                        CanaryBlock block = (CanaryBlock)this.q.getCanaryWorld().getBlockAt(i0, i1 - 1, i2);
                        block.setStatus((byte)7); // 7 fireball hit
                        IgnitionHook ignitionHook = (IgnitionHook)new IgnitionHook(block, null, null, IgnitionCause.FIREBALL_HIT).call();
                        if (!ignitionHook.isCanceled()) {
                            this.q.c(i0, i1, i2, Block.aw.cF);
                        }
                        //
                    }
                }

                this.x();
            }
        }
    }

    public boolean L() {
        return false;
    }

    public boolean a(DamageSource damagesource, float f0) {
        return false;
    }
}

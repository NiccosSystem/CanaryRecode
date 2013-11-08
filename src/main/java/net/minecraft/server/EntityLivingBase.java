package net.minecraft.server;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.canarymod.api.entity.living.humanoid.EntityNonPlayableCharacter;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.potion.CanaryPotionEffect;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.entity.EntityDeathHook;
import net.canarymod.hook.entity.EntityMoveHook;
import net.canarymod.hook.entity.PotionEffectAppliedHook;
import net.canarymod.hook.entity.PotionEffectFinishHook;
import net.canarymod.hook.entity.VehicleMoveHook;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class EntityLivingBase extends Entity {

    private static final UUID b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    private static final AttributeModifier c = (new AttributeModifier(b, "Sprinting speed boost", 0.30000001192092896D, 2)).a(false);
    private BaseAttributeMap d;
    private final CombatTracker e = new CombatTracker(this);
    private final HashMap f = new HashMap();
    private final ItemStack[] g = new ItemStack[5];
    public boolean au;
    public int av;
    public int aw;
    public float ax;
    public int ay;
    public int az;
    public float aA;
    public int aB;
    public int aC;
    public float aD;
    public float aE;
    public float aF;
    public float aG;
    public float aH;
    public int aI = 20;
    public float aJ;
    public float aK;
    public float aL;
    public float aM;
    public float aN;
    public float aO;
    public float aP;
    public float aQ;
    public float aR = 0.02F;
    protected EntityPlayer aS;
    protected int aT;
    protected boolean aU;
    protected int aV;
    protected float aW;
    protected float aX;
    protected float aY;
    protected float aZ;
    protected float ba;
    protected int bb;
    protected float bc;
    protected boolean bd;
    public float be;
    public float bf;
    protected float bg;
    protected int bh;
    protected double bi;
    protected double bj;
    protected double bk;
    protected double bl;
    protected double bm;
    private boolean h = true;
    private EntityLivingBase i;
    private int j;
    private EntityLivingBase bn;
    private int bo;
    private float bp;
    private int bq;
    private float br;

    public EntityLivingBase(World world) {
        super(world);
        this.az();
        this.g(this.aT());
        this.m = true;
        this.aM = (float)(Math.random() + 1.0D) * 0.01F;
        this.b(this.u, this.v, this.w);
        this.aL = (float)Math.random() * 12398.0F;
        this.A = (float)(Math.random() * 3.1415927410125732D * 2.0D);
        this.aP = this.A;
        this.Y = 0.5F;
    }

    protected void a() {
        this.ah.a(7, Integer.valueOf(0));
        this.ah.a(8, Byte.valueOf((byte)0));
        this.ah.a(9, Byte.valueOf((byte)0));
        this.ah.a(6, Float.valueOf(1.0F));
    }

    protected void az() {
        this.aX().b(SharedMonsterAttributes.a);
        this.aX().b(SharedMonsterAttributes.c);
        this.aX().b(SharedMonsterAttributes.d);
        if (!this.bf()) {
            this.a(SharedMonsterAttributes.d).a(0.10000000149011612D);
        }
    }

    protected void a(double d0, boolean flag0) {
        if (!this.H()) {
            this.I();
        }

        if (flag0 && this.T > 0.0F) {
            int i0 = MathHelper.c(this.u);
            int i1 = MathHelper.c(this.v - 0.20000000298023224D - (double)this.N);
            int i2 = MathHelper.c(this.w);
            int i3 = this.q.a(i0, i1, i2);

            if (i3 == 0) {
                int i4 = this.q.e(i0, i1 - 1, i2);

                if (i4 == 11 || i4 == 32 || i4 == 21) {
                    i3 = this.q.a(i0, i1 - 1, i2);
                }
            }

            if (i3 > 0) {
                Block.s[i3].a(this.q, i0, i1, i2, this, this.T);
            }
        }

        super.a(d0, flag0);
    }

    public boolean aA() {
        return false;
    }

    public void y() {
        this.aD = this.aE;
        super.y();
        this.q.C.a("livingEntityBaseTick");
        if (this.T() && this.U()) {
            // CanaryMod: call DamageHook (Suffocation)
            DamageHook hook = (DamageHook)new DamageHook(null, entity, new CanaryDamageSource(DamageSource.d), 1.0F).call();
            if (!hook.isCanceled()) {
                this.a((((CanaryDamageSource)hook.getDamageSource()).getHandle()), hook.getDamageDealt());
            }
            //
        }

        if (this.F() || this.q.I) {
            this.B();
        }

        boolean flag0 = this instanceof EntityPlayer && ((EntityPlayer)this).bG.a;

        if (this.T() && this.a(Material.h)) {
            if (!this.aA() && !this.i(Potion.o.H) && !flag0) {
                this.g(this.h(this.al()));
                if (this.al() == -20) {
                    this.g(0);

                    // CanaryMod - drowning damage.
                    DamageHook hook = (DamageHook)new DamageHook(null, entity, new CanaryDamageSource(DamageSource.e), 2.0F).call();
                    if (!hook.isCanceled()) {
                        for (int i0 = 0; i0 < 8; ++i0) {
                            float f0 = this.ab.nextFloat() - this.ab.nextFloat();
                            float f1 = this.ab.nextFloat() - this.ab.nextFloat();
                            float f2 = this.ab.nextFloat() - this.ab.nextFloat();

                            this.q.a("bubble", this.u + (double)f0, this.v + (double)f1, this.w + (double)f2, this.x, this.y, this.z);
                        }

                        this.a((((CanaryDamageSource)hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    }
                    //
                }
            }

            this.B();
            if (!this.q.I && this.ag() && this.o instanceof EntityLivingBase) {
                this.a((Entity)null);
            }
        }
        else {
            this.g(300);
        }

        this.aJ = this.aK;
        if (this.aC > 0) {
            --this.aC;
        }

        if (this.ay > 0) {
            --this.ay;
        }

        if (this.af > 0) {
            --this.af;
        }

        if (this.aN() <= 0.0F) {
            this.aB();
        }

        if (this.aT > 0) {
            --this.aT;
        }
        else {
            this.aS = null;
        }

        if (this.bn != null && !this.bn.T()) {
            this.bn = null;
        }

        if (this.i != null && !this.i.T()) {
            this.b((EntityLivingBase)null);
        }

        this.aJ();
        this.aZ = this.aY;
        this.aO = this.aN;
        this.aQ = this.aP;
        this.C = this.A;
        this.D = this.B;
        this.q.C.b();
    }

    public boolean g_() {
        return false;
    }

    protected void aB() {
        ++this.aB;
        if (this.aB == 20) {
            int i0;

            if (!this.q.I && (this.aT > 0 || this.aC()) && !this.g_() && this.q.O().b("doMobLoot")) {
                i0 = this.e(this.aS);

                while (i0 > 0) {
                    int i1 = EntityXPOrb.a(i0);

                    i0 -= i1;
                    this.q.d((Entity)(new EntityXPOrb(this.q, this.u, this.v, this.w, i1)));
                }
            }

            this.x();

            for (i0 = 0; i0 < 20; ++i0) {
                double d0 = this.ab.nextGaussian() * 0.02D;
                double d1 = this.ab.nextGaussian() * 0.02D;
                double d2 = this.ab.nextGaussian() * 0.02D;

                this.q.a("explode", this.u + (double)(this.ab.nextFloat() * this.O * 2.0F) - (double)this.O, this.v + (double)(this.ab.nextFloat() * this.P), this.w + (double)(this.ab.nextFloat() * this.O * 2.0F) - (double)this.O, d0, d1, d2);
            }
        }
    }

    protected int h(int i0) {
        int i1 = EnchantmentHelper.b(this);

        return i1 > 0 && this.ab.nextInt(i1 + 1) > 0 ? i0 : i0 - 1;
    }

    protected int e(EntityPlayer entityplayer) {
        return 0;
    }

    protected boolean aC() {
        return false;
    }

    public Random aD() {
        return this.ab;
    }

    public EntityLivingBase aE() {
        return this.i;
    }

    public int aF() {
        return this.j;
    }

    public void b(EntityLivingBase entitylivingbase) {
        this.i = entitylivingbase;
        this.j = this.ac;
    }

    public EntityLivingBase aG() {
        return this.bn;
    }

    public int aH() {
        return this.bo;
    }

    public void k(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.bn = (EntityLivingBase)entity;
        }
        else {
            this.bn = null;
        }

        this.bo = this.ac;
    }

    public int aI() {
        return this.aV;
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("HealF", this.aN());
        nbttagcompound.a("Health", (short)((int)Math.ceil((double)this.aN())));
        nbttagcompound.a("HurtTime", (short)this.ay);
        nbttagcompound.a("DeathTime", (short)this.aB);
        nbttagcompound.a("AttackTime", (short)this.aC);
        nbttagcompound.a("AbsorptionAmount", this.bn());
        ItemStack[] aitemstack = this.ae();
        int i0 = aitemstack.length;

        int i1;
        ItemStack itemstack;

        for (i1 = 0; i1 < i0; ++i1) {
            itemstack = aitemstack[i1];
            if (itemstack != null) {
                this.d.a(itemstack.D());
            }
        }

        nbttagcompound.a("Attributes", (NBTBase)SharedMonsterAttributes.a(this.aX()));
        aitemstack = this.ae();
        i0 = aitemstack.length;

        for (i1 = 0; i1 < i0; ++i1) {
            itemstack = aitemstack[i1];
            if (itemstack != null) {
                this.d.b(itemstack.D());
            }
        }

        if (!this.f.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();
            Iterator iterator = this.f.values().iterator();

            while (iterator.hasNext()) {
                PotionEffect potioneffect = (PotionEffect)iterator.next();

                nbttaglist.a((NBTBase)potioneffect.a(new NBTTagCompound()));
            }

            nbttagcompound.a("ActiveEffects", (NBTBase)nbttaglist);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.m(nbttagcompound.g("AbsorptionAmount"));
        if (nbttagcompound.b("Attributes") && this.q != null && !this.q.I) {
            SharedMonsterAttributes.a(this.aX(), nbttagcompound.m("Attributes"), this.q == null ? null : this.q.Y());
        }

        if (nbttagcompound.b("ActiveEffects")) {
            NBTTagList nbttaglist = nbttagcompound.m("ActiveEffects");

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.b(i0);
                PotionEffect potioneffect = PotionEffect.b(nbttagcompound1);

                this.f.put(Integer.valueOf(potioneffect.a()), potioneffect);
            }
        }

        if (nbttagcompound.b("HealF")) {
            this.g(nbttagcompound.g("HealF"));
        }
        else {
            NBTBase nbtbase = nbttagcompound.a("Health");

            if (nbtbase == null) {
                this.g(this.aT());
            }
            else if (nbtbase.a() == 5) {
                this.g(((NBTTagFloat)nbtbase).a);
            }
            else if (nbtbase.a() == 2) {
                this.g((float)((NBTTagShort)nbtbase).a);
            }
        }

        this.ay = nbttagcompound.d("HurtTime");
        this.aB = nbttagcompound.d("DeathTime");
        this.aC = nbttagcompound.d("AttackTime");
    }

    protected void aJ() {
        Iterator iterator = this.f.keySet().iterator();

        while (iterator.hasNext()) {
            Integer integer = (Integer)iterator.next();
            PotionEffect potioneffect = (PotionEffect)this.f.get(integer);

            if (!potioneffect.a(this)) {
                if (!this.q.I) {
                    iterator.remove();
                    this.b(potioneffect);
                }
            }
            else if (potioneffect.b() % 600 == 0) {
                this.a(potioneffect, false);
            }
        }

        int i0;

        if (this.h) {
            if (!this.q.I) {
                if (this.f.isEmpty()) {
                    this.ah.b(8, Byte.valueOf((byte)0));
                    this.ah.b(7, Integer.valueOf(0));
                    this.d(false);
                }
                else {
                    i0 = PotionHelper.a(this.f.values());
                    this.ah.b(8, Byte.valueOf((byte)(PotionHelper.b(this.f.values()) ? 1 : 0)));
                    this.ah.b(7, Integer.valueOf(i0));
                    this.d(this.i(Potion.p.H));
                }
            }

            this.h = false;
        }

        i0 = this.ah.c(7);
        boolean flag0 = this.ah.a(8) > 0;

        if (i0 > 0) {
            boolean flag1 = false;

            if (!this.aj()) {
                flag1 = this.ab.nextBoolean();
            }
            else {
                flag1 = this.ab.nextInt(15) == 0;
            }

            if (flag0) {
                flag1 &= this.ab.nextInt(5) == 0;
            }

            if (flag1 && i0 > 0) {
                double d0 = (double)(i0 >> 16 & 255) / 255.0D;
                double d1 = (double)(i0 >> 8 & 255) / 255.0D;
                double d2 = (double)(i0 >> 0 & 255) / 255.0D;

                this.q.a(flag0 ? "mobSpellAmbient" : "mobSpell", this.u + (this.ab.nextDouble() - 0.5D) * (double)this.O, this.v + this.ab.nextDouble() * (double)this.P - (double)this.N, this.w + (this.ab.nextDouble() - 0.5D) * (double)this.O, d0, d1, d2);
            }
        }
    }

    public void aK() {
        Iterator iterator = this.f.keySet().iterator();

        while (iterator.hasNext()) {
            Integer integer = (Integer)iterator.next();
            PotionEffect potioneffect = (PotionEffect)this.f.get(integer);

            if (!this.q.I) {
                iterator.remove();
                this.b(potioneffect);
            }
        }
    }

    public Collection aL() {
        return this.f.values();
    }

    public boolean i(int i0) {
        return this.f.containsKey(Integer.valueOf(i0));
    }

    public boolean a(Potion potion) {
        return this.f.containsKey(Integer.valueOf(potion.H));
    }

    public PotionEffect b(Potion potion) {
        return (PotionEffect)this.f.get(Integer.valueOf(potion.H));
    }

    public void c(PotionEffect potioneffect) {
        if (this.d(potioneffect)) {
            // CanaryMod: PotionEffectApplied
            PotionEffectAppliedHook hook = (PotionEffectAppliedHook)new PotionEffectAppliedHook((net.canarymod.api.entity.living.LivingBase)getCanaryEntity(), new CanaryPotionEffect(potioneffect)).call();
            if (hook.getPotionEffect() == null) {
                return;
            }
            potioneffect = ((CanaryPotionEffect)hook.getPotionEffect()).getHandle();
            //
            if (this.d(potioneffect)) {
                if (this.f.containsKey(Integer.valueOf(potioneffect.a()))) {
                    ((PotionEffect)this.f.get(Integer.valueOf(potioneffect.a()))).a(potioneffect);
                    this.a((PotionEffect)this.f.get(Integer.valueOf(potioneffect.a())), true);
                }
                else {
                    this.f.put(Integer.valueOf(potioneffect.a()), potioneffect);
                    this.a(potioneffect);
                }
            }
        }
    }

    public boolean d(PotionEffect potioneffect) {
        if (this.aY() == EnumCreatureAttribute.b) {
            int i0 = potioneffect.a();

            if (i0 == Potion.l.H || i0 == Potion.u.H) {
                return false;
            }
        }

        return true;
    }

    public boolean aM() {
        return this.aY() == EnumCreatureAttribute.b;
    }

    public void k(int i0) {
        PotionEffect potioneffect = (PotionEffect)this.f.remove(Integer.valueOf(i0));

        if (potioneffect != null) {
            this.b(potioneffect);
        }
    }

    protected void a(PotionEffect potioneffect) {
        this.h = true;
        if (!this.q.I) {
            Potion.a[potioneffect.a()].b(this, this.aX(), potioneffect.c());
        }
    }

    protected void a(PotionEffect potioneffect, boolean flag0) {
        this.h = true;
        if (flag0 && !this.q.I) {
            Potion.a[potioneffect.a()].a(this, this.aX(), potioneffect.c());
            Potion.a[potioneffect.a()].b(this, this.aX(), potioneffect.c());
        }
    }

    protected void b(PotionEffect potioneffect) {
        this.h = true;
        if (!this.q.I) {
            // CanaryMod: PotionEffectFinish
            new PotionEffectFinishHook((net.canarymod.api.entity.living.LivingBase)getCanaryEntity(), new CanaryPotionEffect(potioneffect)).call();
            //
            Potion.a[potioneffect.a()].a(this, this.aX(), potioneffect.c());
        }
    }

    public void f(float f0) {
        float f1 = this.aN();

        if (f1 > 0.0F) {
            this.g(f1 + f0);
        }
    }

    public final float aN() {
        return this.ah.d(6);
    }

    public void g(float f0) {
        this.ah.b(6, Float.valueOf(MathHelper.a(f0, 0.0F, this.aT())));
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.ar()) {
            return false;
        }
        else if (this.q.I) {
            return false;
        }
        else {
            this.aV = 0;
            if (this.aN() <= 0.0F) {
                return false;
            }
            else if (damagesource.m() && this.a(Potion.n)) {
                return false;
            }
            else {
                if ((damagesource == DamageSource.m || damagesource == DamageSource.n) && this.n(4) != null) {
                    this.n(4).a((int)(f0 * 4.0F + this.ab.nextFloat() * f0 * 2.0F), this);
                    f0 *= 0.75F;
                }

                this.aG = 1.5F;
                boolean flag0 = true;

                // CanaryMod: call DamageHook (Entity)
                CanaryEntityLiving attacker = null;

                if (damagesource instanceof EntityDamageSource && (damagesource).h() instanceof EntityLiving) {
                    attacker = (CanaryEntityLiving)(damagesource).h().getCanaryEntity();
                }
                DamageHook hook = new DamageHook(attacker, entity, new CanaryDamageSource(damagesource), (int)f0);

                if ((float)this.af > (float)this.aI / 2.0F) {
                    if (f0 <= this.bc) {
                        return false;
                    }

                    hook.setDamageDealt((int)(f0 - this.bc));
                    if (attacker != null) {
                        hook.call();
                    }
                    if (hook.isCanceled()) {
                        if (this instanceof EntityCreature) {
                            // MERGE: Can't find substitute to what it was before (c) - Chris
                            ((EntityCreature)this).aA = 0;
                        }
                        return false;
                    }

                    this.d((((CanaryDamageSource)hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    this.bc = f0;
                    flag0 = false;
                }
                else {
                    if (attacker != null) {
                        hook.call();
                    }
                    if (hook.isCanceled()) {
                        if (this instanceof EntityCreature) {
                            ((EntityCreature)this).aA = 0;
                        }
                        return false;
                    }
                    this.bc = f0;
                    this.ax = this.aN();
                    this.af = this.aI;
                    this.d((((CanaryDamageSource)hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    this.ay = this.az = 10;
                }
                //

                this.aA = 0.0F;
                Entity entity = damagesource.i();

                if (entity != null) {
                    if (entity instanceof EntityLivingBase) {
                        this.b((EntityLivingBase)entity);
                    }

                    if (entity instanceof EntityPlayer) {
                        this.aT = 100;
                        this.aS = (EntityPlayer)entity;
                    }
                    else if (entity instanceof EntityWolf) {
                        EntityWolf entitywolf = (EntityWolf)entity;

                        if (entitywolf.bT()) {
                            this.aT = 100;
                            this.aS = null;
                        }
                    }
                }

                if (flag0) {
                    this.q.a((Entity)this, (byte)2);
                    if (damagesource != DamageSource.e) {
                        this.K();
                    }

                    if (entity != null) {
                        double d0 = entity.u - this.u;

                        double d1;

                        for (d1 = entity.w - this.w; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                            d0 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.aA = (float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - this.A;
                        this.a(entity, f0, d0, d1);
                    }
                    else {
                        this.aA = (float)((int)(Math.random() * 2.0D) * 180);
                    }
                }

                if (this.aN() <= 0.0F) {
                    if (flag0) {
                        this.a(this.aP(), this.ba(), this.bb());
                    }

                    this.a(damagesource);
                }
                else if (flag0) {
                    this.a(this.aO(), this.ba(), this.bb());
                }

                return true;
            }
        }
    }

    public void a(ItemStack itemstack) {
        this.a("random.break", 0.8F, 0.8F + this.q.s.nextFloat() * 0.4F);

        for (int i0 = 0; i0 < 5; ++i0) {
            Vec3 vec3 = this.q.V().a(((double)this.ab.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

            vec3.a(-this.B * 3.1415927F / 180.0F);
            vec3.b(-this.A * 3.1415927F / 180.0F);
            Vec3 vec31 = this.q.V().a(((double)this.ab.nextFloat() - 0.5D) * 0.3D, (double)(-this.ab.nextFloat()) * 0.6D - 0.3D, 0.6D);

            vec31.a(-this.B * 3.1415927F / 180.0F);
            vec31.b(-this.A * 3.1415927F / 180.0F);
            vec31 = vec31.c(this.u, this.v + (double)this.f(), this.w);
            this.q.a("iconcrack_" + itemstack.b().cv, vec31.c, vec31.d, vec31.e, vec3.c, vec3.d + 0.05D, vec3.e);
        }
    }

    public void a(DamageSource damagesource) {
        // CanaryMod: EntityDeath
        new EntityDeathHook(this.getCanaryEntity(), damagesource.getCanaryDamageSource()).call();
        //
        Entity entity = damagesource.i();
        EntityLivingBase entitylivingbase = this.aS();

        if (this.bb >= 0 && entitylivingbase != null) {
            entitylivingbase.b(this, this.bb);
        }

        if (entity != null) {
            entity.a(this);
        }

        this.aU = true;
        if (!this.q.I) {
            int i0 = 0;

            if (entity instanceof EntityPlayer) {
                i0 = EnchantmentHelper.g((EntityLivingBase)entity);
            }

            if (!this.g_() && this.q.O().b("doMobLoot")) {
                this.b(this.aT > 0, i0);
                this.a(this.aT > 0, i0);
                if (this.aT > 0) {
                    int i1 = this.ab.nextInt(200) - i0;

                    if (i1 < 5) {
                        this.l(i1 <= 0 ? 1 : 0);
                    }
                }
            }
        }

        this.q.a((Entity)this, (byte)3);
    }

    protected void a(boolean flag0, int i0) {
    }

    public void a(Entity entity, float f0, double d0, double d1) {
        if (this.ab.nextDouble() >= this.a(SharedMonsterAttributes.c).e()) {
            this.an = true;
            float f1 = MathHelper.a(d0 * d0 + d1 * d1);
            float f2 = 0.4F;

            this.x /= 2.0D;
            this.y /= 2.0D;
            this.z /= 2.0D;
            this.x -= d0 / (double)f1 * (double)f2;
            this.y += (double)f2;
            this.z -= d1 / (double)f1 * (double)f2;
            if (this.y > 0.4000000059604645D) {
                this.y = 0.4000000059604645D;
            }
        }
    }

    protected String aO() {
        return "damage.hit";
    }

    protected String aP() {
        return "damage.hit";
    }

    protected void l(int i0) {
    }

    protected void b(boolean flag0, int i0) {
    }

    public boolean e() {
        int i0 = MathHelper.c(this.u);
        int i1 = MathHelper.c(this.E.b);
        int i2 = MathHelper.c(this.w);
        int i3 = this.q.a(i0, i1, i2);

        return i3 == Block.aK.cF || i3 == Block.bz.cF;
    }

    public boolean T() {
        return !this.M && this.aN() > 0.0F;
    }

    protected void b(float f0) {
        super.b(f0);
        PotionEffect potioneffect = this.b(Potion.j);
        float f1 = potioneffect != null ? (float)(potioneffect.c() + 1) : 0.0F;
        int i0 = MathHelper.f(f0 - 3.0F - f1);

        if (i0 > 0) {
            // CanaryMod: call DamageHook (Fall)
            DamageHook hook = (DamageHook)new DamageHook(null, entity, new CanaryDamageSource(DamageSource.h), i0).call();
            if (!hook.isCanceled()) {
                if (i0 > 4) {
                    this.a("damage.fallbig", 1.0F, 1.0F);
                }
                else {
                    this.a("damage.fallsmall", 1.0F, 1.0F);
                }

                this.a((((CanaryDamageSource)hook.getDamageSource()).getHandle()), hook.getDamageDealt());
            }
            //

            int i1 = this.q.a(MathHelper.c(this.u), MathHelper.c(this.v - 0.20000000298023224D - (double)this.N), MathHelper.c(this.w));

            if (i1 > 0) {
                StepSound stepsound = Block.s[i1].cS;

                this.a(stepsound.e(), stepsound.c() * 0.5F, stepsound.d() * 0.75F);
            }
        }
    }

    public int aQ() {
        int i0 = 0;
        ItemStack[] aitemstack = this.ae();
        int i1 = aitemstack.length;

        for (int i2 = 0; i2 < i1; ++i2) {
            ItemStack itemstack = aitemstack[i2];

            if (itemstack != null && itemstack.b() instanceof ItemArmor) {
                int i3 = ((ItemArmor)itemstack.b()).c;

                i0 += i3;
            }
        }

        return i0;
    }

    protected void h(float f0) {
    }

    protected float b(DamageSource damagesource, float f0) {
        if (!damagesource.e()) {
            int i0 = 25 - this.aQ();
            float f1 = f0 * (float)i0;

            this.h(f0);
            f0 = f1 / 25.0F;
        }

        return f0;
    }

    protected float c(DamageSource damagesource, float f0) {
        if (this instanceof EntityZombie) {
            f0 = f0;
        }

        int i0;
        int i1;
        float f1;

        if (this.a(Potion.m) && damagesource != DamageSource.i) {
            i0 = (this.b(Potion.m).c() + 1) * 5;
            i1 = 25 - i0;
            f1 = f0 * (float)i1;
            f0 = f1 / 25.0F;
        }

        if (f0 <= 0.0F) {
            return 0.0F;
        }
        else {
            i0 = EnchantmentHelper.a(this.ae(), damagesource);
            if (i0 > 20) {
                i0 = 20;
            }

            if (i0 > 0 && i0 <= 20) {
                i1 = 25 - i0;
                f1 = f0 * (float)i1;
                f0 = f1 / 25.0F;
            }

            return f0;
        }
    }

    protected void d(DamageSource damagesource, float f0) {
        if (!this.ar()) {
            f0 = this.b(damagesource, f0);
            f0 = this.c(damagesource, f0);
            float f1 = f0;

            f0 = Math.max(f0 - this.bn(), 0.0F);
            this.m(this.bn() - (f1 - f0));
            if (f0 != 0.0F) {
                float f2 = this.aN();

                this.g(f2 - f0);
                this.aR().a(damagesource, f2, f0);
                this.m(this.bn() - f0);
            }
        }
    }

    public CombatTracker aR() {
        return this.e;
    }

    public EntityLivingBase aS() {
        return (EntityLivingBase)(this.e.c() != null ? this.e.c() : (this.aS != null ? this.aS : (this.i != null ? this.i : null)));
    }

    public final float aT() {
        return (float)this.a(SharedMonsterAttributes.a).e();
    }

    public final int aU() {
        return this.ah.a(9);
    }

    public final void m(int i0) {
        this.ah.b(9, Byte.valueOf((byte)i0));
    }

    private int h() {
        return this.a(Potion.e) ? 6 - (1 + this.b(Potion.e).c()) * 1 : (this.a(Potion.f) ? 6 + (1 + this.b(Potion.f).c()) * 2 : 6);
    }

    public void aV() {
        if (!this.au || this.av >= this.h() / 2 || this.av < 0) {
            this.av = -1;
            this.au = true;
            if (this.q instanceof WorldServer) {
                ((WorldServer)this.q).q().a((Entity)this, (Packet)(new Packet18Animation(this, 1)));
            }
        }
    }

    protected void C() {
        this.a(DamageSource.i, 4.0F);
    }

    protected void aW() {
        int i0 = this.h();

        if (this.au) {
            ++this.av;
            if (this.av >= i0) {
                this.av = 0;
                this.au = false;
            }
        }
        else {
            this.av = 0;
        }

        this.aE = (float)this.av / (float)i0;
    }

    public AttributeInstance a(Attribute attribute) {
        return this.aX().a(attribute);
    }

    public BaseAttributeMap aX() {
        if (this.d == null) {
            this.d = new ServersideAttributeMap();
        }

        return this.d;
    }

    public EnumCreatureAttribute aY() {
        return EnumCreatureAttribute.a;
    }

    public abstract ItemStack aZ();

    public abstract ItemStack n(int i0);

    public abstract void c(int i0, ItemStack itemstack);

    public void c(boolean flag0) {
        super.c(flag0);
        AttributeInstance attributeinstance = this.a(SharedMonsterAttributes.d);

        if (attributeinstance.a(b) != null) {
            attributeinstance.b(c);
        }

        if (flag0) {
            attributeinstance.a(c);
        }
    }

    public abstract ItemStack[] ae();

    protected float ba() {
        return 1.0F;
    }

    protected float bb() {
        return this.g_() ? (this.ab.nextFloat() - this.ab.nextFloat()) * 0.2F + 1.5F : (this.ab.nextFloat() - this.ab.nextFloat()) * 0.2F + 1.0F;
    }

    protected boolean bc() {
        return this.aN() <= 0.0F;
    }

    public void a(double d0, double d1, double d2) {
        this.b(d0, d1, d2, this.A, this.B);
    }

    public void l(Entity entity) {
        double d0 = entity.u;
        double d1 = entity.E.b + (double)entity.P;
        double d2 = entity.w;

        for (double d3 = -1.5D; d3 < 2.0D; ++d3) {
            for (double d4 = -1.5D; d4 < 2.0D; ++d4) {
                if (d3 != 0.0D || d4 != 0.0D) {
                    int i0 = (int)(this.u + d3);
                    int i1 = (int)(this.w + d4);
                    AxisAlignedBB axisalignedbb = this.E.c(d3, 1.0D, d4);

                    if (this.q.a(axisalignedbb).isEmpty()) {
                        if (this.q.w(i0, (int)this.v, i1)) {
                            this.a(this.u + d3, this.v + 1.0D, this.w + d4);
                            return;
                        }

                        if (this.q.w(i0, (int)this.v - 1, i1) || this.q.g(i0, (int)this.v - 1, i1) == Material.h) {
                            d0 = this.u + d3;
                            d1 = this.v + 1.0D;
                            d2 = this.w + d4;
                        }
                    }
                }
            }
        }

        this.a(d0, d1, d2);
    }

    protected void be() {
        this.y = 0.41999998688697815D;
        if (this.a(Potion.j)) {
            this.y += (double)((float)(this.b(Potion.j).c() + 1) * 0.1F);
        }

        if (this.ai()) {
            float f0 = this.A * 0.017453292F;

            this.x -= (double)(MathHelper.a(f0) * 0.2F);
            this.z += (double)(MathHelper.b(f0) * 0.2F);
        }

        this.an = true;
    }

    public void e(float f0, float f1) {
        double d0;

        if (this.H() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).bG.b)) {
            d0 = this.v;
            this.a(f0, f1, this.bf() ? 0.04F : 0.02F);
            this.d(this.x, this.y, this.z);
            this.x *= 0.800000011920929D;
            this.y *= 0.800000011920929D;
            this.z *= 0.800000011920929D;
            this.y -= 0.02D;
            if (this.G && this.c(this.x, this.y + 0.6000000238418579D - this.v + d0, this.z)) {
                this.y = 0.30000001192092896D;
            }
        }
        else if (this.J() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).bG.b)) {
            d0 = this.v;
            this.a(f0, f1, 0.02F);
            this.d(this.x, this.y, this.z);
            this.x *= 0.5D;
            this.y *= 0.5D;
            this.z *= 0.5D;
            this.y -= 0.02D;
            if (this.G && this.c(this.x, this.y + 0.6000000238418579D - this.v + d0, this.z)) {
                this.y = 0.30000001192092896D;
            }
        }
        else {
            float f2 = 0.91F;

            if (this.F) {
                f2 = 0.54600006F;
                int i0 = this.q.a(MathHelper.c(this.u), MathHelper.c(this.E.b) - 1, MathHelper.c(this.w));

                if (i0 > 0) {
                    f2 = Block.s[i0].cV * 0.91F;
                }
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;

            if (this.F) {
                f4 = this.bg() * f3;
            }
            else {
                f4 = this.aR;
            }

            this.a(f0, f1, f4);
            f2 = 0.91F;
            if (this.F) {
                f2 = 0.54600006F;
                int i1 = this.q.a(MathHelper.c(this.u), MathHelper.c(this.E.b) - 1, MathHelper.c(this.w));

                if (i1 > 0) {
                    f2 = Block.s[i1].cV * 0.91F;
                }
            }

            if (this.e()) {
                float f5 = 0.15F;

                if (this.x < (double)(-f5)) {
                    this.x = (double)(-f5);
                }

                if (this.x > (double)f5) {
                    this.x = (double)f5;
                }

                if (this.z < (double)(-f5)) {
                    this.z = (double)(-f5);
                }

                if (this.z > (double)f5) {
                    this.z = (double)f5;
                }

                this.T = 0.0F;
                if (this.y < -0.15D) {
                    this.y = -0.15D;
                }

                boolean flag0 = this.ah() && this instanceof EntityPlayer;

                if (flag0 && this.y < 0.0D) {
                    this.y = 0.0D;
                }
            }

            this.d(this.x, this.y, this.z);
            if (this.G && this.e()) {
                this.y = 0.2D;
            }

            if (this.q.I && (!this.q.f((int)this.u, 0, (int)this.w) || !this.q.d((int)this.u, (int)this.w).d)) {
                if (this.v > 0.0D) {
                    this.y = -0.1D;
                }
                else {
                    this.y = 0.0D;
                }
            }
            else {
                this.y -= 0.08D;
            }

            this.y *= 0.9800000190734863D;
            this.x *= (double)f2;
            this.z *= (double)f2;
        }

        this.aF = this.aG;
        d0 = this.u - this.r;
        double d1 = this.w - this.t;
        float f6 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F) {
            f6 = 1.0F;
        }

        this.aG += (f6 - this.aG) * 0.4F;
        this.aH += this.aG;
    }

    protected boolean bf() {
        return false;
    }

    public float bg() {
        return this.bf() ? this.bp : 0.1F;
    }

    public void i(float f0) {
        this.bp = f0;
    }

    public boolean m(Entity entity) {
        this.k(entity);
        return false;
    }

    public boolean bh() {
        return false;
    }

    public void l_() {
        super.l_();
        if (!this.q.I) {
            int i0 = this.aU();

            if (i0 > 0) {
                if (this.aw <= 0) {
                    this.aw = 20 * (30 - i0);
                }

                --this.aw;
                if (this.aw <= 0) {
                    this.m(i0 - 1);
                }
            }

            for (int i1 = 0; i1 < 5; ++i1) {
                ItemStack itemstack = this.g[i1];
                ItemStack itemstack1 = this.n(i1);

                if (!ItemStack.b(itemstack1, itemstack)) {
                    ((WorldServer)this.q).q().a((Entity)this, (Packet)(new Packet5PlayerInventory(this.k, i1, itemstack1)));
                    if (itemstack != null) {
                        this.d.a(itemstack.D());
                    }

                    if (itemstack1 != null) {
                        this.d.b(itemstack1.D());
                    }

                    this.g[i1] = itemstack1 == null ? null : itemstack1.m();
                }
            }
        }

        this.c();
        double d0 = this.u - this.r;
        double d1 = this.w - this.t;
        float f0 = (float)(d0 * d0 + d1 * d1);
        float f1 = this.aN;
        float f2 = 0.0F;

        this.aW = this.aX;
        float f3 = 0.0F;

        if (f0 > 0.0025000002F) {
            f3 = 1.0F;
            f2 = (float)Math.sqrt((double)f0) * 3.0F;
            f1 = (float)Math.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
        }

        if (this.aE > 0.0F) {
            f1 = this.A;
        }

        if (!this.F) {
            f3 = 0.0F;
        }

        this.aX += (f3 - this.aX) * 0.3F;
        this.q.C.a("headTurn");
        f2 = this.f(f1, f2);
        this.q.C.b();
        this.q.C.a("rangeChecks");

        while (this.A - this.C < -180.0F) {
            this.C -= 360.0F;
        }

        while (this.A - this.C >= 180.0F) {
            this.C += 360.0F;
        }

        while (this.aN - this.aO < -180.0F) {
            this.aO -= 360.0F;
        }

        while (this.aN - this.aO >= 180.0F) {
            this.aO += 360.0F;
        }

        while (this.B - this.D < -180.0F) {
            this.D -= 360.0F;
        }

        while (this.B - this.D >= 180.0F) {
            this.D += 360.0F;
        }

        while (this.aP - this.aQ < -180.0F) {
            this.aQ -= 360.0F;
        }

        while (this.aP - this.aQ >= 180.0F) {
            this.aQ += 360.0F;
        }

        this.q.C.b();
        this.aY += f2;
    }

    protected float f(float f0, float f1) {
        float f2 = MathHelper.g(f0 - this.aN);

        this.aN += f2 * 0.3F;
        float f3 = MathHelper.g(this.A - this.aN);
        boolean flag0 = f3 < -90.0F || f3 >= 90.0F;

        if (f3 < -75.0F) {
            f3 = -75.0F;
        }

        if (f3 >= 75.0F) {
            f3 = 75.0F;
        }

        this.aN = this.A - f3;
        if (f3 * f3 > 2500.0F) {
            this.aN += f3 * 0.2F;
        }

        if (flag0) {
            f1 *= -1.0F;
        }

        return f1;
    }

    public void c() {
        double prevX = this.r, prevY = this.s, prevZ = this.t;
        float prevR = this.A, prevP = this.B;
        if (this.bq > 0) {
            --this.bq;
        }

        if (this.bh > 0) {
            double d0 = this.u + (this.bi - this.u) / (double)this.bh;
            double d1 = this.v + (this.bj - this.v) / (double)this.bh;
            double d2 = this.w + (this.bk - this.w) / (double)this.bh;
            double d3 = MathHelper.g(this.bl - (double)this.A);

            this.A = (float)((double)this.A + d3 / (double)this.bh);
            this.B = (float)((double)this.B + (this.bm - (double)this.B) / (double)this.bh);
            --this.bh;
            this.b(d0, d1, d2);
            this.b(this.A, this.B);
        }
        else if (!this.bm()) {
            this.x *= 0.98D;
            this.y *= 0.98D;
            this.z *= 0.98D;
        }

        if (Math.abs(this.x) < 0.005D) {
            this.x = 0.0D;
        }

        if (Math.abs(this.y) < 0.005D) {
            this.y = 0.0D;
        }

        if (Math.abs(this.z) < 0.005D) {
            this.z = 0.0D;
        }

        this.q.C.a("ai");
        if (this.bc()) {
            this.bd = false;
            this.be = 0.0F;
            this.bf = 0.0F;
            this.bg = 0.0F;
        }
        else if (this.bm()) {
            if (this.bf()) {
                this.q.C.a("newAi");
                this.bi();
                this.q.C.b();
            }
            else {
                this.q.C.a("oldAi");
                this.bl();
                this.q.C.b();
                this.aP = this.A;
            }
        }
        // CanaryMod: NPC Addition, this is vital for pathfinding
        // Just call the "newAi" method up there ^^
        if (this instanceof EntityNonPlayableCharacter) {
            this.bi();
        }// END
        this.q.C.b();
        this.q.C.a("jump");
        if (this.bd) {
            if (!this.H() && !this.J()) {
                if (this.F && this.bq == 0) {
                    this.be();
                    this.bq = 10;
                }
            }
            else {
                this.y += 0.03999999910593033D;
            }
        }
        else {
            this.bq = 0;
        }

        this.q.C.b();
        this.q.C.a("travel");
        this.be *= 0.98F;
        this.bf *= 0.98F;
        this.bg *= 0.9F;
        this.e(this.be, this.bf);
        this.q.C.b();
        // CanaryMod: EntityMoveHook
        if (!(this instanceof EntityPlayerMP) && (Math.floor(this.r) != Math.floor(this.u) || Math.floor(this.s) != Math.floor(this.v) || Math.floor(this.t) != Math.floor(this.w))) {
            if (this instanceof EntityPig && ((EntityPig)this).bT() && this.n != null && this.n instanceof EntityPlayerMP) {
                // Its a Pig Vehicle! This part is a bit ugly but its so far the only point i found that connects to pigs and actual moving
                // CanaryMod: VehcileMoveHook (Pig) --
                Vector3D from = new Vector3D(this.r, this.s, this.t);
                Vector3D to = new Vector3D(this.u, this.v, this.w);
                VehicleMoveHook vmh = (VehicleMoveHook)new VehicleMoveHook((Vehicle)this.entity, from, to).call();
                // --
                Location fromL = new Location(getCanaryWorld(), this.r, this.s, this.t, this.B, this.A);// Remember rotation and pitch are swapped in Location constructor...
                EntityMoveHook emh = (EntityMoveHook)new EntityMoveHook(entity, fromL).call();
                if (vmh.isCanceled() || emh.isCanceled()) {
                    this.x = 0.0D;
                    this.y = 0.0D;
                    this.z = 0.0D;
                    this.b(this.r, this.s, this.t, prevR, prevP);
                    this.r = prevX;
                    this.s = prevY;
                    this.t = prevZ;
                    this.V(); // Update rider
                    if (this.n instanceof EntityPlayerMP) {
                        double ox = Math.cos((double)this.A * 3.141592653589793D / 180.0D) * 0.4D;
                        double oz = Math.sin((double)this.A * 3.141592653589793D / 180.0D) * 0.4D;
                        ((EntityPlayerMP)this.n).a.b(new Packet13PlayerLookMove(this.u + ox, this.v + this.Y() + this.n.X(), this.v + this.Y(), this.w + oz, this.n.A, this.n.B, this.F));
                        this.n.x = 0.0D;
                        this.n.y = 0.0D;
                        this.n.z = 0.0D;
                    }
                }
            }
            else {
                Location from = new Location(getCanaryWorld(), this.r, this.s, this.t, this.B, this.A);// Remember rotation and pitch are swapped in Location constructor...
                EntityMoveHook hook = (EntityMoveHook)new EntityMoveHook(entity, from).call();
                if (hook.isCanceled()) {
                    this.b(this.r, this.s, this.t, prevR, prevP);
                    this.r = prevX;
                    this.s = prevY;
                    this.t = prevZ;
                    this.V(); // Update rider
                    if (this.n != null && this.n instanceof EntityPlayerMP) {
                        double ox = Math.cos((double)this.A * 3.141592653589793D / 180.0D) * 0.4D;
                        double oz = Math.sin((double)this.A * 3.141592653589793D / 180.0D) * 0.4D;
                        ((EntityPlayerMP)this.n).a.b(new Packet13PlayerLookMove(this.u + ox, this.v + this.Y() + this.n.X(), this.v + this.Y(), this.w + oz, this.n.A, this.n.B, this.F));
                    }
                }
            }
        }
        //
        this.q.C.a("push");
        if (!this.q.I) {
            this.bj();
        }

        this.q.C.b();
    }

    protected void bi() {
    }

    protected void bj() {
        List list = this.q.b((Entity)this, this.E.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty()) {
            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity = (Entity)list.get(i0);

                if (entity.M()) {
                    this.n(entity);
                }
            }
        }
    }

    protected void n(Entity entity) {
        entity.f((Entity)this);
    }

    public void V() {
        super.V();
        this.aW = this.aX;
        this.aX = 0.0F;
        this.T = 0.0F;
    }

    protected void bk() {
    }

    protected void bl() {
        ++this.aV;
    }

    public void f(boolean flag0) {
        this.bd = flag0;
    }

    public void a(Entity entity, int i0) {
        if (!entity.M && !this.q.I) {
            EntityTracker entitytracker = ((WorldServer)this.q).q();

            if (entity instanceof EntityItem) {
                entitytracker.a(entity, (Packet)(new Packet22Collect(entity.k, this.k)));
            }

            if (entity instanceof EntityArrow) {
                entitytracker.a(entity, (Packet)(new Packet22Collect(entity.k, this.k)));
            }

            if (entity instanceof EntityXPOrb) {
                entitytracker.a(entity, (Packet)(new Packet22Collect(entity.k, this.k)));
            }
        }
    }

    public boolean o(Entity entity) {
        return this.q.a(this.q.V().a(this.u, this.v + (double)this.f(), this.w), this.q.V().a(entity.u, entity.v + (double)entity.f(), entity.w)) == null;
    }

    public Vec3 aa() {
        return this.j(1.0F);
    }

    public Vec3 j(float f0) {
        float f1;
        float f2;
        float f3;
        float f4;

        if (f0 == 1.0F) {
            f1 = MathHelper.b(-this.A * 0.017453292F - 3.1415927F);
            f2 = MathHelper.a(-this.A * 0.017453292F - 3.1415927F);
            f3 = -MathHelper.b(-this.B * 0.017453292F);
            f4 = MathHelper.a(-this.B * 0.017453292F);
            return this.q.V().a((double)(f2 * f3), (double)f4, (double)(f1 * f3));
        }
        else {
            f1 = this.D + (this.B - this.D) * f0;
            f2 = this.C + (this.A - this.C) * f0;
            f3 = MathHelper.b(-f2 * 0.017453292F - 3.1415927F);
            f4 = MathHelper.a(-f2 * 0.017453292F - 3.1415927F);
            float f5 = -MathHelper.b(-f1 * 0.017453292F);
            float f6 = MathHelper.a(-f1 * 0.017453292F);

            return this.q.V().a((double)(f4 * f5), (double)f6, (double)(f3 * f5));
        }
    }

    public boolean bm() {
        return !this.q.I;
    }

    public boolean L() {
        return !this.M;
    }

    public boolean M() {
        return !this.M;
    }

    public float f() {
        return this.P * 0.85F;
    }

    protected void K() {
        this.J = this.ab.nextDouble() >= this.a(SharedMonsterAttributes.c).e();
    }

    public float ap() {
        return this.aP;
    }

    public float bn() {
        return this.br;
    }

    public void m(float f0) {
        if (f0 < 0.0F) {
            f0 = 0.0F;
        }

        this.br = f0;
    }

    public Team bo() {
        return null;
    }

    public boolean c(EntityLivingBase entitylivingbase) {
        return this.a(entitylivingbase.bo());
    }

    public boolean a(Team team) {
        return this.bo() != null ? this.bo().a(team) : false;
    }

    // CanaryMod
    public void setAge(int age) {
        this.aV = age;
    }

    //CanaryMod
    public void removeAllPotionEffects() {
        Iterator iterator = this.f.values().iterator();
        while (iterator.hasNext()) {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            this.b(potioneffect);
        }
    }
}

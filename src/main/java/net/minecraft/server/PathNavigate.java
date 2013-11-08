package net.minecraft.server;

import net.canarymod.api.CanaryPathFinder;

public class PathNavigate {

    private EntityLiving a;
    private World b;
    private PathEntity c;
    public double d; // CanaryMod: private => public
    private AttributeInstance e;
    private boolean f;
    private int g;
    private int h;
    private Vec3 i = Vec3.a(0.0D, 0.0D, 0.0D);
    private boolean j = true;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;

    private CanaryPathFinder nav = new CanaryPathFinder(this); // CanaryMod: our var

    public PathNavigate(EntityLiving entityliving, World world) {
        this.a = entityliving;
        this.b = world;
        this.e = entityliving.a(SharedMonsterAttributes.b);
    }

    public void a(boolean flag0) {
        this.l = flag0;
    }

    public boolean a() {
        return this.l;
    }

    public void b(boolean flag0) {
        this.k = flag0;
    }

    public void c(boolean flag0) {
        this.j = flag0;
    }

    public boolean c() {
        return this.k;
    }

    public void d(boolean flag0) {
        this.f = flag0;
    }

    public void a(double d0) {
        this.d = d0;
    }

    public void e(boolean flag0) {
        this.m = flag0;
    }

    public float d() {
        return (float)this.e.e();
    }

    public PathEntity a(double d0, double d1, double d2) {
        return !this.l() ? null : this.b.a(this.a, MathHelper.c(d0), (int)d1, MathHelper.c(d2), this.d(), this.j, this.k, this.l, this.m);
    }

    public boolean a(double d0, double d1, double d2, double d3) {
        PathEntity pathentity = this.a((double)MathHelper.c(d0), (double)((int)d1), (double)MathHelper.c(d2));

        return this.a(pathentity, d3);
    }

    public PathEntity a(Entity entity) {
        return !this.l() ? null : this.b.a(this.a, entity, this.d(), this.j, this.k, this.l, this.m);
    }

    public boolean a(Entity entity, double d0) {
        PathEntity pathentity = this.a(entity);

        return pathentity != null ? this.a(pathentity, d0) : false;
    }

    public boolean a(PathEntity pathentity, double d0) {
        if (pathentity == null) {
            this.c = null;
            // Canary.logInfo("path was null");
            return false;
        }
        else {
            if (!pathentity.a(this.c)) {
                this.c = pathentity;
            }

            if (this.f) {
                this.n();
            }

            if (this.c.d() == 0) {
                // Canary.logInfo("path was of length 0");
                return false;
            }
            else {
                this.d = d0;
                Vec3 vec3 = this.j();

                this.h = this.g;
                this.i.c = vec3.c;
                this.i.d = vec3.d;
                this.i.e = vec3.e;
                return true;
            }
        }
    }

    public PathEntity e() {
        return this.c;
    }

    public void f() {
        ++this.g;
        if (!this.g()) {
            if (this.l()) {
                this.i();
            }

            if (!this.g()) {
                Vec3 vec3 = this.c.a((Entity)this.a);

                if (vec3 != null) {
                    this.a.i().a(vec3.c, vec3.d, vec3.e, this.d);
                }
            }
        }
    }

    private void i() {
        Vec3 vec3 = this.j();
        int i0 = this.c.d();

        for (int i1 = this.c.e(); i1 < this.c.d(); ++i1) {
            if (this.c.a(i1).b != (int)vec3.d) {
                i0 = i1;
                break;
            }
        }

        float f0 = this.a.O * this.a.O;

        int i2;

        for (i2 = this.c.e(); i2 < i0; ++i2) {
            if (vec3.e(this.c.a(this.a, i2)) < (double)f0) {
                this.c.c(i2 + 1);
            }
        }

        i2 = MathHelper.f(this.a.O);
        int i3 = (int)this.a.P + 1;
        int i4 = i2;

        for (int i5 = i0 - 1; i5 >= this.c.e(); --i5) {
            if (this.a(vec3, this.c.a(this.a, i5), i2, i3, i4)) {
                this.c.c(i5);
                break;
            }
        }

        if (this.g - this.h > 100) {
            if (vec3.e(this.i) < 2.25D) {
                this.h();
            }

            this.h = this.g;
            this.i.c = vec3.c;
            this.i.d = vec3.d;
            this.i.e = vec3.e;
        }
    }

    public boolean g() {
        return this.c == null || this.c.b();
    }

    public void h() {
        this.c = null;
    }

    private Vec3 j() {
        return this.b.V().a(this.a.u, (double)this.k(), this.a.w);
    }

    private int k() {
        if (this.a.H() && this.m) {
            int i0 = (int)this.a.E.b;
            int i1 = this.b.a(MathHelper.c(this.a.u), i0, MathHelper.c(this.a.w));
            int i2 = 0;

            do {
                if (i1 != Block.F.cF && i1 != Block.G.cF) {
                    return i0;
                }

                ++i0;
                i1 = this.b.a(MathHelper.c(this.a.u), i0, MathHelper.c(this.a.w));
                ++i2;
            }
            while (i2 <= 16);

            return (int)this.a.E.b;
        }
        else {
            return (int)(this.a.E.b + 0.5D);
        }
    }

    private boolean l() {
        return this.a.F || this.m && this.m();
    }

    private boolean m() {
        return this.a.H() || this.a.J();
    }

    private void n() {
        if (!this.b.l(MathHelper.c(this.a.u), (int)(this.a.E.b + 0.5D), MathHelper.c(this.a.w))) {
            for (int i0 = 0; i0 < this.c.d(); ++i0) {
                PathPoint pathpoint = this.c.a(i0);

                if (this.b.l(pathpoint.a, pathpoint.b, pathpoint.c)) {
                    this.c.b(i0 - 1);
                    return;
                }
            }
        }
    }

    private boolean a(Vec3 vec3, Vec3 vec31, int i0, int i1, int i2) {
        int i3 = MathHelper.c(vec3.c);
        int i4 = MathHelper.c(vec3.e);
        double d0 = vec31.c - vec3.c;
        double d1 = vec31.e - vec3.e;
        double d2 = d0 * d0 + d1 * d1;

        if (d2 < 1.0E-8D) {
            return false;
        }
        else {
            double d3 = 1.0D / Math.sqrt(d2);

            d0 *= d3;
            d1 *= d3;
            i0 += 2;
            i2 += 2;
            if (!this.a(i3, (int)vec3.d, i4, i0, i1, i2, vec3, d0, d1)) {
                return false;
            }
            else {
                i0 -= 2;
                i2 -= 2;
                double d4 = 1.0D / Math.abs(d0);
                double d5 = 1.0D / Math.abs(d1);
                double d6 = (double)(i3 * 1) - vec3.c;
                double d7 = (double)(i4 * 1) - vec3.e;

                if (d0 >= 0.0D) {
                    ++d6;
                }

                if (d1 >= 0.0D) {
                    ++d7;
                }

                d6 /= d0;
                d7 /= d1;
                int i5 = d0 < 0.0D ? -1 : 1;
                int i6 = d1 < 0.0D ? -1 : 1;
                int i7 = MathHelper.c(vec31.c);
                int i8 = MathHelper.c(vec31.e);
                int i9 = i7 - i3;
                int i10 = i8 - i4;

                do {
                    if (i9 * i5 <= 0 && i10 * i6 <= 0) {
                        return true;
                    }

                    if (d6 < d7) {
                        d6 += d4;
                        i3 += i5;
                        i9 = i7 - i3;
                    }
                    else {
                        d7 += d5;
                        i4 += i6;
                        i10 = i8 - i4;
                    }
                }
                while (this.a(i3, (int)vec3.d, i4, i0, i1, i2, vec3, d0, d1));

                return false;
            }
        }
    }

    private boolean a(int i0, int i1, int i2, int i3, int i4, int i5, Vec3 vec3, double d0, double d1) {
        int i6 = i0 - i3 / 2;
        int i7 = i2 - i5 / 2;

        if (!this.b(i6, i1, i7, i3, i4, i5, vec3, d0, d1)) {
            return false;
        }
        else {
            for (int i8 = i6; i8 < i6 + i3; ++i8) {
                for (int i9 = i7; i9 < i7 + i5; ++i9) {
                    double d2 = (double)i8 + 0.5D - vec3.c;
                    double d3 = (double)i9 + 0.5D - vec3.e;

                    if (d2 * d0 + d3 * d1 >= 0.0D) {
                        int i10 = this.b.a(i8, i1 - 1, i9);

                        if (i10 <= 0) {
                            return false;
                        }

                        Material material = Block.s[i10].cU;

                        if (material == Material.h && !this.a.H()) {
                            return false;
                        }

                        if (material == Material.i) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean b(int i0, int i1, int i2, int i3, int i4, int i5, Vec3 vec3, double d0, double d1) {
        for (int i6 = i0; i6 < i0 + i3; ++i6) {
            for (int i7 = i1; i7 < i1 + i4; ++i7) {
                for (int i8 = i2; i8 < i2 + i5; ++i8) {
                    double d2 = (double)i6 + 0.5D - vec3.c;
                    double d3 = (double)i8 + 0.5D - vec3.e;

                    if (d2 * d0 + d3 * d1 >= 0.0D) {
                        int i9 = this.b.a(i6, i7, i8);

                        if (i9 > 0 && !Block.s[i9].b((IBlockAccess)this.b, i6, i7, i8)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    // CanaryMod
    public CanaryPathFinder getCanaryPathFinder() {
        return this.nav;
    }
}

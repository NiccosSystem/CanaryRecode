package net.minecraft.server;

import net.minecraft.server.OBlock;
import net.minecraft.server.OBlockFurnace;
import net.minecraft.server.OEntityPlayer;
import net.minecraft.server.OFurnaceRecipes;
import net.minecraft.server.OIInventory;
import net.minecraft.server.OItem;
import net.minecraft.server.OItemStack;
import net.minecraft.server.OMaterial;
import net.minecraft.server.ONBTBase;
import net.minecraft.server.ONBTTagCompound;
import net.minecraft.server.ONBTTagList;
import net.minecraft.server.OTileEntity;

public class OTileEntityFurnace extends OTileEntity implements OIInventory {

   private OItemStack[] d = new OItemStack[3];
   public int a = 0;
   public int b = 0;
   public int c = 0;


   public OTileEntityFurnace() {
      super();
   }

   public int c() {
      return this.d.length;
   }

   public OItemStack g_(int var1) {
      return this.d[var1];
   }

   public OItemStack a(int var1, int var2) {
      if(this.d[var1] != null) {
         OItemStack var3;
         if(this.d[var1].a <= var2) {
            var3 = this.d[var1];
            this.d[var1] = null;
            return var3;
         } else {
            var3 = this.d[var1].a(var2);
            if(this.d[var1].a == 0) {
               this.d[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   public OItemStack b(int var1) {
      if(this.d[var1] != null) {
         OItemStack var2 = this.d[var1];
         this.d[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void a(int var1, OItemStack var2) {
      this.d[var1] = var2;
      if(var2 != null && var2.a > this.a()) {
         var2.a = this.a();
      }

   }

   public String e() {
      return "container.furnace";
   }

   public void a(ONBTTagCompound var1) {
      super.a(var1);
      ONBTTagList var2 = var1.n("Items");
      this.d = new OItemStack[this.c()];

      for(int var3 = 0; var3 < var2.d(); ++var3) {
         ONBTTagCompound var4 = (ONBTTagCompound)var2.a(var3);
         byte var5 = var4.d("Slot");
         if(var5 >= 0 && var5 < this.d.length) {
            this.d[var5] = OItemStack.a(var4);
         }
      }

      this.a = var1.e("BurnTime");
      this.c = var1.e("CookTime");
      this.b = a(this.d[1]);
   }

   public void b(ONBTTagCompound var1) {
      super.b(var1);
      var1.a("BurnTime", (short)this.a);
      var1.a("CookTime", (short)this.c);
      ONBTTagList var2 = new ONBTTagList();

      for(int var3 = 0; var3 < this.d.length; ++var3) {
         if(this.d[var3] != null) {
            ONBTTagCompound var4 = new ONBTTagCompound();
            var4.a("Slot", (byte)var3);
            this.d[var3].b(var4);
            var2.a((ONBTBase)var4);
         }
      }

      var1.a("Items", (ONBTBase)var2);
   }

   public int a() {
      return 64;
   }

   public boolean i() {
      return this.a > 0;
   }

   public void q_() {
      boolean var1 = this.a > 0;
      boolean var2 = false;
      if(this.a > 0) {
         --this.a;
      }

      if(!this.k.F) {
         if(this.a == 0 && this.o()) {
            this.b = this.a = a(this.d[1]);
            if(this.a > 0) {
               var2 = true;
               if(this.d[1] != null) {
                  --this.d[1].a;
                  if(this.d[1].a == 0) {
                     this.d[1] = null;
                  }
               }
            }
         }

         if(this.i() && this.o()) {
            ++this.c;
            if(this.c == 200) {
               this.c = 0;
               this.n();
               var2 = true;
            }
         } else {
            this.c = 0;
         }

         if(var1 != this.a > 0) {
            var2 = true;
            OBlockFurnace.a(this.a > 0, this.k, this.l, this.m, this.n);
         }
      }

      if(var2) {
         this.G_();
      }

   }

   private boolean o() {
      if(this.d[0] == null) {
         return false;
      } else {
         OItemStack var1 = OFurnaceRecipes.a().a(this.d[0].a().bP);
         return var1 == null?false:(this.d[2] == null?true:(!this.d[2].a(var1)?false:(this.d[2].a < this.a() && this.d[2].a < this.d[2].b()?true:this.d[2].a < var1.b())));
      }
   }

   public void n() {
      if(this.o()) {
         OItemStack var1 = OFurnaceRecipes.a().a(this.d[0].a().bP);
         if(this.d[2] == null) {
            this.d[2] = var1.j();
         } else if(this.d[2].c == var1.c) {
            ++this.d[2].a;
         }

         --this.d[0].a;
         if(this.d[0].a <= 0) {
            this.d[0] = null;
         }

      }
   }

   public static int a(OItemStack var0) {
      if(var0 == null) {
         return 0;
      } else {
         int var1 = var0.a().bP;
         return var1 < 256 && OBlock.m[var1].cd == OMaterial.d?300:(var1 == OItem.C.bP?100:(var1 == OItem.l.bP?1600:(var1 == OItem.ax.bP?20000:(var1 == OBlock.y.bO?100:(var1 == OItem.bn.bP?2400:0)))));
      }
   }

   public static boolean b(OItemStack var0) {
      return a(var0) > 0;
   }

   public boolean a(OEntityPlayer var1) {
      return this.k.b(this.l, this.m, this.n) != this?false:var1.e((double)this.l + 0.5D, (double)this.m + 0.5D, (double)this.n + 0.5D) <= 64.0D;
   }

   public void f() {}

   public void g() {}
}
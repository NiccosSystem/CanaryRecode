package net.minecraft.server;

import net.minecraft.server.OAxisAlignedBB;
import net.minecraft.server.OBlock;
import net.minecraft.server.OBlockDirectional;
import net.minecraft.server.OEntityLiving;
import net.minecraft.server.OEntityPlayer;
import net.minecraft.server.OIBlockAccess;
import net.minecraft.server.OMaterial;
import net.minecraft.server.OMathHelper;
import net.minecraft.server.OWorld;

public class OBlockFenceGate extends OBlockDirectional {

   public OBlockFenceGate(int var1, int var2) {
      super(var1, var2, OMaterial.d);
   }

   public boolean c(OWorld var1, int var2, int var3, int var4) {
      return !var1.d(var2, var3 - 1, var4).a()?false:super.c(var1, var2, var3, var4);
   }

   public OAxisAlignedBB e(OWorld var1, int var2, int var3, int var4) {
      int var5 = var1.c(var2, var3, var4);
      return d(var5)?null:(var5 != 2 && var5 != 0?OAxisAlignedBB.b((double)((float)var2 + 0.375F), (double)var3, (double)var4, (double)((float)var2 + 0.625F), (double)((float)var3 + 1.5F), (double)(var4 + 1)):OAxisAlignedBB.b((double)var2, (double)var3, (double)((float)var4 + 0.375F), (double)(var2 + 1), (double)((float)var3 + 1.5F), (double)((float)var4 + 0.625F)));
   }

   public void a(OIBlockAccess var1, int var2, int var3, int var4) {
      int var5 = b(var1.c(var2, var3, var4));
      if(var5 != 2 && var5 != 0) {
         this.a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
      } else {
         this.a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
      }

   }

   public boolean a() {
      return false;
   }

   public boolean b() {
      return false;
   }

   public boolean b(OIBlockAccess var1, int var2, int var3, int var4) {
      return d(var1.c(var2, var3, var4));
   }

   public int c() {
      return 21;
   }

   public void a(OWorld var1, int var2, int var3, int var4, OEntityLiving var5) {
      int var6 = (OMathHelper.b((double)(var5.bs * 4.0F / 360.0F) + 0.5D) & 3) % 4;
      var1.c(var2, var3, var4, var6);
   }

   public boolean a(OWorld var1, int var2, int var3, int var4, OEntityPlayer var5) {
      int var6 = var1.c(var2, var3, var4);
      if(d(var6)) {
         var1.c(var2, var3, var4, var6 & -5);
      } else {
         int var7 = (OMathHelper.b((double)(var5.bs * 4.0F / 360.0F) + 0.5D) & 3) % 4;
         int var8 = b(var6);
         if(var8 == (var7 + 2) % 4) {
            var6 = var7;
         }

         var1.c(var2, var3, var4, var6 | 4);
      }

      var1.a(var5, 1003, var2, var3, var4, 0);
      return true;
   }

   public void a(OWorld var1, int var2, int var3, int var4, int var5) {
      if(!var1.F) {
         int var6 = var1.c(var2, var3, var4);
         boolean var7 = var1.x(var2, var3, var4);
         if(var7 || var5 > 0 && OBlock.m[var5].e() || var5 == 0) {
            if(var7 && !d(var6)) {
               var1.c(var2, var3, var4, var6 | 4);
               var1.a((OEntityPlayer)null, 1003, var2, var3, var4, 0);
            } else if(!var7 && d(var6)) {
               var1.c(var2, var3, var4, var6 & -5);
               var1.a((OEntityPlayer)null, 1003, var2, var3, var4, 0);
            }
         }

      }
   }

   public static boolean d(int var0) {
      return (var0 & 4) != 0;
   }
}
package net.minecraft.server;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;

public class ItemReed extends Item {

    private int a;

    public ItemReed(int i0, Block block) {
        super(i0);
        this.a = block.cF;
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        int i4 = world.a(i0, i1, i2);

        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(i0, i1, i2);

        clicked.setFaceClicked(BlockFace.fromByte((byte)i3));

        if (i4 == Block.aX.cF && (world.h(i0, i1, i2) & 7) < 1) {
            i3 = 1;
        }
        else if (i4 != Block.bz.cF && i4 != Block.ac.cF && i4 != Block.ad.cF) {
            if (i3 == 0) {
                --i1;
            }

            if (i3 == 1) {
                ++i1;
            }

            if (i3 == 2) {
                --i2;
            }

            if (i3 == 3) {
                ++i2;
            }

            if (i3 == 4) {
                --i0;
            }

            if (i3 == 5) {
                ++i0;
            }
        }

        if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
            return false;
        }
        else if (itemstack.b == 0) {
            return false;
        }
        else {
            if (world.a(this.a, i0, i1, i2, false, i3, (Entity)null, itemstack)) {
                Block block = Block.s[this.a];
                int i5 = block.a(world, i0, i1, i2, i3, f0, f1, f2, 0);

                if (world.f(i0, i1, i2, this.a, i5, 3)) {
                    // set placed
                    CanaryBlock placed = new CanaryBlock((short)this.a, (short)0, i0, i1, i2, world.getCanaryWorld());
                    // Create and Call
                    BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, placed).call();
                    if (hook.isCanceled()) {
                        return false;
                    }
                    //
                    if (world.a(i0, i1, i2) == this.a) {
                        Block.s[this.a].a(world, i0, i1, i2, (EntityLivingBase)entityplayer, itemstack);
                        Block.s[this.a].k(world, i0, i1, i2, i5);
                    }

                    world.a((double)((float)i0 + 0.5F), (double)((float)i1 + 0.5F), (double)((float)i2 + 0.5F), block.cS.b(), (block.cS.c() + 1.0F) / 2.0F, block.cS.d() * 0.8F);
                    --itemstack.b;
                }
            }

            return true;
        }
    }
}

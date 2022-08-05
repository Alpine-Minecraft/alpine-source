package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;

public class ReflectorForge
{

    public static void putLaunchBlackboard(String p_putLaunchBlackboard_0_, Object p_putLaunchBlackboard_1_)
    {
    }

    public static boolean renderFirstPersonHand(RenderGlobal p_renderFirstPersonHand_0_, float p_renderFirstPersonHand_1_, int p_renderFirstPersonHand_2_)
    {
        return false;
    }

    public static boolean blockHasTileEntity(IBlockState p_blockHasTileEntity_0_)
    {
        Block block = p_blockHasTileEntity_0_.getBlock();
        return block.hasTileEntity();
    }
}

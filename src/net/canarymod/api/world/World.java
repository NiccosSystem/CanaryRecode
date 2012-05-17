package net.canarymod.api.world;

import java.util.List;

import net.canarymod.api.entity.IPlayer;
import net.minecraft.server.OEntityPlayerMP;
import net.minecraft.server.OWorld;
import net.minecraft.server.OWorldServer;

public class World implements IWorld {
   
    private Dimension[] dimensions;
    private OWorldServer[] oDimensions;
    public long[][] nanoTicks;
    private boolean enabled;
    
    /**
     * The world name
     */
    private String name;
    
    public World(String name, OWorld[] dimensions) {
        this.name = name;
        this.dimensions = new Dimension[] {new Dimension(dimensions[0], this), new Dimension(dimensions[1],this), new Dimension(dimensions[2],this)};
        nanoTicks = new long[dimensions.length][100]; //TODO: Find out what the hell this does
        oDimensions = (OWorldServer[]) dimensions;
    }
    
    public OWorldServer[] getWorldArray() {
        return this.oDimensions;
    }
    /**
     * Return the name of this World (WorldContainer)
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the NORMAL dimension for this world container
     * @return
     */
    @Override
    public Dimension getNormal() {
        return this.dimensions[0];
    }
    
    /**
     * Get the NETHER dimension for this world container
     * @return
     */
    @Override
    public Dimension getNether() {
        return this.dimensions[1];
    }
    
    /**
     * Get the END dimension for this world container
     * @return
     */
    @Override
    public Dimension getEnd() {
        return this.dimensions[2];
    }
    
    @Override
    public IDimension getDimension(DimensionType dimension) {
    	return this.dimensions[dimension.getIntValue()];
    }
    
    @Override
    public void setNanoTick(DimensionType dimension, int tickIndex, long tick) {
        nanoTicks[dimension.getIntValue()][tickIndex] = tick;
    }

    @Override
	public void setEnabled(boolean enabled) {
    	this.enabled = enabled; // TODO: implement
    }
	
    @Override
	public boolean isEnabled() {
    	return this.enabled;
    }
	
    @Override
	public boolean canEnterWorld(IPlayer player) {
    	return player.hasPermission("world.traveling."+name+".enter") && isEnabled();
    }
	
    @Override
	public boolean canLeaveWorld(IPlayer player) {
    	return player.hasPermission("world.traveling."+name+".leave");
    }

	@Override
	public IPlayer matchPlayer(String player) {
	    IPlayer lastPlayer = null;

        name = name.toLowerCase();
        for(OWorldServer world : oDimensions) {
            for (OEntityPlayerMP player : (List<OEntityPlayerMP>) world.getServer().get) {
                String playerName = player.v;

                if (playerName.toLowerCase().equals(name)) {
                    // Perfect match found
                    lastPlayer = player.getPlayer();
                    break;
                }
                if (playerName.toLowerCase().indexOf(name.toLowerCase()) != -1) {
                    // Partial match
                    if (lastPlayer != null) {
                        // Found multiple
                        return null;
                    }
                    lastPlayer = player.getPlayer();
                }
            }
        }
        

        return lastPlayer;
	}

	@Override
	public IPlayer getPlayer(String player) {
		// TODO Auto-generated method stub
		return null;
	}
}
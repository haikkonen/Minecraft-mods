package net.minecraft.src.nbxlite.chunkproviders;

import java.util.List;
import net.minecraft.src.*;
import net.minecraft.src.nbxlite.BoundChunk;
import net.minecraft.src.nbxlite.indev.*;

public class ChunkProviderBaseFinite implements IChunkProvider{
    protected World worldObj;
    protected long seed;

    public ChunkProviderBaseFinite(World world, long l){
        worldObj = world;
        seed = l;
    }

    protected int IndexFinite(int x, int y, int z){
        return x+(y*mod_noBiomesX.IndevWidthZ+z)*mod_noBiomesX.IndevWidthX;
    }

    protected byte[] getChunkArray(int x1, int z1){
        byte[] result = new byte[32768];
        for (int x=0; x<16; x++){
            for (int z=0; z<16; z++){
                for (int y=0; y<Math.min(mod_noBiomesX.IndevHeight, 128); y++){
                    byte block = mod_noBiomesX.IndevWorld[IndexFinite(x+(x1*16), y, z+(z1*16))];
                    if (block==0){
                        continue;
                    }
                    result[x << 11 | z << 7 | y]=block;
                }
            }
        }
        return result;
    }

    protected void fixDeepMaps(Chunk chunk, int x1, int z1){
        for (int x=0; x<16; x++){
            for (int z=0; z<16; z++){
                for (int y=128; y<mod_noBiomesX.IndevHeight; y++){
                    byte block = mod_noBiomesX.IndevWorld[IndexFinite(x+(x1*16), y, z+(z1*16))];
                    if (block==0){
                        continue;
                    }
                    ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];
                    if (extendedblockstorage == null)
                    {
                        extendedblockstorage = chunk.getBlockStorageArray()[y >> 4] = new ExtendedBlockStorage((y >> 4) << 4);
                    }
                    extendedblockstorage.setExtBlockID(x, y & 0xf, z, block);
                }
            }
        }
    }

    public Chunk loadChunk(int i, int j){
        return provideChunk(i, j);
    }

    public void generateFiniteLevel(){}

    public Chunk provideChunk(int i, int j){
        boolean tall = mod_noBiomesX.IndevHeight>128;
        boolean tall2 = mod_noBiomesX.IndevHeight>160;
        boolean bounds = i>=0 && i<mod_noBiomesX.IndevWidthX/16 && j>=0 && j<mod_noBiomesX.IndevWidthZ/16;
        Chunk chunk;
        if (bounds){
            if (mod_noBiomesX.IndevWorld==null){
                generateFiniteLevel();
            }
            chunk = new Chunk(worldObj, getChunkArray(i, j), i, j);
            if (tall){
                fixDeepMaps(chunk, i, j);
            }
        }else{
            chunk = new BoundChunk(worldObj, i, j);
        }
        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int i, int j){
        return true;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j){
        for (int x = i * 16; x < (i + 1) * 16; x++){
            for (int y = 0; y < mod_noBiomesX.IndevHeight; y++){
                for (int z = j * 16; z < (j + 1) * 16; z++){
                    if (Block.lightValue[worldObj.getBlockId(x, y, z)]>0){
                        worldObj.updateAllLightTypes(x, y, z);
                    }
                }
            }
        }
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate){
        return true;
    }

    public boolean unload100OldestChunks(){
        return false;
    }

    public boolean canSave(){
        return true;
    }

    public String makeString(){
        return "RandomLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i, int j, int k){
        return null;
    }

    public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k){
        return null;
    }
}

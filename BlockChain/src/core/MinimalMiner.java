package core;

import core.BlockChain.Blocks.StdBlock;
import core.BlockChain.Blocks.Hashable;

public class MinimalMiner
{
    public static StdBlock mine(Hashable data, byte[] prevHash)
    {
        StdBlock result = new StdBlock(data, prevHash);
        byte[] hash = result.calculateHash();
        for(long i = 0; hash[0] != 0x77; ++i)
        {
            result.setNonce(i);
            hash = result.calculateHash();
        }
        result.setHash(hash);
        return result;
    }
}
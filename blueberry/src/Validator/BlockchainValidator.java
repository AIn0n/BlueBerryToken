package Validator;

import core.BlockChain.BlockChain;
import core.BlockChain.Blocks.Block;

public class BlockchainValidator {
    public static boolean BlockchainValidateHashes(BlockChain bc)
    {
        for(Block block: bc) if(!block.isHashValid()) return false;
        return true;
    }
}

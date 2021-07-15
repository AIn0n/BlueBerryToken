package Validator;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;

public class BlockchainValidator {
    public static boolean BlockchainValidateHashes(BlockChain bc)
    {
        for(Block block: bc) if(!block.isHashValid()) return false;
        return true;
    }
}

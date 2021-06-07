package Miner;

import BlockChain.Block;
import BlockChain.BlockChain;
import BlockChain.Datable;
import BlockChain.StrData;
import HashingUtility.HashingUtility;

public class Miner {
    /*
    private final PublicKey minerPk;

    public Miner(PublicKey pk)
    {
        this.minerPk = pk;
    }

     */

    public Block mine(Datable data, byte[] prevHash, long prevIdx)
    {
        Block result = new Block(data, prevHash, prevIdx);
        int i = 0;
        while(result.getHash()[0] != 0x12)
        {
            result.setNonce(i++);
            result.calculateHash();
            System.out.println("hash: " + HashingUtility.byteListToString(result.getHash()));   //debug
        }
        return result;
    }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);

        Miner miner = new Miner();
        Block blockToDig = miner.mine(new StrData("bar"), bc.getLastHash(), bc.getLastIndex());
        bc.add(blockToDig);

        for (Block block : bc) { block.printBlock(); }
    }
}

package Miner;

import BlockChain.Block;
import BlockChain.BlockChain;
import BlockChain.Datable;
import BlockChain.StrData;
import HashingUtility.HashingUtility;

import java.security.*;

public class Miner {

    private final PublicKey miner;

    public Miner(PublicKey pk){this.miner = pk;}

    public Block mine(Datable data, Block prevBlock)
    {
        Block result = new Block(data, prevBlock, this.miner);
        int i = 0;
        do
        {
            result.setNonce(i++);
            result.calculateHash();
            System.out.println("hash: " + HashingUtility.byteListToString(result.getHash()));   //debug
        }
        while(result.getHash()[0] != 0x12);

        return result;
    }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);

    //key generation
        try
        {
            KeyPairGenerator KeyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            KeyGen.initialize(1024);
            KeyPair pair = KeyGen.genKeyPair();
            Miner miner = new Miner(pair.getPublic());
            Block blockToDig = miner.mine(new StrData("bar"), bc.last());
            bc.add(blockToDig);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {e.printStackTrace();}

        for (Block block : bc) { block.printBlock(); }
    }
}

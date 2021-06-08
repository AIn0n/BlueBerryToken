package Miner;

import BlockChain.*;
import BlockChain.Blocks.Block;
import BlockChain.Blocks.StdBlock;
import BlockChain.Blocks.Datable;
import HashingUtility.HashingUtility;

import java.security.*;

public class Miner {

    private final PublicKey miner;

    public Miner(PublicKey pk) { this.miner = pk; }

    public StdBlock mine(Datable data, Block prevBlock)
    {
        StdBlock result = new StdBlock(data, prevBlock, this.miner);
        int i = 0;
        do
        {
            result.setNonce(i++);
            result.calculateHash();
            System.out.println("hash: " + HashingUtility.byteListToString(result.getHash()));   //debug
        }
        while(result.getHash()[0] != 0x77);

        return result;
    }

    public static void main(String[] args)
    {
        StrData initData = new StrData("foo");
        BlockChain bc = new BlockChain(initData);
        try //key generation
        {
            KeyPairGenerator KeyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            KeyGen.initialize(1024);
            KeyPair pair = KeyGen.genKeyPair();
            Miner miner = new Miner(pair.getPublic());
            StdBlock blockToDig = miner.mine(new StrData("bar"), bc.last());
            bc.add(blockToDig);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {e.printStackTrace();}

        for (Block block : bc) { System.out.println(block); }
    }
}
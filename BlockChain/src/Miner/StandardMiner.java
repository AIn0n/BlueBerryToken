package Miner;

import BlockChain.*;
import BlockChain.Blocks.Block;
import BlockChain.Blocks.StdBlock;
import BlockChain.Blocks.Datable;
import HashingUtility.HashUtil;

import java.security.*;

public class StandardMiner {

    private final PublicKey miner;

    public StandardMiner(PublicKey pk) { this.miner = pk; }

    public StdBlock mine(Datable data, byte[] prevHash)
    {
        StdBlock result = new StdBlock(data, prevHash, this.miner);
        byte[] hash = result.calculateHash();
        for(long i = 0; hash[0] != 0x77; ++i)
        {
            result.setNonce(i);
            hash = result.calculateHash();
            System.out.println("hash: " + HashUtil.byteListToString(hash));   //debug
        }

        result.setHash(hash);
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
            StandardMiner miner = new StandardMiner(pair.getPublic());
            StdBlock blockToDig = miner.mine(new StrData("bar"), bc.last().getHash());
            bc.add(blockToDig);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {e.printStackTrace();}

        for (Block block : bc) { System.out.println(block); }
    }
}
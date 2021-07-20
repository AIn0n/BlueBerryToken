package InitBlueBerryToken;

import Transaction.Transactions;
import Transaction.Tx;
import Transaction.TxIn;
import Transaction.TxOut;
import core.BlockChain.BlockChain;
import core.MinimalMiner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BlueBerryInit {
    public static ArrayList<KeyPair> generateKeyPairs(int n) throws NoSuchProviderException, NoSuchAlgorithmException
    {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
        kpg.initialize(512);
        ArrayList<KeyPair> keys = new ArrayList<>();
        for(int i = 0; i < n; ++i)
            keys.add(kpg.generateKeyPair());

        return keys;
    }

    public static BlockChain generateBlockChain(KeyPair owner, ArrayList<KeyPair> keys, long startBalance)
    {
        Random rng = new Random();
        TxOut firstTx = new TxOut(owner.getPublic(), startBalance * keys.size(), rng.nextLong());
        BlockChain bc = new BlockChain(new Transactions(new Tx(
                new HashSet<>(),
                new HashSet<TxOut>() {{ add(firstTx);}})));

        TxIn fromOwnerIn = new TxIn(firstTx.getHash(), firstTx.getAmount());
        fromOwnerIn.sign(owner.getPrivate());
        HashSet<TxOut> toNewcomersOuts = new HashSet<>();
        for(KeyPair key: keys)
            toNewcomersOuts.add(new TxOut(key.getPublic(), startBalance, rng.nextLong()));

        bc.add(MinimalMiner.mine(new Transactions(new Tx(
                new HashSet<TxIn>() {{add(fromOwnerIn);}}, toNewcomersOuts)),bc.last().getHash()));

        return bc;
    }
}

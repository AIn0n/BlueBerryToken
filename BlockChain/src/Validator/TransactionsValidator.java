package Validator;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Miner.StandardMiner;
import Transaction.Transactions;
import Transaction.Tx;
import Transaction.TxIn;
import Transaction.TxOut;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

public class TransactionsValidator
{
    public static boolean areBalancesValid(Iterable<Tx> txs)
    {
        for (Tx tx : txs) if(!tx.isBalanceValid()) return false;
        return true;
    }

    public static TxOut findOutByHash(Iterable<TxOut> outs, byte[] hash) throws NoSuchElementException
    {
        for(TxOut out: outs)
            if(Arrays.equals(out.getHash(), hash)) return out;
        throw new NoSuchElementException();
    }

    public static boolean areSignaturesAndPrevOutsValid(Iterable<Tx> txs)
    {
        HashSet<TxOut> outs = getAllOuts(txs);
        HashSet<TxIn> ins = getAllIns(txs);
        for(TxIn in: ins)
        {
            try
            {
                if(!in.verifySignature(findOutByHash(outs, in.getPrevOutHash()).getRecipient())) return false;
            }
            catch (NoSuchElementException e) { return false; }
        }
        return true;
    }

    public static HashSet<TxOut> getAllOuts(Iterable<Tx> txs)
    {
        HashSet<TxOut> outs = new HashSet<>();
        for(Tx tx: txs)
        {
            outs.addAll((Collection<? extends TxOut>) tx.getOuts());
        }
        return outs;
    }

    public static HashSet<TxIn> getAllIns(Iterable<Tx> txs)
    {
        HashSet<TxIn> ins = new HashSet<>();
        for(Tx tx: txs)
        {
            ins.addAll((Collection<? extends TxIn>) tx.getIns());
        }
        return ins;
    }

    public static Iterable<Tx> getAllTransactions(BlockChain bc)
    {
        HashSet<Tx> txs = new HashSet<>();
        for(Block block: bc)
        {
            Transactions transactions = (Transactions) block.getData();
            txs.addAll(transactions.getTxHashSet());
        }
        return txs;
    }

    public static void main(String[]a)
    {
        try
        {
            //keys for potential users
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
            kpg.initialize(1024);
            KeyPair owner = kpg.generateKeyPair();
            KeyPair kp1 = kpg.generateKeyPair();
            KeyPair kp2 = kpg.generateKeyPair();
            KeyPair kp3 = kpg.generateKeyPair();
            //test out to any of new users
            HashSet<TxOut> test = new HashSet<>();
            test.add(new TxOut(kp1.getPublic(), 1000, 0));
            test.add(new TxOut(kp2.getPublic(), 1000, 1));
            TxOut txToFabricate = new TxOut(kp3.getPublic(), 1000, 2);
            test.add(txToFabricate);
            //adding two first, genesis blocks to blockchain - one with creator's out and one with giving it to newcomers
            TxOut initOut = new TxOut(owner.getPublic(), 3000, 4);
            BlockChain bc = new BlockChain(new Transactions(new Tx(new HashSet<>(), new HashSet<TxOut>() {{add(initOut);}})));
            TxIn initIn = new TxIn(initOut.getHash(), initOut.getAmount());
            initIn.sign(owner.getPrivate());
            StandardMiner miner = new StandardMiner(owner.getPublic());
            bc.add(miner.mine(new Transactions(new Tx(new HashSet<TxIn>() {{add(initIn);}}, test)), bc.last().getHash()));
            TxIn invalidIn = new TxIn(txToFabricate.getHash(), txToFabricate.getAmount());
            invalidIn.sign(owner.getPrivate());
            bc.add(miner.mine(new Transactions(new Tx(new HashSet<TxIn>() {{add(invalidIn);}}, new HashSet<TxOut>()
            {{add(new TxOut(owner.getPublic(), 1000, 5));}})), bc.last().getHash()));
            for(Tx tx: getAllTransactions(bc)) { System.out.println(tx); }
            System.out.println(TransactionsValidator.areSignaturesAndPrevOutsValid(getAllTransactions(bc)));

        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace(); }
    }
}

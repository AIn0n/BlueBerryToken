package Validator;

import core.BlockChain.BlockChain;
import core.BlockChain.Blocks.Block;
import Transaction.*;

import java.util.*;

public class TransactionsValidator
{
    public static boolean validate(BlockChain bc)
    {
        Iterable<Tx> txs = getAllTransactions(bc);
        ArrayList<TxOut> outs = getAllOuts(txs);
        ArrayList<TxIn> ins = getAllIns(txs);
        return areSignaturesAndOutsValid(ins, outs) && areInnerBalancesValid(txs);
    }

    public static boolean areInnerBalancesValid(Iterable<Tx> txs)
    {
        int invalidBalances = 0;
        for (Tx tx : txs) if(!tx.isBalanceValid()) invalidBalances++;
        return invalidBalances == 1;
    }

    public static boolean areSignaturesAndOutsValid(ArrayList<TxIn> ins, ArrayList<TxOut> outs)
    {
        ArrayList<byte[]> occurredOutHashes = new ArrayList<>();
        for(TxIn in: ins)
        {
            try
            {
                byte[] prevOutHash = in.getPrevOutHash();
                TxOut prevOut = findOutByHash(outs, prevOutHash);
                if(!in.verifySignature(prevOut.getRecipient())  ||
                        occurredOutHashes.contains(prevOutHash) ||
                        prevOut.getAmount() != in.getAmount()) return false;
                occurredOutHashes.add(prevOutHash);
            }
            catch (NoSuchElementException e) { return false; }
        }
        return true;
    }

    public static Tx findInitTransaction(BlockChain bc)
    {
        Transactions initTransactions = (Transactions) bc.first().getData();
        return new ArrayList<>(initTransactions.getTxHashSet()).get(0);
    }

    public static TxOut findOutByHash(Iterable<TxOut> outs, byte[] hash) throws NoSuchElementException
    {
        for(TxOut out: outs)
            if(Arrays.equals(out.getHash(), hash)) return out;
        throw new NoSuchElementException();
    }

    public static HashSet<TxOut> getUnspentOuts(Iterable<Tx> txs)
    {
        ArrayList<TxOut> outs = getAllOuts(txs);
        ArrayList<TxIn> ins = getAllIns(txs);
        HashSet<TxOut> unspentOuts = new HashSet<>(outs);
        for(TxIn in: ins)
            unspentOuts.remove(findOutByHash(outs, in.getPrevOutHash()));

        return unspentOuts;
    }

    public static ArrayList<TxOut> getAllOuts(Iterable<Tx> txs)
    {
        ArrayList<TxOut> outs = new ArrayList<>();
        for(Tx tx: txs)
            outs.addAll(tx.getOuts());

        return outs;
    }

    public static ArrayList<TxIn> getAllIns(Iterable<Tx> txs)
    {
        ArrayList<TxIn> ins = new ArrayList<>();
        for(Tx tx: txs)
            ins.addAll(tx.getIns());

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
}

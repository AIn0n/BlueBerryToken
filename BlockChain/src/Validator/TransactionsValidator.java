package Validator;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.*;
import java.util.*;

public class TransactionsValidator
{
    public static boolean areBalancesValid(Iterable<Tx> txs)
    {
        int invalidBalances = 0;
        for (Tx tx : txs) if(!tx.isBalanceValid()) invalidBalances++;
        return invalidBalances == 1;
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
                if(!in.verifySignature(findOutByHash(outs, in.getPrevOutHash()).getRecipient()))
                    return false;
            }
            catch (NoSuchElementException e) { return false; }
        }
        return true;
    }

    public static HashSet<TxOut> getUnspentOuts(Iterable<Tx> txs)
    {
        HashSet<TxOut> outs = getAllOuts(txs);
        HashSet<TxOut> unspentOuts = new HashSet<>(outs);
        HashSet<TxIn> ins = getAllIns(txs);
        for(TxIn in: ins)
            unspentOuts.remove(findOutByHash(outs, in.getPrevOutHash()));

        return unspentOuts;
    }

    public static HashSet<TxOut> getAllOuts(Iterable<Tx> txs)
    {
        HashSet<TxOut> outs = new HashSet<>();
        for(Tx tx: txs)
            outs.addAll((Collection<? extends TxOut>) tx.getOuts());

        return outs;
    }

    public static HashSet<TxIn> getAllIns(Iterable<Tx> txs)
    {
        HashSet<TxIn> ins = new HashSet<>();
        for(Tx tx: txs)
            ins.addAll((Collection<? extends TxIn>) tx.getIns());

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

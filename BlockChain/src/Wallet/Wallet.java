package Wallet;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.Transactions;

import java.security.KeyPair;

public class Wallet
{
    private WalletListener listener;
    private BlockChain blockChain;
    private long Balance;
    private KeyPair keys;

    public Wallet(WalletListener listener, KeyPair keyPair)
    {
        this.listener = listener;
        this.keys = keyPair;
    }

    public void updateBalance()
    {
    }

    public static void main(String[] args)
    {

    }
}
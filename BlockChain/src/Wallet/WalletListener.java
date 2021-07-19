package Wallet;

import core.BlockChain.BlockChain;
import Transaction.Tx;

public interface WalletListener
{
    BlockChain getBlockChain();
    void sendTx(Tx tx);
    byte[] getLastBlockHash();
}

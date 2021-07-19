package Wallet;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.Tx;

public interface WalletListener
{
    BlockChain getBlockChain();
    void sendTx(Tx tx);
    byte[] getLastBlockHash();
}

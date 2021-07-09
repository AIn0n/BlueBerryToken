package Wallet;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.Tx;

public interface WalletListener
{
    Block getBlock();
    BlockChain getBlockChain();
    void sendTx(Tx tx);
}

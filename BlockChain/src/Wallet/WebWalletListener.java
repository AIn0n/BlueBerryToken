package Wallet;

import BlockChain.BlockChain;
import BlockChain.Blocks.Block;
import Transaction.Tx;

public class WebWalletListener implements WalletListener {

    @Override
    public Block getBlock() {
        return null;
    }

    @Override
    public BlockChain getBlockChain() {
        return null;
    }

    @Override
    public void sendTx(Tx tx) {

    }
}

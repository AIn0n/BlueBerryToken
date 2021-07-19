package Wallet;

import BlockChain.BlockChain;
import Transaction.Tx;

public class LocalWalletListener implements WalletListener
{
    private final BlockChain blockChain;

    public LocalWalletListener(BlockChain bc) { this.blockChain = bc; }

    @Override
    public BlockChain getBlockChain() {
        return blockChain;
    }

    @Override
    public void sendTx(Tx tx) {
    }

    @Override
    public byte[] getLastBlockHash() {
        return blockChain.last().getHash();
    }
}

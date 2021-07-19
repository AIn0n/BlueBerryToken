package Wallet;

import core.BlockChain.BlockChain;
import Transaction.Tx;

public class WebWalletListener implements WalletListener {

    @Override
    public BlockChain getBlockChain() {
        return null;
    }

    @Override
    public void sendTx(Tx tx) {

    }

    @Override
    public byte[] getLastBlockHash() {
        return new byte[0];
    }
}

package Wallet;

import java.io.*;

public class WalletFileHandler {
    static public Wallet readWalletFromFile(String filename) throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Wallet wallet = (Wallet) ois.readObject();
        ois.close();
        return wallet;
    }
    static public void saveWalletInFile(Wallet wallet, String filename) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(wallet);
        oos.close();
    }
}

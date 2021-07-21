import InitBlueBerryToken.BlueBerryInit;
import Validator.TransactionsValidator;
import Wallet.Wallet;
import Wallet.LocalWalletListener;
import core.BlockChain.BlockChain;

import java.security.*;
import java.util.*;

public class ShowCaseMain
{
    private ArrayList<KeyPair> keys;
    private final ArrayList<Wallet> wallets = new ArrayList<>();
    private BlockChain bc;
    private LocalWalletListener listener;
    private final Scanner in = new Scanner(System.in);
    private final Base64.Encoder encoder = Base64.getEncoder();

    public BlockChain getBc() { return bc; }

    public static void main(String[]a)
    {
        System.out.println("Please insert number of public/secret key pairs to generate");
        ShowCaseMain cls = new ShowCaseMain();
        try
        {
            cls.generateKeysAndBlockchain();
            cls.printKeysAndWallets();
            cls.initWallets();
            while(cls.getAction())
            {
                if(!TransactionsValidator.validate(cls.getBc()))
                {
                    System.out.println("invalid transaction!!");
                    break;
                }
            }

        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace(); }
    }

    public void initWallets()
    {
        for(KeyPair kp: keys)
            wallets.add(new Wallet(listener, kp));
    }

    public void generateKeysAndBlockchain() throws NoSuchProviderException, NoSuchAlgorithmException {
        keys = BlueBerryInit.generateKeyPairs(in.nextInt() + 1);
        KeyPair owner = keys.remove(0);
        System.out.println("keys generated");
        bc = BlueBerryInit.generateBlockChain(owner, keys, 1000);
        listener = new LocalWalletListener(bc);
        System.out.println("Blockchain generated");
    }

    public void printKeysAndWallets() {
        for (int i = 0; i < keys.size(); i++) {
            System.out.print("Wallet index -> " + i + " keys:\t");
            printKeyPair(keys.get(i));
        }
    }

    public void makeTransaction() throws NoSuchElementException
    {
        System.out.print("insert sender wallet index ->");
        Wallet sender = wallets.get(in.nextInt());
        System.out.print("\ninsert public, receiver key ->");
        Wallet receiver = wallets.get(in.nextInt());
        System.out.print("\ninsert amount -> ");
        sender.sendTokens(receiver.getKeys().getPublic(), in.nextLong());
    }

    public void showBalance() throws NoSuchElementException
    {
        System.out.print("insert wallet index ->");
        Wallet wallet = wallets.get(in.nextInt());
        wallet.updateBalance();
        System.out.println(wallet.getBalance());
    }

    public boolean getAction()
    {
        System.out.println("press: (e) - exit, (t) - make transaction, (b) - show balance, (p) - print last block");
        String action = in.next();
        switch (action) {
            case "e":
                System.out.println("bye!");
                return false;
            case "t":
                makeTransaction();
                break;
            case "b":
                showBalance();
                break;
        }
        return true;
    }

    public String privateKeyToShortString(PrivateKey sk)
    {
        return encoder.encodeToString(sk.getEncoded()).substring(255, 265);
    }

    public String publicKeyToShortString(PublicKey pk)
    {
        return encoder.encodeToString(pk.getEncoded()).substring(255, 265);
    }

    public void printKeyPair(KeyPair kp)
    {
        System.out.print("pk: " + privateKeyToShortString(kp.getPrivate()) + '\t');
        System.out.println("sk: " + publicKeyToShortString(kp.getPublic()));
    }
}

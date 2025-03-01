package edu.grinnell.csc207.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {
    
    private int number;
    
    private int amount;
    
    private Hash prevHash;
    
    private long nonce;
    
    private Hash hash;

    /**
     * 
     * @param num the block's number
     * @param amount data contained in block
     * @param prevHash the previous hash (number-1)
     * @throws NoSuchAlgorithmException
     */
    Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.number = num;
        this.amount = amount;
        if (prevHash.isValid()) {
            this.prevHash = prevHash;
        } else {
            this.prevHash = null;
        }
        
        this.nonce = 0;

        String msg = "";

        if (prevHash.isValid()) {
            msg += number + amount + prevHash.toString() + nonce;
        } else {
            msg += number + amount + nonce;
        }

        hash = new Hash(calculateHash(msg));


        while (!(hash.isValid())) {
            nonce++;
            msg = "";

            if (prevHash.isValid()) {
                msg += number + amount + prevHash.toString() + nonce;
            } else {
                msg += number + amount + nonce;
            }
        
            hash = new Hash(calculateHash(msg));
        }
    }

    /**
     * 
     * @param num the block's number
     * @param amount data contained in block
     * @param prevHash the previous hash (number-1)
     * @param nonce the value of nonce 
     * @throws NoSuchAlgorithmException
     */
    Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.number = num;
        this.amount = amount;
        String msg = "";
        this.nonce = nonce;

        if (prevHash.isValid()) {
            this.prevHash = prevHash;
            msg += number + amount + prevHash.toString() + nonce;
        } else {
            this.prevHash = null;
            msg += number + amount + nonce;
        }
        hash = new Hash(calculateHash(msg));
    }

    /**
     * 
     * @param msg String of data points "number, amount, (prevHash), nonce" in order.
     * @return a byte array representation of a possible hash.
     * @throws NoSuchAlgorithmException
     */
    public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(msg.getBytes());
        byte[] hash = md.digest();
        return hash;
    }

    /**
     * 
     * @return the number of this block.
     */
    public int getNum() {
        return number;
    }

    /**
     * 
     * @return the amount transferred that is recorded in this block.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 
     * @return the nonce of this block.
     */
    public long getNonce() {
        return nonce;
    }

    /**
     * 
     * @return the hash of the previous block in the blockchain.
     */
    public Hash getPrevHash() {
        return prevHash;
    }

    /**
     * 
     * @return the hash of this block.
     */
    public Hash getHash() {
        return hash;
    }

    /**
     * @return a string representation of the block.
     */
    public String toString() {
        return "Block " + this.getNum() + 
               " (Amount: " + this.getAmount() + 
               ", Nonce: " + this.getNonce() +
               ", prevHash: " + this.getPrevHash() +
               ", hash: " + this.getHash() + ")";
    }

}

package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {

    private static class Node {
        private Block value;
        private Node next;
        
        public Node (Block val, Node newNode) {
            value = val;
            next = newNode;
        }
    }

    private Node first;

    private Node last;

    /**
     * 
     * @param initial creates a BlockChain that starts with the given initial amount.
     * @throws NoSuchAlgorithmException 
     */
    BlockChain(int initial) throws NoSuchAlgorithmException {
        first = new Node(mine(initial), null);
        last = first;
    }

    /**
     * 
     * @param amount creates a Block that starts with the given initial amount.
     * @return a valid block to be added to the BlockChain.
     * @throws NoSuchAlgorithmException 
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        if (last == null) {
            return new Block(0, amount, null);
        }
        int num = last.value.getNum() + 1;
        return new Block(num, amount, null);
    }

    /**
     * 
     * @return the size of the blockchain.
     */
    public int getSize() {
        return last.value.getNum() + 1;
    }

    /**
     * 
     * @param blk adds this block to the list.
     * @throws IllegalArgumentException
     */
    public void append(Block blk) {
        if (blk.getHash().isValid()) {
            last.next = new Node(blk, null);
            last = last.next;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 
     * @return true. unless the chain only contains a single block then does nothing and false.
     */
    public boolean removeLast() {
        if (this.getSize() <= 1) {
            return false;
        }
        Node cur = first;
        for (int i = 0; i < this.getSize() - 1; i++) {
            cur = cur.next;
        }
        last = cur;
        last.next = null;
        return true;
    }

    /**
     * 
     * @return the hash of the last block in the chain.
     */ 
    public Hash getHash() {
        return last.value.getHash();
    }

    /**
     * 
     * @return true if all blocks are valid
     */
    public boolean isValidBlockChain() {
        boolean isValid = true;
        Node cur = first;
        int amount = 0;
        for (int i = 0; i < this.getSize(); i++) {
            if (!(cur.value.getHash().isValid())) {
                isValid = false;
            }
            amount += cur.value.getAmount();
            if (amount < 0) {
                isValid = false;
            }
            cur = cur.next;
        }
        return isValid;
    }

    /**
     * prints Anna's and Bob's respective balances
     */
    public void printBalances() {
        Node cur = first;
        int initial = cur.value.getAmount();
        int amount = initial;
        cur = cur.next;
        for (int i = 1; i < this.getSize(); i++) {
           amount += cur.value.getAmount();
           cur = cur.next;
        }
        System.out.println("Alice: " + amount + ", Bob: " + (initial - amount));
    }

    /**
     * @return a string representation of the BlockChain
     */
    public String toString() {
        Node cur = first;
        String ret = "";
        for (int i = 0; i < this.getSize(); i++) {
            ret += cur.value.toString() + '\n';
            cur = cur.next;
        }
        return ret;
    }
    
}

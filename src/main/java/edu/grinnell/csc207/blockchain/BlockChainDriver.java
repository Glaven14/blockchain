package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {
   
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException 
     * @throws NumberFormatException 
     */
    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        if (args.length != 1) {
            System.out.println("Usage: BlockChainDriver <initial value>");
            System.exit(1);
        }

        BlockChain blockChain = new BlockChain(Integer.parseInt(args[0]));

        boolean loop = true;
        Scanner input = new Scanner(System.in);
        Block newBlock = null;

        while (loop) {
            System.out.println(blockChain.toString());
            System.out.print("Command? ");

            String in = input.next();


            switch (in) {

                case "mine":
                    System.out.print("Amount Transfered? ");
                    if (input.hasNextInt()) {
                        int amount = input.nextInt();
                        newBlock = blockChain.mine(amount);
                        long nonce = newBlock.getNonce();
                        System.out.println("amount = " + amount + ", nonce = " + nonce);
                    } else {
                        System.out.println("Not a valid amount.");
                    }
                    break;

                case "append":
                    System.out.print("Amount Transfered? ");
                    if (input.hasNextInt()) {
                        int amount = input.nextInt();
                        System.out.print("Nonce? ");
                        if (input.hasNextInt()) {
                            long nonce = input.nextInt();
                            if (newBlock != null 
                                && nonce == newBlock.getNonce() 
                                && amount == newBlock.getAmount()) {
                                blockChain.append(newBlock);
                            } else {
                                System.out.println("Please mine a block first.");
                            }
                        } else {
                            System.out.println("Not a valid amount.");
                        }
                    } else {
                        System.out.println("Not a valid amount.");
                    }
                    break;

                case "remove":
                    Boolean done = blockChain.removeLast();
                    if (!done) {
                        System.out.println("Could not remove last block.");
                    } 
                    break;

                case "check":
                    if (blockChain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is invalid!");
                    }
                    break;

                case "report":
                    blockChain.printBalances();
                    break;

                case "help": 
                    System.out.println("Valid commands:\r\n" 
                                        + "    mine: discovers the nonce for a "
                                        + "given transaction\r\n" 
                                        + "    append: appends a new block onto the "
                                        + "end of the chain\r\n" 
                                        + "    remove: removes the last block from "
                                        + "the end of the chain\r\n"
                                        + "    check: checks that the block chain is valid\r\n"
                                        + "    report: reports the balances of Alice and Bob\r\n"
                                        + "    help: prints this list of commands\r\n" 
                                        + "    quit: quits the program");
                    break;

                case "quit":
                    input.close();
                    loop = false;
                    break;

                default:
                    System.out.println("Please enter a valid command.");

                    System.out.println("Valid commands:\r\n" 
                                        + "    mine: discovers the nonce for a "
                                        + "given transaction\r\n" 
                                        + "    append: appends a new block onto the "
                                        + "end of the chain\r\n" 
                                        + "    remove: removes the last block from "
                                        + "the end of the chain\r\n"
                                        + "    check: checks that the block chain is valid\r\n"
                                        + "    report: reports the balances of Alice and Bob\r\n"
                                        + "    help: prints this list of commands\r\n" 
                                        + "    quit: quits the program");
    
        
            }
        }
        
    }  
}

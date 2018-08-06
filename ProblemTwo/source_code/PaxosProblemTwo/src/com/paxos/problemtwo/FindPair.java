package com.paxos.problemtwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is use for finding the pair of two {@link Gift}s from a given list, 
 * making sure the total price of such pair is minimally less than the given gift card balance.
 * The given gift card balance should be a non-negative integer, and the given list of gifts should be provided via a text file.
 * @author Xiang Li
 *
 */
public class FindPair {
    public static void main(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException("Number of arguments should always be two!");
        }
        if (!args[1].trim().matches("[0-9]+")) {
            throw new IllegalArgumentException("The second argument is the price, it must be a non-negative number!");
        }

        final String inputFileName = args[0].trim();
        final int balance = Integer.valueOf(args[1].trim());

        List<Gift> giftListAscendingPrice = new ArrayList<>();
        BufferedReader br = null;
        try (FileReader fileReader = new FileReader(inputFileName)) {
            br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] giftInfo = line.split(",");
                if (isGiftInfoValid(giftInfo, balance)) {
                    giftListAscendingPrice.add(new Gift(giftInfo));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + inputFileName + " does not exist!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (giftListAscendingPrice.isEmpty()) {
            throw new IllegalArgumentException("We don't have anything in gift list!");
        }

        findMinimalLessThanBalance(giftListAscendingPrice, balance);
    }

    /**
     * Checks if the provided gift info entry is valid.
     * A gift info entry is valid if:
     * 1. it has 2 elements; and
     * 2. its first element is a string with any character, but the length must be > 0; and
     * 3. its second element is a string containing only numeric values; and
     * 4. its second element, as a number, is less than the balance.
     * 
     * If the gift info entry violates 1,2 or 3, the input file is illegal, we should abort the program.
     * If the gift info entry violates 4, only this entry is not valid, we will just skip this entry.
     * @param giftInfo the gift info in a String array form, first element is name, second element is price
     * @param balance the balance we have in our gift card
     * @return if the gift info is valid
     */
    private static boolean isGiftInfoValid(String[] giftInfo, int balance) {

        if (giftInfo.length != 2) {
            throw new IllegalArgumentException("We should have one name and one price for each gift!");
        }

        if (giftInfo[0].trim().length() < 1) {
            throw new IllegalArgumentException("The name of the gift should have at least one character!");
        }

        if (!giftInfo[1].trim().matches("[0-9]+")) {
            throw new IllegalArgumentException("The price of the gift should be a number");
        }

        if (Integer.valueOf(giftInfo[1].trim()) >= balance) {
            return false;
        }

        return true;
    }

    /**
     * Finds the combination of the two gifts for which the sum of the prices is minimally less than our gift card balance.
     * Note that based on the problem description, the input is already sorted; since we are reading the file from first line to last line, 
     * we can then guarantee that the gift list we generated is also sorted with ascending price.
     * Let's say the number of gifts in the list is n.
     * O(n) time complexity: we simply just iterate the gift list, and therefore each element is visited and processed once.
     * O(n) space complexity: the gift list will take O(n) space.
     * @param giftListAscendingPrice the gift list, it should be sorted with ascending gift price
     * @param balance the gift card balance we have
     */
    private static void findMinimalLessThanBalance(List<Gift> giftListAscendingPrice, int balance) {
        int leftGiftIndex = 0;
        int rightGiftIndex = giftListAscendingPrice.size() - 1;
        int minDifference = balance + 1;

        int bestLeftGiftIndex = leftGiftIndex;
        int bestRightGiftIndex = rightGiftIndex;

        while (leftGiftIndex < rightGiftIndex) {
            int sum = giftListAscendingPrice.get(leftGiftIndex).price() + giftListAscendingPrice.get(rightGiftIndex).price();
            int difference = balance - sum;
            if (difference < 0) {
                // current combination is invalid - we can't afford a total greater than our balance, let's try a cheaper one
                rightGiftIndex--;
            } else {
                // the total is less or equal to our balance, it is valid, now let's find if the total is minimally less than balance
                if (difference < minDifference) {
                    minDifference = difference;
                    bestLeftGiftIndex = leftGiftIndex;
                    bestRightGiftIndex = rightGiftIndex;
                }
                // let's continue our search, trying a more expensive one
                leftGiftIndex++;
            }
        }
        if (minDifference > balance) {
            System.out.println("Not possible");
        } else {
            System.out.println(giftListAscendingPrice.get(bestLeftGiftIndex)+", "+giftListAscendingPrice.get(bestRightGiftIndex));
        }
    }
}

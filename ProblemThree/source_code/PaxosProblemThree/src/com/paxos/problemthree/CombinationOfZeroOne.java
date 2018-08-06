package com.paxos.problemthree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is used for the permutation of all possible strings by replacing each 'X' with 'O' or '1' in a given string.
 * The given string is required to only contain 'X', 'O' or '1'. 
 * @author Xiang Li
 *
 */
public class CombinationOfZeroOne {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Number of arguments should always be one!");
        }
        if (!args[0].trim().matches("[01X]+")) {
            throw new IllegalArgumentException("The input must only contain \'0\', \'1\', and \'X\'");
        }
        getAllCombinationAndPrint(args[0]);
    }

    /**
     * Gets all combinations of replacing each 'X' by '1' or '0'.
     * Let's say the length of the input string is n, and the number of 'X' is x.
     * O((2 ^ x) * n) time complexity: 
     * we have 2^x possibilities of replacing 'X' with '1' or '0', and O(n) time for each replacing and finding first 'X' occurrence.
     * O((2 ^ x) + n) space complexity:
     * we need to maintain a queue to store possible combinations, in worst case (all characters are 'X'), 
     * it is (2^x) space complexity; we will also need extra space to store newly generated strings and substrings.
     * @param input the original input entered by user
     */
    private static void getAllCombinationAndPrint(String input) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(input);
        while (!queue.isEmpty()) {
            String currentString = queue.poll();
            int firstXOccurrence = currentString.indexOf('X');
            if (firstXOccurrence == -1) {
                System.out.println(currentString);
            } else {
                queue.offer(replaceFirstOccurredX(firstXOccurrence, currentString, '0'));
                queue.offer(replaceFirstOccurredX(firstXOccurrence, currentString, '1'));                
            }
        }
    }

    /**
     * Replaces the first 'X' in the string by a given character.
     * O(n) time complexity and O(n) space complexity: 
     * we will always create 3 String objects: 2 substrings and 1 string to return.
     * @param firstXIndex the index at which we first find 'X' in the originalString
     * @param originalString the string we need to replace its first 'X' by charForReplace
     * @param charForReplace the char we will use to replace 'X'
     * @return the string in which the first 'X' is replaced
     */
    private static String replaceFirstOccurredX(int firstXIndex, String originalString, char charForReplace) {
        return originalString.substring(0, firstXIndex) + charForReplace + originalString.substring(firstXIndex+1);
    }
}
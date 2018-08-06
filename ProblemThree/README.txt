This is a description of my solution of problem three.

Please refer to the source code for implementation details.

Let's say x is the number of 'X's in the input and n is the length of the input string.

The time complexity is O((2^x) * n).
We have 2^x possibilities of replacing 'X' with '1' or '0', and O(n) time for each replacing and finding first 'X' occurrence.

The space complexity is O((2^x) +  n).
We need to maintain a queue to store possible combinations, in worst case (all characters are 'X'), it is (2^x) space complexity; we will also need extra space to store newly generated strings and substrings.


The runnable jar is in runnable_jar/ directory, to run it:

java -jar myprogram.jar <input>

For example:

java -jar myprogram.jar 10X10X0

The source code is source_code/PaxosProblemThree/src/com/paxos/problemthree/CombinationOfZeroOne.java


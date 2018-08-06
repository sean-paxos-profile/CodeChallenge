This is a description of my solution of problem two.

Please refer to the source code for implementation details.

Let's say n is the number of gift entries in our input file.

The time complexity is O(n).
We simply just iterate the gift list, and therefore each element is visited and processed once.

The space complexity is O(n).
The gift list will take O(n) space.

The runnable jar is in runnable_jar/ directory, to run it (in runnable_jar, we also have an input file prices.txt used to test):

java -jar find-pair.jar <input_filename> <balance>

For example:

java -jar find-pair.jar prices.txt 2500

The source code is:
source_code/PaxosProblemTwo/src/com/paxos/problemtwo/Gift.java 
source_code/PaxosProblemTwo/src/com/paxos/problemtwo/FindPair.java

package com.paxos.problemtwo;

/**
 * Gift class.
 * A Gift object includes the information of a gift, containing:
 * 1. name - the name of the gift
 * 2. price - the price of the gift
 * @author Xiang Li
 *
 */
class Gift {
    String name;
    int price;

    public Gift(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * This constructor takes a String array of size 2, 
     * for which the first element is the gift's name, and the second element is the gift's price.
     * The caller of this constructor is responsible for checking if the giftInfo is valid.
     * @param giftInfo the String array of size 2
     */
    public Gift(String[] giftInfo) {
        this.name = giftInfo[0].trim();
        this.price = Integer.valueOf(giftInfo[1].trim());
    }

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    @Override
    public String toString() {
        return name+" "+price;
    }
}


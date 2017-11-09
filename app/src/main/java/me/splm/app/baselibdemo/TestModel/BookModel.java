package me.splm.app.baselibdemo.TestModel;


import java.io.Serializable;

/**
 * Guarantee your Object could be Serializabled.
 */
public class BookModel implements Serializable {
    private String name;
    private int price;
    private int tag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}

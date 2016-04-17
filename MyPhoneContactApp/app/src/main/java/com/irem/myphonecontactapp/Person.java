package com.irem.myphonecontactapp;

/**
 * Created by irem on 01.04.2016.
 */
public class Person {
    /*Person object has name , number and picture instance*/
    private String name;
    private String numbers;
    private int pictureResourceID;

    public Person(String name,String numbers,int pictureResourceID){
        this.name = name;
        this.numbers = numbers;
        this.pictureResourceID = pictureResourceID;

    }
    public String getNumbers() {
        return numbers;
    }
    public String getName() {
        return name;
    }
    public int getPictureResourceID() {
        return pictureResourceID;
    }
    public void setName(String name1){
        name=name1;
    }
    public void setNumbers(String numbers1) {
        numbers=numbers1;
    }
}

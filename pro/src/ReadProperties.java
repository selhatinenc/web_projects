package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadProperties {
  private   int maxblocked,blocktime,discountpercentage; private String title;
    public ReadProperties(){
        try {
            ArrayList<String> all= (ArrayList<String>) Files.readAllLines(Paths.get(new File("src/main/resources/assets/data/properties.dat").toString()));
           blocktime=Integer.parseInt(all.get(5).split("=")[1]); maxblocked= Integer.parseInt(all.get(2).split("=")[1]);title=all.get(3).split("=")[1]; discountpercentage= Integer.parseInt(all.get(4).split("=")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getMaxblocked() {
        return maxblocked;
    }

    public int getDiscountpercentage() {
        return discountpercentage;
    }

    public String getTitle() {
        return title;
    }

    public int getBlocktime() {
        return blocktime;
    }
}

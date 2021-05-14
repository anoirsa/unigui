package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class is only for testing purposes during the building of the application
// Therefore it does not have any relation with the structure of the application
public class testingGroundsClass {

    public static void main(String[] args) {
        List<String> stingi = new ArrayList<>();
        stingi.add("Joe");
        List<String> moni =  new ArrayList<>();
        moni = (ArrayList)((ArrayList<String>) stingi).clone();
        moni.add("Biden");

        System.out.println(stingi);
        System.out.println(moni);
    }
}

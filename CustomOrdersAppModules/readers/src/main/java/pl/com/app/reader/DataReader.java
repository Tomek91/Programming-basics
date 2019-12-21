package pl.com.app.reader;

import java.util.Scanner;

public class DataReader {
    private static Scanner sc = new Scanner(System.in);

    public static void close() {
        sc.close();
    }


    public String getString(){
        return sc.nextLine();
    }
}

import java.awt.image.DataBuffer;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        UpdateChecker updateChecker = new UpdateChecker();
        updateChecker.check();
    }

}

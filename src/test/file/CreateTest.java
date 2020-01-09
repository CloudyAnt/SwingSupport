package test.file;

import pri.file.FOperator;

import java.io.File;

// This test will create real file
public class CreateTest {
    public static void main(String args[]) {
        FOperator.write(new File("/Users/20890/Desktop/test.txt"), "Hello");
    }
}

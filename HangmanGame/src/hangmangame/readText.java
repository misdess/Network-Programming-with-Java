package hangmangame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class readText {

    static File file = new File("C:\\File.txt");

    public readText() {
    }

    public String getLine(int lineNum) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        for (int i = 0; i < lineNum; i++) {
            line = br.readLine();
        }
        return line;
    }

    public static void main(String[] args) throws IOException {
        readText rt = new readText();
        System.out.println(rt.getLine(3));
        if (file.canWrite()) {
            file.renameTo(new File("C:\\misganu.txt"));
            System.out.println(new Date(file.lastModified()));
            System.out.println(file.getPath());
            System.out.println(file.getParentFile());
        }
    }
}

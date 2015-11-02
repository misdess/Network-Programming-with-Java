package hangmangame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class SlowConnectionHandler implements Runnable {

    private final Socket socket;
    private static int winCounter = 0, attemptCounter;
    private static Character a;
    private static String str;
    char[] given2;
    private static final Random rand = new Random();
    readText rt = new readText();

    SlowConnectionHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try {
            String given = rt.getLine(rand.nextInt(25143));
            System.out.println(given);
            winCounter = 0;
            boolean first = true;
            boolean charFound = false;

            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                given2 = new char[given.length()];
                attemptCounter = 9;
                System.out.println(given);
                for (int i = 0; i < given.length(); i++) {
                    given2[i] = '_';
                }
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                String view = new String(given2).concat(Integer.toString(attemptCounter)).concat(getSign(winCounter));
                if (first) {
                    pw.print(view);
                    pw.println();
                    pw.flush();
                }
                first = false;
//game already started
                while (attemptCounter > 0) {
                    str = br.readLine();

                    if (str.length() < 2) {
                        a = str.charAt(0);
                        int index = 0;
                        while (index < given.length()) {
                            if (charCompare(a, given.charAt(index)) == true) {
                                if(given2[index]!=a)//deal with repeated input of the same character
                                    charFound = true;
                                given2[index] = a;
                                index++;
                                
                                continue;
                            }
                            index++;
                        }
                        if (!charFound) {
                            attemptCounter--;
                        }
                        charFound = false;

                        if (arrayCompare(given2, given.toCharArray())) {//winMessage();
                            winCounter++;
                            attemptCounter = 0;
                            given = generateWord();
                            view = new String(generateWord(given)).concat(Integer.toString(attemptCounter)).concat(getSign(winCounter));

                        } else if (attemptCounter == 0) {//lossMessage();
                            winCounter--;
                            given = generateWord();
                            view = new String(generateWord(given)).concat(Integer.toString(attemptCounter)).concat(getSign(winCounter));

                        } else {
                            view = new String(given2).concat(Integer.toString(attemptCounter)).concat(getSign(winCounter));
                        }
                        pw.print(view);
                        pw.println();
                        pw.flush();

                    } else {//input is a word
                        if (str.compareToIgnoreCase(given) == 0) {//winMessage();
                            winCounter++;
                            attemptCounter = 0;
                            given = generateWord();
                            view = new String(generateWord(given)).concat(Integer.toString(attemptCounter)).concat(getSign(winCounter));
                            pw.print(view);
                            pw.println();
                            pw.flush();

                            break;
                        } else {//lossMessage();
                            attemptCounter--;
                            if (attemptCounter == 0) {
                                winCounter--;
                            }
                            view = new String(given2).concat(Integer.toString(attemptCounter)).concat(getSign(winCounter));
                            pw.print(view);
                            pw.println();
                            pw.flush();
                        }
                    }
                }
            }
            //  System.out.println("Game exiting");
        } catch (IOException ex) {
            Logger.getLogger(SlowConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private char[] generateWord(String str) throws IOException {
        given2 = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            given2[i] = '_';
        }
        return given2;
    }

    private String generateWord() throws IOException {
        return rt.getLine(rand.nextInt(25143));
    }

    private static boolean charCompare(char c1, char c2) {
        return Character.toString(c1).compareToIgnoreCase(Character.toString(c2)) == 0;

    }

    private static boolean arrayCompare(char[] str1, char[] str2) {
        boolean equal = true;
        for (int i = 0; i < str1.length; i++) {
            if (str1[i] != str2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    private String getSign(int a) {
        if (a == 0) {
            return "00";
        }
        if (a > 0) {
            return "+" + a;
        } else {
            return "-" + a * -1;
        }
    }
}

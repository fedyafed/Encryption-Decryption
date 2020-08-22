package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int shift = 0;
        boolean unicode = false;
        boolean encode = true;
        char[] source = null;
        String inFile = null;
        String outFile = null;
        for (int i = 0; i < args.length / 2; i++) {
            switch (args[2 * i]) {
                case "-mode":
                    encode = args[2 * i + 1].equalsIgnoreCase("enc");
                    break;
                case "-key":
                    shift = Integer.parseInt(args[2 * i + 1]);
                    break;
                case "-data":
                    source = args[2 * i + 1].toCharArray();
                    break;
                case "-in":
                    inFile = args[2 * i + 1];
                    break;
                case "-out":
                    outFile = args[2 * i + 1];
                    break;
                case "-alg":
                    unicode = (args[2 * i + 1].equalsIgnoreCase("unicode"));
            }
        }

        final Encoder encoder = Encoder.getInstance(unicode, shift, encode);
        if (source != null) {
            encoder.setSource(source);
        } else {
            encoder.setSource(inFile);
        }
        encoder.setTarget(outFile);
        encoder.process();
    }
}

abstract class Encoder {
    protected char[] source;
    protected char[] result;
    private String outFile;

    abstract void encode();

    public void process() {
        if (source == null) {
            return;
        }

        encode();
        writeResult();
    }

    private void writeResult() {
        PrintWriter writer = null;
        try {
            if (outFile == null) {
                writer = new PrintWriter(System.out);
            } else {
                writer = new PrintWriter(outFile);
            }
            writer.println(result);
        } catch (FileNotFoundException e) {
            System.out.println("Error in writing result");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static Encoder getInstance(boolean unicode, int shift, boolean encode) {
        if (unicode) {
            return new UnicodeEncoder(shift, encode);
        } else {
            return new ShiftEncoder(shift, encode);
        }
    }

    public void setSource(char[] source) {
        this.source = source;
    }

    public void setSource(String inFile) {
        Scanner scanner = null;
        try {
            if (inFile == null) {
                scanner = new Scanner(System.in);
            } else {
                scanner = new Scanner(new File(inFile));
            }
            setSource(scanner.nextLine().toCharArray());
        } catch (Exception e) {
            System.out.println("Error in reading source.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public void setTarget(String outFile) {
        this.outFile = outFile;
    }
}

class ShiftEncoder extends Encoder {
    private final int shift;

    public ShiftEncoder(int shift, boolean encode) {
        if (encode) {
            this.shift = shift % 26;
        } else {
            this.shift = -shift % 26;
        }
    }

    @Override
    void encode() {
        result = new char[source.length];
        for (int i = 0; i < source.length; i++) {
            if (source[i] >= 'a' && source[i] <= 'z') {
                result[i] = (char) ('a' + (source[i] - 'a' + shift + 26) % 26);
            } else {
                result[i] = source[i];
            }

        }
    }
}

class UnicodeEncoder extends Encoder {
    private final int shift;

    public UnicodeEncoder(int shift, boolean encode) {
        if (encode) {
            this.shift = shift;
        } else {
            this.shift = -shift;
        }
    }

    @Override
    void encode() {
        result = new char[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = (char) (source[i] + shift);
        }
    }
}

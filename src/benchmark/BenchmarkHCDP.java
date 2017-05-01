/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark;

import hcdp.Code;
import hcdp.Compressor;
import hcdp.HuffmanNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Admin
 */
public class BenchmarkHCDP {

    public static void executeBechmark(String sampleTextPath) throws IOException {
        
        int dictionarySize = 4096;

        // Frequency counting
        Compressor compressor = new Compressor();
        HashMap<String, Integer> frequencies = compressor.countFrequencies(sampleTextPath);
        frequencies = compressor.filterFrequencies(frequencies, dictionarySize);
        HuffmanNode generatedTree = compressor.generateTree(frequencies);

        HashMap<String, String> binaryTableEncode = compressor.generateBinaryTable(generatedTree, new String(""), new HashMap<String, String>());

        FileReader fileReader = new FileReader(sampleTextPath);
        BufferedReader lineReader = new BufferedReader(fileReader);
        StringBuilder textToEncode = new StringBuilder();
        
        String line = "";

        final int numberOfWordsCompression = 100;

        int counter = 0;
        double sumRatios = 0;
        int numberOfBlocks = 0;
        StringTokenizer token = new StringTokenizer("");
        boolean readLine = true;

        while (true) {
            if (readLine) {
                if ((line = lineReader.readLine()) != null) {
                    token = new StringTokenizer(line);
                } else {
                    break;
                }
            }
            while (counter < numberOfWordsCompression && token.hasMoreTokens()) {
                textToEncode.append(token.nextToken()).append(" ");
                counter++;
            }
            if (counter >= numberOfWordsCompression) {
                String text = textToEncode.toString().trim();
                long t1 = System.currentTimeMillis();

                Code code = compressor.encode(text, binaryTableEncode);
                long t2 = System.currentTimeMillis();
                double compressionRatio = ((code.getData().length * 1.0 * 8) / (text.length() * 8)) * 100.0;
                System.out.println("Compression Ratio: " + compressionRatio
                        + " - Compression Time: " + (t2 - t1));

                numberOfBlocks++;
                sumRatios += compressionRatio;

                counter = 0;
                textToEncode.delete(0, textToEncode.length() - 1);
                readLine = true;
                if (token.hasMoreTokens()) {
                    readLine = false;
                }
            }
            if (!token.hasMoreTokens()) {
                readLine = true;
            }
        }

        if (textToEncode.length()
                > 0) {
            String text = textToEncode.toString().trim();
            // Correccion Error con el String Builder
            if (text.length() > 0) {
                long t1 = System.currentTimeMillis();
                Code code = compressor.encode(text, binaryTableEncode);
                long t2 = System.currentTimeMillis();
                double compressionRatio = ((code.getData().length * 1.0 * 8 + 32) / (text.length() * 8)) * 100.0;
                System.out.println("Compression Ratio: " + compressionRatio
                        + " - Compression Time: " + (t2 - t1));
                numberOfBlocks++;
                sumRatios += compressionRatio;
            }
        }

        System.out.println(
                "AVERAGE COMPRESSION RATIO = " + (sumRatios / numberOfBlocks));
    }
}

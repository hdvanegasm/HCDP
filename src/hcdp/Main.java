/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcdp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        String filePath = "Put file path here";
        int dictionarySize = 4096;

        // Frequency counting
        Compressor compressor = new Compressor();
        HashMap<String, Integer> frequencies = compressor.countFrequencies(filePath);
        frequencies = compressor.filterFrequencies(frequencies, dictionarySize);
        HuffmanNode generatedTree = compressor.generateTree(frequencies);

        HashMap<String, String> binaryTableEncode = compressor.generateBinaryTable(generatedTree, new String(""), new HashMap<String, String>());

        String text = "Put text to encode here";

        // Compressed text
        Code encodedText = compressor.encode(text, binaryTableEncode);

        // Decode
        Decompressor decompressor = new Decompressor();
        HashMap<String, String> decodeTable = decompressor.generateDecodeTable(binaryTableEncode);
        String decodedText = decompressor.decode(encodedText, decodeTable);
    }

}

# HCDP
This is a Java implementation of HCDP compression method

## GENERAL DESCRIPTION:

In this document you can find a Java implementation of HCDP compression method. It is a method based on Huffman Coding and the application of dynamic programming algorithms.

## HOW TO USE:

In the source code, you can find all the classes needed to run the algorithm. There are two main classes: the firs class is "Main.java"; here you can find a basic structure in order to use the compression and decompression functions; You must modify the variable "filePath", at this point you must put the file path of the sample text that you will use in order to construct the compression dictionary; Also you should modify the string "text", here you will put the text that you want to compress, then you can use the "Decompressor" class in order to decode some compress information. The second class is "BenchmarkHCDP.java", here you will run some experimental test in order to calculate the compression ratio and the compression time of the method; This test takes a sample text and builds the dictionary based on this text, then the method take blocks of 100 words of the sample text and compress them in order to calculate a partial compression ratio; After the compression of the whole text, it calculates the "average compression ratio" based on the partial compression ratios calculated before.


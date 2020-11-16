package com.sauna;

import org.junit.Assert;
import org.junit.Test;

public class ASCIIMapAnalyzerTest {

    @Test
    public void test_map1() throws Exception {
        ASCIIMapAnalyzer asciiMapAnalyzer = new ASCIIMapAnalyzer("./src/main/resources/map1.txt");
        Solution solution = asciiMapAnalyzer.getSolution();
        Assert.assertEquals("ACB", solution.getLetters());
        Assert.assertEquals("@---A---+|C|+---+|+-B-x", solution.getFullPath());
    }

    @Test
    public void test_map2() throws Exception {
        ASCIIMapAnalyzer asciiMapAnalyzer = new ASCIIMapAnalyzer("./src/main/resources/map2.txt");
        Solution solution = asciiMapAnalyzer.getSolution();
        Assert.assertEquals("ABCD", solution.getLetters());
        Assert.assertEquals("@|A+---B--+|+----C|-||+---D--+|x", solution.getFullPath());
    }

    @Test
    public void test_map3() throws Exception {
        ASCIIMapAnalyzer asciiMapAnalyzer = new ASCIIMapAnalyzer("./src/main/resources/map3.txt");
        Solution solution = asciiMapAnalyzer.getSolution();
        Assert.assertEquals("BEEFCAKE", solution.getLetters());
        Assert.assertEquals("@---+B||E--+|E|+--F--+|C|||A--|-----K|||+--E--Ex", solution.getFullPath());
    }
}

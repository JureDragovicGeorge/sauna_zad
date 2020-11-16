package com.sauna;

public class Solution {

    private String fullPath;
    private String letters;

    public Solution(String fullPath, String letters) {
        this.fullPath = fullPath;
        this.letters = letters;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}

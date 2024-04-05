package com.brent.ik.recursive;

import java.util.ArrayList;

public class Palindromic {

    static ArrayList<String> generate_palindromic_decompositions(String s) {
        ArrayList<Character> partial = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        String lastWord = "";
        int curr = 0;
        String slate ="";
        helper(s,slate,result,curr,lastWord);
        return result;
    }
    static void helper(String word,String slate,ArrayList<String> result, int curr, String lastWord){

        if(curr==word.length()){
            result.add(slate);
            return;
        }
        if(isPalindrome(lastWord)){
            String temp = new String(slate);
            slate += word.charAt(curr) + "|";
            helper(word,slate,result,curr+1,"" + word.charAt(curr+1));
            slate = temp;

        }
        String temp = new String(slate);
        slate += word.charAt(curr);
        lastWord+= word.charAt(curr);
        helper(word,slate,result,curr+1,lastWord);
        slate = temp;

    }
    static boolean isPalindrome(String word){

        for(int i=0;i<word.length()/2;i++){
            if(word.charAt(i)!=(word.charAt(word.length()-i-1))){
                return false;
            }
        }
        return true;
    }

}

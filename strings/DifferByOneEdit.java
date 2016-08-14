/*
 * There are three types of edits that can be performed on a string: insert a character, delete a character, or replace a character. An edit can occur anywhere within the string.
 *
 * Given two strings, write a method to check if they are 1 or 0 edits away, returning a boolean value
 *
 *
 *  For example: 
 *  d("hello", "hellos") = 1 insertion, yes, true
 *  d("hello", "helslo") = 1 insertion, yes, true   
 *  d("hello", "ello") = 1 deletion, yes, true
 *  d("hello", "bello") = 1 replacement, yes, true
 *  d("hello", "hellooo") = 2 insertions, no, false
 *  d("hello", "olleh") = 4 replacements, no, false
 *  d("hhllo", "hhlsla") = 1 insertion + 1 replacement, no, false
 */


import java.io.*;
import java.util.*;

class Solution {
  public static void main(String[] args) {
    // one insertion
    System.out.println( "Should be true: " + solve("hello", "hellos"));
    System.out.println( "Should be true: " + solve("hello", "hellso"));
    System.out.println( "Should be true: " + solve("hello", "helslo"));
    System.out.println( "Should be true: " + solve("hello", "hesllo"));
    System.out.println( "Should be true: " + solve("hello", "hsello"));
    System.out.println( "Should be true: " + solve("hello", "shello"));
  
    // one removal
    System.out.println( "Should be true: " + solve("hello", "ello") );
    System.out.println( "Should be true: " + solve("hello", "hllo") );
    System.out.println( "Should be true: " + solve("hello", "helo") );
    System.out.println( "Should be true: " + solve("hello", "hell") );
    
    // one replacement
    System.out.println( "Should be true: " + solve("hello", "bello") );
    System.out.println( "Should be true: " + solve("hello", "hzllo") );
    System.out.println( "Should be true: " + solve("hello", "hezlo") );
    System.out.println( "Should be true: " + solve("hello", "helzo") );
    System.out.println( "Should be true: " + solve("hello", "hellz") );
    
    // more than one eidt    
    System.out.println( "Should be false: " + solve("hello", "hellooo") );
    System.out.println( "Should be false: " + solve("hello", "bellz") );
    System.out.println( "Should be false: " + solve("hello", "olleh") );
    System.out.println( "Should be false: " + solve("hello", "hlleol") );
    
  }
         
  public static Boolean solve(String str1, String str2) {
      if (str1.length() == 0) {
          return (str2.length() < 2) ? true : false; 
      }

      if (str2.length() == 0) {
          return (str1.length() < 2) ? true : false; 
      }
      
      
      if (Math.abs( str1.length() - str2.length() ) < 2) {
          if (str1.length() == str2.length()) { // EQUAL LENGTH CASE
              // find out if 2 strings differ by only ONE character
              int length = str1.length();
          
              for (int i = 0; i < length; i++) {
                  if (str1.charAt(i) != str2.charAt(i)) {
                      String str1Rest = str1.substring(i+1);
                      String str2Rest = str2.substring(i+1);
                      if (str1Rest.equals(str2Rest)) {
                          return true; // 1 replacement edit away
                      } else {
                          return false;
                      }
                  }
              }
          
              return true; // 0 edits away
        
          } else {
              // LENGTHS DIFFER BY EXACTLY 1 CHARACTER: ONLY one insertion or ONLY one deletion
              // FROM larger string ONLY one character needs to be removed
              // in order to get the smaller string.
              if (str1.length() > str2.length()) { // str1 is longer
                  for (int i = 0; i < str1.length(); i++) {
                      String removeIsubstring = str1.substring(0,i) + str1.substring(i+1);
                      if (str2.equals(removeIsubstring)) {
                          return true;
                      }
                  }
          
                  return false;
            
              } else {
      
                  for (int i = 0; i < str2.length(); i++) {
                      String removeIsubstring = str2.substring(0,i) + str2.substring(i+1);
                      if (str1.equals(removeIsubstring)) {
                          return true;
                      }
                  }
      
                  return false;
              }
          }
        }
        return false;
    }
}

package com.qhrtech.devquiz.digitsquaresum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;

/**
 * My submission to QHR that I am proud of.
 * @author Sadegh Charmchi
 */
public class MySubmission extends DigitSquareSumSubmission {
    // TODO: Update with candidate's name
    private static final String MY_NAME = "Sadegh Charmchi";

    public MySubmission() {
        super(MY_NAME);
    }
    
    @Override
    public int digitSquareSum(int n) {
        int sum = 0;
        while (n > 0) {
        	int lastDigit = n % 10;
        	sum += lastDigit * lastDigit;
        	n = n / 10;
        }
        return sum;
    }

    @Override
    public File questionOne() {
    	File f = new File("Q1_Output.csv");
    	
    	// This will automatically handle closing of the file in case of an exception in java 1.7 and up
    	try (PrintStream ps = new PrintStream(f)) {
    		for (int n = 1; n <= 500; n++) {
        		int res = digitSquareSum(n);
        		ps.println(n + "," + res);
        	}
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return f;
    }

    @Override
    public String questionTwo() {
    	String output = "";
    	
    	// keep the numbers in the cycle that we have seen before
    	// if we see the number again then prevent cycle from happening again
    	HashSet<Integer> numsInCycle = new HashSet<>();
    	for (int n = 1; n <= 500; n++) {
    		numsInCycle.clear();
    		int number = n;
    		while (!numsInCycle.contains(number)) {
    			numsInCycle.add(number);
    			number = digitSquareSum(number);
    			if (number == 1) {
    				output += n + ",";
    				break;
    			}
    		}
    	}
    	// get rid of the last comma
    	output = output.substring(0, output.length() - 1);
        return output;
    }
    
    // Credits to Kristian https://www.mathblog.dk/project-euler-92-square-digits-number-chain/
    @Override
    public Integer questionThree() {
    	
    	int result = 0;
        int target = 10000000;
        int numDigits = (int)Math.ceil(Math.log10(target));
        // number here is our integer but each digit is in once cell of the array
        // so we can work with digits separately
        int[] number = new int[numDigits];            
        int digitPointer = numDigits-1;
        
        // start the digit pointer on the rightmost digit xxxxxxx>x< this last one
        // then we keep incrementing it and once it hits 9 then we move the digit pointer one step backward
        while (true) {
            if (digitPointer == 0 && number[digitPointer] == 9)
                // This means we are at the first digit and it is 9 so we have reached the max
                break;
            if (digitPointer == numDigits - 1 && number[digitPointer] < 9) {
                // Increase the last number
                number[digitPointer]++;
                result += getAllNumberVariations(number);                    
            } else if (number[digitPointer] == 9) {
                // we reach max for this specific digit, so move back
            	digitPointer--;
            } else {
                //Increase a digit
                number[digitPointer]++;
                //Move to the end
                for (int j = digitPointer+1; j < numDigits; j++) {
                    number[j] = number[digitPointer];    
                }
                digitPointer = numDigits - 1;
                result += getAllNumberVariations(number);                    
            }                
        }
        return result;
    	
    }
    
    private int getAllNumberVariations(int[] number) {
        
    	int permutations = 0;
    	
    	// make the array into a number
    	int candidate = 0;
    	for (int i = 0; i < number.length; i++) {
    	    candidate = candidate * 10 + number[i];
    	}
        //Calc sequence
        int counter = 0;
        boolean shouldCountPermutations = false;
        // variations of number 89 except 89 itself all have more than 7 cycles so make sure to include them
        if (candidate == 89) {
        	shouldCountPermutations = true;
        	// exclude number 89 itself because its cycle is just one
        	permutations -= 1;
        }
        while (candidate != 89 && candidate != 1) {
            candidate = digitSquareSum(candidate);
            counter++;
            if (candidate == 89 && counter > 7)
            	shouldCountPermutations = true;
        }
        
        if (candidate == 89) {
            // calc the number of each digit
            int[] numDigits = new int[10];                
            for (int i = 0; i < number.length; i++) {
                numDigits[number[i]]++;
            }

            //calculate the multinomial coefficient because we might have non-distinct digits
            int numerator = factorial(number.length);
            for (int i = 0; i < numDigits.length; i++) {
            	// divide by factorial of each digit repetition to make up for
            	//  the digits that happen multiple times in the number
            	int denuminator = factorial(numDigits[i]); 
            	numerator /= denuminator;
            }
            permutations += numerator;
            if (shouldCountPermutations)
            	return permutations;
         }

        return 0;
    }

    private int factorial(int i) {
        if (i < 1) {
            return 1;
        }

        int p = 1;
        for (int j = 1; j <= i; j++) {
            p *= j;
        }
        return p;
    }
    
}

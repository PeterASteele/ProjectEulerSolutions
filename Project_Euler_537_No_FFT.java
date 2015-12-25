import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.*;

import java.util.*;
//Project Euler 537  
//https://projecteuler.net/problem=537
public class Project_Euler_537_No_FFT {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		long mod = 1004535809;
		long left = input.nextInt();
		long right = input.nextInt();
		boolean[] sieve = sieve((int) (left * 100));
		long[] primeCounts = new long[sieve.length];
		long count = 0;
		for (int a = 0; a < sieve.length; a++) {
			if (sieve[a] == true) {
				count++;
			}
			primeCounts[a] = count;
		}
		long[] frequencyDistribution = new long[(int) left + 1];
		for (int a = 0; a < sieve.length; a++) {
			if (primeCounts[a] <= left)
				frequencyDistribution[(int) primeCounts[a]]++;
		}
		frequencyDistribution[0]--;
		for (int a = 0; a < frequencyDistribution.length; a++) {
			System.out.println("There are " + frequencyDistribution[a]
					+ " numbers with " + a + " primes below them ");
		}
		Polynomial test = new Polynomial(frequencyDistribution);
		Polynomial[] powersOf2 = new Polynomial[(int) (Math.log(left)/Math.log(2) + 1)];
		powersOf2[0]  = test;
		//System.out.println(powersOf2[0]);
		for(int a = 1; a < (int) (Math.log(left)/Math.log(2) + 1); a++){
			powersOf2[a] = powersOf2[a-1].multiply(powersOf2[a-1]);
		//	System.out.println(powersOf2[a]);
		}
		Polynomial start = new Polynomial(frequencyDistribution);
		for(int a = 0; a < frequencyDistribution.length; a++){
			start.coefficients[a] = 0;
		}
		start.coefficients[0] = 1;
		for(int a = 0; a < (int) (Math.log(left)/Math.log(2) + 1); a++){
			if((left>>a)%2 == 1){
				start = start.multiply(powersOf2[a]);
				//System.out.println("adding in the " + a + "th power");
				//System.out.println(start);
			}
		}
		
		System.out.println(start.coefficients[(int) (left)]);
	}
	public static class Polynomial{
		long[] coefficients;
		public Polynomial(long[] coefficients){
			this.coefficients  = coefficients;
		}
		public Polynomial multiply(Polynomial other){
			int length  = other.coefficients.length;
			Polynomial output =  new Polynomial(new long[length]);
			for(int  a = 0; a < other.coefficients.length; a++){
				for(int b = 0; b <= a; b++){
					output.coefficients[a] += other.coefficients[b] * coefficients[a-b];
					output.coefficients[a] = output.coefficients[a]%1004535809;
				}
			}
			return output;
		}
		public String toString(){
			StringBuilder output = new StringBuilder();
			for(int a = 0; a < coefficients.length; a++){
				output.append(coefficients[a] + " ");
			}
			return output.toString();
		}
	}
	static boolean[] sieve(int N) {
		boolean[] a = new boolean[N + 1];
		Arrays.fill(a, true);
		a[0] = a[1] = false;
		for (int p = 2; p * p <= N; p++)
			if (a[p]) {
				// Iterate through all multiples m of the prime and mark
				// them as not prime.
				for (int m = p * p; m <= N; m += p)
					a[m] = false;
			}
		return a;
	}

}

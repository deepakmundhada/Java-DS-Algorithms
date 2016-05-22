/* Problem Statement: On a positive integer, you can perform any one of the following 3 steps.
1.) Subtract 1 from it. ( n = n - 1 ),
2.) If its divisible by 2, divide by 2. ( if n % 2 == 0 , then n = n / 2  ),
3.) If its divisible by 3, divide by 3. ( if n % 3 == 0 , then n = n / 3  ).
Now the question is, given a positive integer n, find the minimum number of steps that takes n to 1.

Input - 1000
Output - Minimum number of steps are 9
*/

import java.util.Scanner;
import java.util.Arrays;

public class MinimumStepsTakesNTo1 {
	static int[] memo;

	static void initialize(int n) {
		memo = new int[n+1];
		Arrays.fill(memo, -1);
	}

	//Using Dynamic programming Bottom-up approach
	static int compute_steps(int n) {
		if(n == 1) return 0;

		memo[1] = 0;

		for(int i = 2; i <= n; i++) {
            memo[i] = 1 + memo[i-1];

            if(i % 2 == 0) memo[i] = Math.min(memo[i], 1 + memo[i/2]);

            if(i % 3 == 0) memo[i] = Math.min(memo[i], 1 + memo[i/3]);
		}

		return memo[n];
	}

	//Alternate way of doing same using top-down [gives stack overflow problem]
	/*static int compute_steps(int n) {
		if(n == 1) return 0;

		memo[1] = 0;

		if(memo[n] != -1) return memo[n];

		int r = 1 + compute_steps(n - 1);

		if(n % 2 == 0) r = Math.min(r, 1 + compute_steps(n/2));

		if(n % 3 == 0) r = Math.min(r, 1 + compute_steps(n/3));

		memo[n] = r;

		return memo[n];
	}*/

	public static void main (String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.println("Input integer value for N ");

		while(sc.hasNext()) {
			int n = sc.nextInt();

			initialize(n);

			System.out.println("Minimum number of steps are " + compute_steps(n));
			System.out.println("Have more input? Enter integer value for N ");
		}
	}
}
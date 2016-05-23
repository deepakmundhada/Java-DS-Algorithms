/* Problem statement: The longest Increasing Subsequence (LIS) problem is to find the length of the longest subsequence of a given sequence
such that all elements of the subsequence are sorted in increasing order.

Input - { 10, 22, 9, 33, 21, 50, 41, 60, 80 }
Output - Length is 6 and LIS is {10, 22, 33, 50, 60, 80}
*/

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class LongestIncreasingSequence {
	static List<Integer> list = new ArrayList<>();

	/*static int ceilIndex(int[] array, int l, int r, int key) {
		int m;

		while(r - l > 1) {
			m = l + (r - l) / 2;
			if(array[m] >= key) {
				r = m;
			}
			else {
				l = m;
			}
		}

		return r;
	}

	//Complexity is O(N log N)
	static int computeLIS(int[] array, int length) {
		int[] tail = new int[length];
		int len;

		tail[0] = array[0];
		len = 1;

		for(int i = 1; i < length; i++) {
			if(array[i] < tail[0]) {
				tail[0] = array[i];
			}
			else if(array[i] > tail[len-1]) {
				tail[len++] = array[i];
			}
			else {
				tail[ceilIndex(array, -1, len-1, array[i])] = array[i];
			}
		}

		System.out.print("LIS is ");
		for(int i = 0; i < len; i++) {
			System.out.print(tail[i] + " ");
		}

		return len;
	}*/

	//Complexity is O(N ^ 2)
	/*static int computeLIS(int[] array, int length) {
		int[] lis = new int[length];
		int i, j, m, max = 0;
		int[] tmp = new int[length];

		Arrays.fill(lis, 1);

		for(i = 1; i < length; i++) {
		    int k = 0;

			for(j = 0; j < i; j++) {
				if(array[i] > array[j] && lis[j]+1 > lis[i]) {
					lis[i] = lis[j] + 1;
					tmp[k++] = j;
				}
			}
			tmp[k] = j;

			if(max < lis[i]) {
			    list.clear();
				max = lis[i];

				for(m = 0; m <= k; m++) {
					list.add(array[tmp[m]]);
				}
			}
		}

		Iterator it = list.iterator();
		System.out.print("LIS is ");

		while(it.hasNext()) {
			System.out.print(it.next() + " ");
		}

		return max;
	}*/

	//From Wiki with complexity O(n log n)
	static int[] computeLIS(int[] X, int N) {
		int[] P = new int[N];

		//M[j] — stores the index k where j represents the length of the increasing subsequence and k represents the index of its termination
		int[] M = new int[N + 1];

		int L = 0, low, high, newLow, mid;

		for(int i = 0; i <= N-1; i++) {
			//Binary search for the largest positive j ≤ L such that X[M[j]] < X[i]
			low = 1;
			high = L;

			while(low <= high) {
				mid = new Double(Math.ceil((low + high) / 2)).intValue();

				if(X[M[mid]] < X[i])
					low = mid + 1;
				else
					high = mid - 1;
			}

			//After searching, low is 1 greater than the length of the longest prefix of X[i]
			newLow = low;

			//The predecessor of X[i] is the last index of the subsequence of length newLow-1
			P[i] = M[newLow - 1];
			M[newLow] = i;

			//If we found a subsequence longer than any we've found yet, update L
			if(newLow > L)
				L = newLow;
		}

		//Reconstruct the longest increasing subsequence
		int[] S = new int[L];
		int k = M[L];

		for(int i = L-1; i >= 0; i--) {
			S[i] = X[k];
			k = P[k];
		}

		return S;
	}

	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter array of elements");

		String[] numbers = sc.nextLine().split(" ");
		int length = numbers.length;
		int[] array = new int[length];

		for(int i = 0; i < length; i++) {
			array[i] = Integer.parseInt(numbers[i]);
		}

		int[] seq = computeLIS(array, length);

		System.out.print("LIS is ");
		for(int i : seq)
			System.out.print(i + " ");
		System.out.println("and LIS length is " + seq.length);
	}
}

/* 10 22 33 51 21 25 39 42 43 44 54 65 76 87
0 8 4 12 2 10 6 14 1 9 5 13 3 11 7 15 */
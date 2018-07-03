/*write a function threeSum(int[] a) that returns true if there exist three integers in a that sum to zero and false otherwise.
	@ author Hong Seok Jang
*/
public class threeSum {
	private static boolean isThreeSum(int[] a) {
		for (int i = 0; i < a.length; i ++)
			for (int j = 0; j < a.length; j ++)
				for (int k = 0; k < a.length; k ++)
					if (a[i] + a[j] + a[k] == 0)
		  			return true;
		return false;
	}
	
	public static void main(String[] arg) {
		int[] a = {-6, 2, 4};
		int[] b = {-6, 2, 5};
		int[] c = {8, 2, -1, 15};
		int[] d = {8, 2, -1, -1, 15};
		int[] e = {5, 1, 0, 3, 6};
		System.out.println(isThreeSum(a));
		System.out.println(isThreeSum(b));
		System.out.println(isThreeSum(c));
		System.out.println(isThreeSum(d));
		System.out.println(isThreeSum(e));
	}
} 
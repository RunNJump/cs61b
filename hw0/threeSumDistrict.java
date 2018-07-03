/* Write a function threeSum(int[] a) that returns true if there exist three integers in a that sum to zero and false otherwise.
	 but with the constraint that each number can be used only once.
	 @author Hong Seok Jang
*/

public class threeSumDistrict {
	private static boolean isThreeSumDistrict(int[] a) {
		for (int i = 0; i < a.length; i++)
			for (int j = i + 1; j < a.length; j++)
				for (int k = j + 1; k < a.length; k++)
					if (a[i] + a[j] + a[k] == 0)
						return true;
		return false;
	}

	public static void main (String[] args) {
		int[] a = {-6, 2, 4};
		int[] b = {-6, 2, 5};
		int[] c = {8, 2, -1, 15};
		int[] d = {8, 2, -1, -1, 15};
		int[] e = {5, 1, 0, 3, 6};
		System.out.println(isThreeSumDistrict(a));
		System.out.println(isThreeSumDistrict(b));
		System.out.println(isThreeSumDistrict(c));
		System.out.println(isThreeSumDistrict(d));
		System.out.println(isThreeSumDistrict(e));
	}
}
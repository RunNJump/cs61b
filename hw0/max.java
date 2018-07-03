/* Write a function max(int[] a) that returns the maximum value of the an int array.
 @author Hong Seok Jang
 */
public class max {
	public static int maxValue(int[] a) {
		int answer = a[0];
		for(int i = 1; i < a.length; i++) {
			if (a[i] > answer)
				answer = a[i];
		}
		return answer;
	}
	
	public static void main(String[] args) {
		int[] a = {1, 2, 3, 4};
		System.out.println(maxValue(a));
	}
}
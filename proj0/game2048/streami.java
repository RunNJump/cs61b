import java.lang.Math;

public class streami {
	public boolean task {
		int left = 0;
		int pass = 0;
		int right = 0;
		int leftBound = 0;
		int rightBound = 3;

		/* 
		tripleTree is similar to binary tree but it has tree children.
											012
				345 						678 						91011
		121314 151617 181920 		212223 242526 272829		303132 333435 363738
		*/
		List<Integer> tripleTree = new ArrayList();
		

		/*	Execute the first element in Weights;
			First one(left) is the case that I put the element in left side
			Second one(pass) is the case that I didn't put the element either side
			Third one(right) is the case that I put the element in right side
		*/
		tripleTree.add(Weights[0]);
		tripleTree.add(0);
		tripleTree.add(-Weights[0]);

		for (int i = 1; i < Weights.length(); i++) {
			leftBound = rightBound;
			rightBound = leftBound + pow(3,(i+1));
			for (int j = leftBound; j < rightBound; j++) {
				left = tripleTree[getParent(j)] + Weight[i];
				if (left == Object){
					return true;
				}
				tripleTree.add(left);
				pass = tripleTree[getParent(j)];
				if (pass == Object){
					return true;
				}
				tripleTree.add(pass);
				right = tripleTree[getParent(j)] - Weight[i]
				if (right == Object){
					return true;
				}
				tripleTree.add(right);
			}
		}
		return false;
	}

	public int getParent(int index) {
		return index/3 - 1
	}
}


package common;

import java.util.Random;

public class SweetArray<ArrayType>{
	// Implementing Fisher–Yates shuffle
	  public void shuffleArray(ArrayType[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      ArrayType a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }

}

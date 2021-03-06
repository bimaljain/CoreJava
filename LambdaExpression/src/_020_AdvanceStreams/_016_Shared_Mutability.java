package _020_AdvanceStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _016_Shared_Mutability {

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

		//double the even values and put that into a list.

		//wrong way to do this.
		List<Integer> doubleOfEven = new ArrayList<>();

		numbers.parallelStream()
		.filter(e -> e % 2 == 0)
		.map(e -> e * 2)
		.forEach(e -> doubleOfEven.add(e)); //Don't do this.
		//mutability is OK, sharing is nice, but shared mutability is devil's work

		System.out.println("Shared Mutability: " + doubleOfEven); 

		List<Integer> doubleOfEven2 =
				numbers.parallelStream()
				.filter(e -> e % 2 == 0)
				.map(e -> e * 2)
				.collect(Collectors.toList());
		System.out.println(doubleOfEven2);
	}
}

/*
 One possible output:
 Shared Mutability: [null, 8, 4, 8]
 [4, 8, 4, 8]
 
 note that during shared mutability, we get inconsistent result, whereas collectors will take care of this.
 */

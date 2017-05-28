package com.oblac.jmh;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generator of various data required for benchmark testing.
 */
public class DataGenerator {

	private static final Random RND = new Random();

	/**
	 * Returns list of random integers with specified size.
	 * Values are uniformly distributed.
	 */
	public static ArrayList<Integer> listOfRandomIntegers(int size) {
		ArrayList<Integer> list = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			list.add(RND.nextInt());
		}

		return list;
	}

	/**
	 * Returns array of random uniformly distributed integers with specified size.
	 */
	public static int[] arrayOfRandomInts(int size) {
		int[] array = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = RND.nextInt();
		}

		return array;
	}

	/**
	 * Returns array of random uniformly distributed integers with specified size.
	 */
	public static int[] arrayOfGaussiandRandomInts(int size) {
		int[] array = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = (int) (RND.nextGaussian() * Integer.MAX_VALUE);
		}

		return array;
	}
}
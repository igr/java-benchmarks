package com.oblac.jmh.core;

import java.util.Random;

public class MatrixMath {

	private final double[][] matrix;

	/**
	 * Initializes a square matrix, where each dimension is equal to the specified size.
	 *
	 * @param size height (and also width) of the generated 2-D matrix
	 */
	public MatrixMath(int size) {
		matrix = new double[size][];
		Random rand = new Random();
		for (int r = size - 1; r >= 0; r--) {
			matrix[r] = new double[size];
			for (int c = size - 1; c >= 0; c--) {
				matrix[r][c] = rand.nextDouble();
			}
		}
	}

	/**
	 * This version contains a simple nested loop and an innermost conditional.
	 *
	 * @return sum of the element values appearing in the matrix's upper triangle
	 */
	double sumUpperTriangle() {
		int size = matrix.length;
		double sum = 0.0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i <= j) {
					sum += matrix[i][j];
				}
			}
		}
		return sum;
	}

	/**
	 * This version eliminates the nested {@code if} statement, instead relying on
	 * a different loop condition.
	 *
	 * @return sum of the element values appearing in the matrix's upper triangle
	 */
	double sumUpperTriangleCMA() {
		int size = matrix.length;
		double sum = 0.0;

		for (int j = 0; j < size; j++) {
			for (int i = 0; i <= j; i++) {
				sum += matrix[i][j];
			}
		}
		return sum;
	}

	/**
	 * This version eliminates the nested {@code if} statement, but keeps the original
	 * loop conditions; however, the initialization of the inner loop variable has changed.
	 *
	 * @return sum of the element values appearing in the matrix's upper triangle
	 */
	double sumUpperTriangleRMA() {
		int size = matrix.length;
		double sum = 0.0;

		for (int i = 0; i < size; i++) {
			for (int j = i; j < size; j++) {
				sum += matrix[i][j];
			}
		}
		return sum;
	}
}

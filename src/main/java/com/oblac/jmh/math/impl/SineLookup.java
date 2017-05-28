package com.oblac.jmh.math.impl;

/**
 * Sinus lookup table.
 */
public class SineLookup {

	private final double[] table = new double[180];

	public SineLookup() {
		for (int i = 0; i < table.length; i++) {
			table[i] = Math.sin(Math.toRadians(i));
		}
	}

	public double sin(double degree) {
		int d = (int) degree;

		d = d % 180;

		if (d < 0) {
			return -table[d];
		}
		return table[d];
	}

}
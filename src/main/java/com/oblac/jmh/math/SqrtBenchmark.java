package com.oblac.jmh.math;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * Benchmark                   Mode  Cnt       Score      Error  Units
 * SqrtBenchmark.fastSqrt     thrpt   25  110687.259 ± 1121.736  ops/s
 * SqrtBenchmark.fastSqrt2    thrpt   25  108794.076 ±   88.307  ops/s
 * SqrtBenchmark.fastSqrtAlt  thrpt   25  112528.988 ±   65.859  ops/s
 * SqrtBenchmark.math         thrpt   25   67287.027 ±   97.051  ops/s
 */
public class SqrtBenchmark {
	public static double fastSqrt(double d) {
		return java.lang.Double.longBitsToDouble((java.lang.Double.doubleToLongBits(d) - (1L << 52) >> 1) + (1L << 61));
	}

	public static double fastSqrt2(double d) {
		double sqrt = fastSqrt(d);
		return (sqrt + d / sqrt) / 2.0;
	}
	public static double fastSqrtAlt(double number) {
		if (number < 100000) {
			return java.lang.Double.longBitsToDouble(((java.lang.Double.doubleToRawLongBits(number) >> 32) + 1072632448) << 31);
		} else {
			return java.lang.Double.longBitsToDouble(((java.lang.Double.doubleToRawLongBits(number) >> 32) + 1072679338) << 31);
		}
	}

	@Benchmark
	public double math() {
		double sum = 0;
		for (double d = 1000; d < 11000; d += 1) {
			sum += Math.sqrt(d);
		}
		return sum;
	}
	@Benchmark
	public double fastSqrt() {
		double sum = 0;
		for (double d = 1000; d < 11000; d += 1) {
			sum += fastSqrt(d);
		}
		return sum;
	}

	@Benchmark
	public double fastSqrt2() {
		double sum = 0;
		for (double d = 1000; d < 11000; d += 1) {
			sum += fastSqrt2(d);
		}
		return sum;
	}

	@Benchmark
	public double fastSqrtAlt() {
		double sum = 0;
		for (double d = 1000; d < 11000; d += 1) {
			sum += fastSqrtAlt(d);
		}
		return sum;
	}


}

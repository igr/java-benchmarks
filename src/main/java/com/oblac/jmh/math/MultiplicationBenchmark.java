package com.oblac.jmh.math;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MultiplicationBenchmark {

	@Benchmark
	public void test10() {
		multiplication(10);
	}

	@Benchmark
	public void test20() {
		multiplication(20);
	}

	@Benchmark
	public void test10BigInteger() {
		multiplicationWithBigInteger(10);
	}

	@Benchmark
	public void test20BigInteger() {
		multiplicationWithBigInteger(20);
	}

	public long multiplication(int n) {
		long product = 1;
		for (int i = 2; i < n + 1; i++) {
			product *= i;
		}
		return product;
	}

	public BigInteger multiplicationWithBigInteger(int n) {
		BigInteger product = new BigInteger("1");
		for (int i = 2; i < n + 1; i++) {
			product = product.multiply(new BigInteger(String.valueOf(i)));
		}
		return product;
	}
}
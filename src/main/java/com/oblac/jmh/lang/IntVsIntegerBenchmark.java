package com.oblac.jmh.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 IntVsIntegerBenchmark.sumA   avgt   20   21.256 ± 0.737  ns/op
 IntVsIntegerBenchmark.sumA2  avgt   20  186.875 ± 6.625  ns/op
 IntVsIntegerBenchmark.sumL   avgt   20   78.255 ± 1.646  ns/op
 IntVsIntegerBenchmark.sumL2  avgt   20  111.339 ± 1.025  ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Thread)
public class IntVsIntegerBenchmark {
	public int[] a = new int[100];
	public List<Integer> l = Arrays.stream(a).boxed().collect(Collectors.toList());

	@Benchmark
	public int sumA() {
		int sum = 0;
		for (int i : a) {
			sum += i;
		}
		return sum;
	}

	@Benchmark
	public int sumA2() {
		return Arrays.stream(a).sum();
	}

	@Benchmark
	public int sumL() {
		int sum = 0;
		for (int i : l) {
			sum += i;
		}
		return sum;
	}

	@Benchmark
	public int sumL2() {
		return l.stream().mapToInt(i -> i).sum();
	}


}

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

import java.util.concurrent.TimeUnit;

/**
 Benchmark                Mode  Cnt  Score   Error  Units
 NewBenchmark.allocArray  avgt   20  3.261 ± 0.026  ns/op
 NewBenchmark.createFoo   avgt   20  2.927 ± 0.065  ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Thread)
public class NewBenchmark {

	public static class Foo {
		private final int value;
		public Foo(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}

	@Benchmark
	public Foo createFoo() {
		return new Foo(1);
	}

	@Benchmark
	public int[] allocArray() {
		final int[] array = new int[1];
		array[0] = 1;
		return array;
	}

}

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
 NewBenchmark.allocArray    avgt   20  3.282 ± 0.060  ns/op
 NewBenchmark.createBigFoo  avgt   20  4.004 ± 0.041  ns/op
 NewBenchmark.createFoo     avgt   20  2.896 ± 0.045  ns/op
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

	public static class BigFoo {
		private final int value;
		private int a;
		private String b;
		private Double c;
		private Foo d;
		private Long e;
		public BigFoo(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

		public Double getC() {
			return c;
		}

		public void setC(Double c) {
			this.c = c;
		}

		public Foo getD() {
			return d;
		}

		public void setD(Foo d) {
			this.d = d;
		}

		public Long getE() {
			return e;
		}

		public void setE(Long e) {
			this.e = e;
		}
	}

	@Benchmark
	public Foo createFoo() {
		return new Foo(1);
	}

	@Benchmark
	public BigFoo createBigFoo() {
		return new BigFoo(1);
	}

	@Benchmark
	public int[] allocArray() {
		final int[] array = new int[1];
		array[0] = 1;
		return array;
	}

}

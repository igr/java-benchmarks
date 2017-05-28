package com.oblac.jmh;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Simple set of scenarios and examples used in presentation. Most of them
 * show bad benchmarks and are related to special context of the talk.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class BenchmarksGoneBad {

	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();

		for (int i = 0; i < 1_000_000; i++) {
			someMethodWhichYouWantToProfile();
		}

		final long elapsedTime = System.currentTimeMillis() - startTime;

		System.out.println("Elapsed " + elapsedTime + "ms.");
	}

	public static BigDecimal someMethodWhichYouWantToProfile() {
		return new BigDecimal(Math.log(Math.PI)).multiply(new BigDecimal(new Random().nextDouble()));
	}

	// ----------------------------------------------------------------

	public class Scenario1 {
		double x = 42;

		public void emptyBenchmark() {
		}

		public void benchmarkBad() {
			Math.log(x);
		}

		public double benchmarkGood() {
			return Math.log(x);
		}
	}

	public class Scenario2 {

		public void emptyBenchmark() {
		}

		public double benchmarkBad() {
			return Math.log(42);
		}

		private double x = 42;
		public double benchmarkGood() {
			return Math.log(x);
		}
	}

	public class Scenario3 {
		final int N = 100;

		public int test() { return doWork(N);}

		int x = 1, y = 2;
		private int doWork(int reps) {
			int s = 0;
			for (int i = 0; i < reps; i++) {
				s += (x + y);
			}
			return s;
		}
	}


	interface M { default void inc() {} }
	class M1 implements M {}
	class M2 implements M {}

	public class Scenario4 {
		M m1 = new M1();
		M m2 = new M2();

		public void testM1() {test(m1);}
		public void testM2() {test(m2);}

		private void test(M m) {
			for (int i = 0; i < 100; i++) {m.inc();}
		}
	}

	public class Scenario5 {
		public int sum1(int[] a) {
			int sum = 0;
			for (int x : a) {
				if (x < 0) {
					sum -= x;
				} else {
					sum += x;
				}
			}
			return sum;
		}
		public int sum2(int[] a) {
			int sum = 0;
			for (int x : a) {
				sum += Math.abs(x);
			}
			return sum;
		}
	}
}

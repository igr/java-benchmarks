package com.oblac.jmh.collection;

import jodd.util.buffer.FastIntBuffer;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 MapVsLinkedBenchmark.llist       1  thrpt    3  36333.550 ± 3003.798  ops/ms
 MapVsLinkedBenchmark.llist       2  thrpt    3  19386.614 ± 1704.854  ops/ms
 MapVsLinkedBenchmark.llist       3  thrpt    3  12542.519 ±  168.193  ops/ms
 MapVsLinkedBenchmark.llist       5  thrpt    3   6823.836 ± 1663.901  ops/ms
 MapVsLinkedBenchmark.llist       8  thrpt    3   3699.939 ±  201.485  ops/ms
 MapVsLinkedBenchmark.llist      13  thrpt    3   2026.850 ±  150.745  ops/ms
 MapVsLinkedBenchmark.llist      21  thrpt    3    996.273 ±   54.840  ops/ms
 MapVsLinkedBenchmark.llist      34  thrpt    3    420.455 ±   14.035  ops/ms
 MapVsLinkedBenchmark.map         1  thrpt    3   2527.335 ±  321.049  ops/ms
 MapVsLinkedBenchmark.map         2  thrpt    3   1298.646 ±  339.733  ops/ms
 MapVsLinkedBenchmark.map         3  thrpt    3    887.028 ±   83.234  ops/ms
 MapVsLinkedBenchmark.map         5  thrpt    3    537.775 ±  152.413  ops/ms
 MapVsLinkedBenchmark.map         8  thrpt    3    345.260 ±    8.706  ops/ms
 MapVsLinkedBenchmark.map        13  thrpt    3    211.488 ±   53.404  ops/ms
 MapVsLinkedBenchmark.map        21  thrpt    3    128.404 ±   19.039  ops/ms
 MapVsLinkedBenchmark.map        34  thrpt    3     80.179 ±   10.308  ops/ms
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 3)
@State(Scope.Thread)
@Fork(1)
public class MapVsLinkedBenchmark {

	static class Val {
		String name;
		Val next;
		int[] arr;
	}


	@Param({"1", "2", "3", "5", "8", "13", "21", "34"})
	public int size;

	@Benchmark
	public int map() {
		Map<String, FastIntBuffer> map = new HashMap<>();

		for (int i = 0; i < size; i++) {
			FastIntBuffer fib = new FastIntBuffer();
			fib.append(i);
			map.put("na" + i, fib);
		}

		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += map.get("na" + i).get(0);
		}
		return sum;
	}

	@Benchmark
	public int llist() {
		Val root = new Val();
		Val val = root;

		for (int i = 0; i < size; i++) {
			val.arr = new int[] {i};
			val.name = "na" + i;
			Val next = new Val();
			val.next = next;
			val = next;
		}

		int sum = 0;
		for (int i = 0; i < size; i++) {
			Val x = root;
			String lookup = "na" + i;

			while (x != null) {
				if (x.name.equals(lookup)) {
					sum += x.arr[0];
					break;
				}
				x = x.next;
			}
		}
		return sum;
	}


}

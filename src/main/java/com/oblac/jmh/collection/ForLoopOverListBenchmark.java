package com.oblac.jmh.collection;

import com.oblac.jmh.DataGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark for different ways of iterating the List.
 * <ul>
 *     <li>Using Enhanced for loop ('for in')</li>
 *     <li>Common index loop</li>
 *     <li>Optimized index loop, as IntelliJ Idea suggest</li>
 * </ul>
 *
 * <pre>

 # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/bin/java
 # VM options: -Dfile.encoding=UTF-8 -Duser.country=US -Duser.language=en -Duser.variant

 Benchmark                                                 (size)   Mode  Samples       Score       Error   Units
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                     1  thrpt       20  403306.517 ± 24245.930  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                    10  thrpt       20  159097.023 ±  1722.893  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                   100  thrpt       20   24753.593 ±   474.777  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                  1000  thrpt       20    2623.022 ±    66.069  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                 10000  thrpt       20     231.745 ±     4.344  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop                  1  thrpt       20  543487.553 ± 26839.325  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop                 10  thrpt       20  170378.505 ±  1989.683  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop                100  thrpt       20   26318.742 ±   304.622  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop               1000  thrpt       20    2755.581 ±    40.017  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop              10000  thrpt       20     240.437 ±     3.641  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop         1  thrpt       20  507417.348 ± 11280.308  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop        10  thrpt       20  174256.254 ±  2394.436  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop       100  thrpt       20   26924.880 ±   261.776  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop      1000  thrpt       20    2800.936 ±    25.993  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop     10000  thrpt       20     244.841 ±     2.431  ops/ms

 * </pre>
 * <p>
 * Conclusion: Enhanced loop if as fast as indexed loop. Actually, for smaller loops (<10 elements),
 * it is even faster!
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Threads(2)
@Fork(2)
@State(Scope.Thread)
@SuppressWarnings({"UnnecessaryUnboxing", "unused"})
public class ForLoopOverListBenchmark {

	@Param({"1", "10", "100", "1000", "10000"})
	private int size;

	private ArrayList<Integer> arrayList;

	@Setup
	public void setup() {
		arrayList = DataGenerator.listOfRandomIntegers(size);
	}

	@Benchmark
	public void forInLoop(Blackhole bh) {
		int count = 0;
		for (Integer integer : arrayList) {
			count += integer.intValue();
		}
		bh.consume(count);
	}

	@Benchmark
	public void forIndexLoop(Blackhole bh) {
		int count = 0;
		for (int i = 0; i < arrayList.size(); i++) {
			Integer integer = arrayList.get(i);
			count += integer.intValue();
		}
		bh.consume(count);
	}

	@Benchmark
	public void forIndexOptimizedLoop(Blackhole bh) {
		int count = 0;
		for (int i = 0, arrayListSize = arrayList.size(); i < arrayListSize; i++) {
			Integer integer = arrayList.get(i);
			count += integer.intValue();
		}
		bh.consume(count);
	}

}
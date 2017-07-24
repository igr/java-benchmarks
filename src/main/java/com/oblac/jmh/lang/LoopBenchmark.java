package com.oblac.jmh.lang;

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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


/**
 *
 Benchmark                         (size)  Mode  Cnt      Score       Error  Units
 LoopBenchmark.goodOldLoop             32  avgt    5    120.943 ±    12.676  ns/op
 LoopBenchmark.goodOldLoop           1024  avgt    5   7360.246 ± 13051.012  ns/op
 LoopBenchmark.goodOldLoop          32768  avgt    5  95615.585 ±  5420.627  ns/op
 LoopBenchmark.goodOldLoopReturns      32  avgt    5     81.452 ±     6.985  ns/op
 LoopBenchmark.goodOldLoopReturns    1024  avgt    5   2576.298 ±   248.882  ns/op
 LoopBenchmark.goodOldLoopReturns   32768  avgt    5  83029.105 ±  4684.280  ns/op
 LoopBenchmark.sumOldLoop              32  avgt    5     13.639 ±     0.514  ns/op
 LoopBenchmark.sumOldLoop            1024  avgt    5    319.638 ±     5.151  ns/op
 LoopBenchmark.sumOldLoop           32768  avgt    5  14329.742 ± 16648.400  ns/op
 LoopBenchmark.sumSweetLoop            32  avgt    5     14.244 ±     1.711  ns/op
 LoopBenchmark.sumSweetLoop          1024  avgt    5    322.782 ±    24.086  ns/op
 LoopBenchmark.sumSweetLoop         32768  avgt    5   9889.917 ±  1022.803  ns/op
 LoopBenchmark.sweetLoop               32  avgt    5     80.421 ±     3.711  ns/op
 LoopBenchmark.sweetLoop             1024  avgt    5   2528.270 ±   160.083  ns/op
 LoopBenchmark.sweetLoop            32768  avgt    5  82227.780 ±  2351.975  ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class LoopBenchmark {

	@Param({ "32", "1024", "32768" })
	int size;

	byte[] bunn;

	@Setup
	public void prepare() {
		bunn = new byte[size];
	}

	@Benchmark
	public void goodOldLoop(Blackhole fox) {
		for (int y = 0; y < bunn.length; y++) { // good old C style for (the win?)
			fox.consume(bunn[y]);
		}
	}

	@Benchmark
	public void sweetLoop(Blackhole fox) {
		for (byte bunny : bunn) {				// syntactic sugar loop goodness
			fox.consume(bunny);
		}
	}

	@Benchmark
	public void goodOldLoopReturns(Blackhole fox) {
		byte[] sunn = bunn; 					// make a local copy of the field
		for (int y = 0; y < sunn.length; y++) {
			fox.consume(sunn[y]);
		}
	}

	@Benchmark
	public int sumOldLoop() {
		int sum = 0;
		for (int y = 0; y < bunn.length; y++) {
			sum += bunn[y];
		}
		return sum;
	}

	@Benchmark
	public int sumSweetLoop() {
		int sum = 0;
		for (byte bunny : bunn) {
			sum += bunny;
		}
		return sum;
	}

}
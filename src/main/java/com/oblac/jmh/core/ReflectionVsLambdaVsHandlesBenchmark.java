package com.oblac.jmh.core;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.*;

/**
 * Is it still true that <em>Java reflection is slow?</em>
 * Here are a few different ways to invoke the same method (happy AND exceptional paths).
 *
 * <pre>
 * # Intel(R) Core(TM) i7-3520M CPU @ 2.90GHz
 * # Linux 4.5.7-202.fc23.x86_64 #1 SMP x86_64 GNU/Linux
 * # JMH 1.12
 * # VM version: JDK 1.8.0_51, VM 25.51-b03
 * # Warmup: 3 iterations, 1 s each
 * # Measurement: 5 iterations, 1 s each
 * # Threads: 1 threads, will synchronize iterations
 * # Run complete. Total time: 00:12:29
 *
 * Benchmark                                          Mode  Cnt     Score    Error  Units
 * ReflectionVsLambdaVsHandles.direct                 avgt   50    43.870 ±  1.689  ns/op
 * ReflectionVsLambdaVsHandles.lambda                 avgt   50    44.423 ±  1.568  ns/op
 * ReflectionVsLambdaVsHandles.methodHandle           avgt   50    48.165 ±  1.479  ns/op
 * ReflectionVsLambdaVsHandles.reflectionNaive        avgt   50   266.993 ± 11.096  ns/op
 * ReflectionVsLambdaVsHandles.reflectionReuse        avgt   50    48.999 ±  1.388  ns/op
 *
 * ReflectionVsLambdaVsHandles.exceptionDirect        avgt   50     3.208 ±  0.095  ns/op
 * ReflectionVsLambdaVsHandles.exceptionLambda        avgt   50     3.732 ±  0.040  ns/op
 * ReflectionVsLambdaVsHandles.exceptionMethodHandle  avgt   50   291.574 ±  6.474  ns/op
 * ReflectionVsLambdaVsHandles.exceptionReflection    avgt   50  1406.453 ± 81.079  ns/op
 * </pre>
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class ReflectionVsLambdaVsHandlesBenchmark {

	private Object nil = null;
	private Object val = "Supercalifragilisticexpialidocious";
	private Function<Object, String> aLambda = s -> toHashCodeString(s);
	private Method aMethod;
	private MethodHandle aMethodHandle;

	@Setup
	public void setUp() throws ReflectiveOperationException {
		aMethod = getReflectiveMethod();
		aMethodHandle = MethodHandles.lookup().unreflect(aMethod);
	}

	private Method getReflectiveMethod() throws ReflectiveOperationException {
		return this.getClass().getMethod("toHashCodeString", Object.class);
	}

	/**
	 * Demo function invoked in a variety of ways.
	 *
	 * @param obj any non-null object
	 * @return string representation of the {@code obj} hash code
	 * @throws NullPointerException if {@code obj == null}
	 */
	public static String toHashCodeString(Object obj) {
		return Integer.toString(obj.hashCode());
	}

	@Benchmark
	public String direct() {
		return toHashCodeString(val);
	}

	@Benchmark
	public String reflectionNaive() throws ReflectiveOperationException {
		Method localMethod = getReflectiveMethod();
		return (String) localMethod.invoke(null, val);
	}

	@Benchmark
	public String reflectionReuse() throws ReflectiveOperationException {
		return (String) aMethod.invoke(null, val);
	}

	@Benchmark
	public String lambda() {
		return aLambda.apply(val);
	}

	@Benchmark
	public String methodHandle() throws Throwable {
		return (String) aMethodHandle.invokeExact(val);
	}

	@Benchmark
	public Object exceptionDirect() {
		try {
			return toHashCodeString(nil);
		}
		catch (RuntimeException err) {
			return err;
		}
	}

	@Benchmark
	public Object exceptionReflection() {
		try {
			return aMethod.invoke(null, nil);
		}
		catch (ReflectiveOperationException err) {
			return err;
		}
	}

	@Benchmark
	public Object exceptionLambda() {
		try {
			return aLambda.apply(nil);
		}
		catch (RuntimeException err) {
			return err;
		}
	}

	@Benchmark
	public Object exceptionMethodHandle() {
		try {
			return (String) aMethodHandle.invokeExact(nil);
		}
		catch (Throwable err) {
			return err;
		}
	}
}

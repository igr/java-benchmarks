package com.oblac.jmh.reflect;

import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 As per: https://gist.github.com/raphw/881e1745996f9d314ab0

 b.LookupBenchmark.reflection                        avgt   20  151,434 ±  15,701  ns/op

 b.LookupBenchmark.handle                            avgt   20  913,461 ± 144,966  ns/op
 b.LookupBenchmark.handlePreLookedUp                 avgt   20  784,532 ± 105,980  ns/op
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LookupBenchmark {

	private String name = "method";

	private MethodHandles.Lookup lookup;

	private MethodType methodType;

	private Class<?> returnType = void.class, declaringType = LookupBenchmark.class;

	void method() {
		/* empty */
	}

	@Setup
	public void setUp() {
		lookup = MethodHandles.lookup();
		methodType = MethodType.methodType(void.class);
	}

	@Benchmark
	public Method reflection() throws Exception {
		return declaringType.getDeclaredMethod(name);
	}

	@Benchmark
	public MethodHandle handle() throws Exception {
		return MethodHandles.lookup().findVirtual(declaringType, name, MethodType.methodType(returnType));
	}

	@Benchmark
	public MethodHandle handlePreLookedUp() throws Exception {
		return lookup.findVirtual(declaringType, name, methodType);
	}
}
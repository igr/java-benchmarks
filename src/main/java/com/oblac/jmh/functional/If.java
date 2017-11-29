package com.oblac.jmh.functional;

public class If {

	private final boolean bool;

	If(boolean bool) {
		this.bool = bool;
	}

	public static If inCase(boolean bool) {
		return new If(bool);
	}

	public If then(Runnable runnable) {
		if (bool) {
			runnable.run();
		}
		return this;
	}
	public If otherwise(Runnable runnable) {
		if (!bool) {
			runnable.run();
		}
		return this;
	}

}

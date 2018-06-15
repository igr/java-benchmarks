package com.oblac.jmh.array;

// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.

import java.util.Arrays;

/**
 * Fast, fast <code>byte</code> buffer.
 * This buffer implementation does not store all data
 * in single array, but in array of chunks.
 */
public class FastByteBuffer {

	private byte[] buffer;
	private byte[][] allBuffers;
	private int bufferIndex;
	private int offset;
	private int blength;
	private int threshold = 0;

	/**
	 * Creates a new {@code byte} buffer. The buffer capacity is
	 * initially 64 bytes, though its size increases if necessary.
	 */
	public FastByteBuffer() {
		buffer = new byte[64];
	}


	private void growFor(final int delta) {
		int newBufferSize = buffer.length << 1;

		if (newBufferSize < delta) {
			newBufferSize = delta + 512;
		}

		if (threshold < 21) {
			threshold++;
			buffer = Arrays.copyOf(buffer, newBufferSize);
			return;
		}
		if (allBuffers == null) {
			allBuffers = new byte[8][];
			allBuffers[0] = buffer = Arrays.copyOf(buffer, newBufferSize);
			return;
		}

		offset = 0;
		blength += buffer.length;
		bufferIndex++;
		if (bufferIndex >= allBuffers.length) {
			allBuffers = Arrays.copyOf(allBuffers, allBuffers.length << 1);
		}
		allBuffers[bufferIndex] = buffer = new byte[newBufferSize];
	}

	/**
	 * Appends single <code>byte</code> to buffer.
	 */
	public FastByteBuffer append(final byte element) {
		if (offset == buffer.length) {
			growFor(1);
		}

		buffer[offset++] = element;

		return this;
	}

	/**
	 * Creates <code>byte</code> array from buffered content.
	 */
	public byte[] toArray() {
		if (bufferIndex == 0) {
			return Arrays.copyOf(buffer, offset);
		}

		int pos = 0;
		final byte[] array = new byte[blength + offset];

		for (int i = 0; i < bufferIndex; i++) {
			final int len = allBuffers[i].length;
			System.arraycopy(allBuffers[i], 0, array, pos, len);
			pos += len;
		}

		System.arraycopy(buffer, 0, array, pos, offset);

		return array;
	}

}
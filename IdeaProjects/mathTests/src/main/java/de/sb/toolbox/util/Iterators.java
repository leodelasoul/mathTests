package de.sb.toolbox.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import de.sb.toolbox.Copyright;


/**
 * This facade provides additional operations for iterators.
 */
@Copyright(year=2016, holders="Sascha Baumeister")
public class Iterators {

	/**
	 * Returns a stream adapter for the given iterator.
	 * @param iterator the iterator
	 * @return the stream adapter created
	 * @throws NullPointerException if the given argument is {@code null}
	 */
	static public <T> Stream<T> newStream (final Iterator<? extends T> iterator) throws NullPointerException {
		final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
		return StreamSupport.stream(spliterator, false);
	}


	/**
	 * Returns the given iterator's elements as a list.
	 * @param iterator the iterator
	 * @return the array list created
	 * @throws NullPointerException if the given argument is {@code null}
	 */
	static public <T> List<T> newList (final Iterator<? extends T> iterator) {
		final List<T> list = new ArrayList<>();
		while (iterator.hasNext()) list.add(iterator.next());
		return list;
	}


	/**
	 * Returns the given iterator's elements as an array of the given component type.
	 * @param iterator the iterator
	 * @param componentType the resulting array's component type
	 * @return the array created
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayStoreException if any iterator element is incompatible with the given component type
	 */
	static public <T> T[] newArray (final Class<T> componentType, final Iterator<? extends T> iterator) throws NullPointerException, ArrayStoreException {
		final List<? extends T> list = newList(iterator);

		@SuppressWarnings("unchecked")
		final T[] array = list.toArray((T[]) Array.newInstance(componentType, list.size()));
		return array;
	}


	/**
	 * Returns the given array wrapped into an unmodifiable list iterator.
	 * @param array the array
	 * @return the iterator adapter created
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T> ListIterator<T> newIterator (final T[] array) {
		return new ListIterator<T>() {
			private int position = -1;

			public boolean hasNext () {
				return this.position + 1 < array.length;
			}

			public int nextIndex () {
				return this.position + 1;
			}

			public T next () throws NoSuchElementException {
				if (this.hasNext ()) return array[++this.position];
				throw new NoSuchElementException();
			}

			public int previousIndex () {
				return Math.max(this.position - 1, -1);
			}

			public boolean hasPrevious () {
				return this.position > 0;
			}

			public T previous () throws NoSuchElementException {
				if (this.hasPrevious()) return array[--this.position];
				throw new NoSuchElementException();
			}

			public void remove () throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}

			public void set (T element) throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}

			public void add (final T element) throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}		
		};
	}


	/**
	 * Returns the given array wrapped into an unmodifiable int iterator.
	 * @param array the array
	 * @return the iterator adapter created
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public PrimitiveIterator.OfInt newIterator (final int[] array) {
		return new PrimitiveIterator.OfInt() {
			private int position;

			public boolean hasNext () {
				return this.position != array.length;
			}

			public int nextInt () {
				if (this.hasNext ()) return array[this.position++];
				throw new NoSuchElementException();
			}
		};
	}


	/**
	 * Returns the given array wrapped into an unmodifiable long iterator.
	 * @param array the array
	 * @return the iterator adapter created
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public PrimitiveIterator.OfLong newIterator (final long[] array) {
		return new PrimitiveIterator.OfLong() {
			private int position;

			public boolean hasNext () {
				return this.position != array.length;
			}

			public long nextLong () {
				if (this.hasNext ()) return array[this.position++];
				throw new NoSuchElementException();
			}
		};
	}


	/**
	 * Returns the given array wrapped into an unmodifiable double iterator.
	 * @param array the array
	 * @return the iterator adapter created
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public PrimitiveIterator.OfDouble newIterator (final double[] array) {
		return new PrimitiveIterator.OfDouble() {
			private int position;

			public boolean hasNext () {
				return this.position != array.length;
			}

			public double nextDouble () {
				if (this.hasNext ()) return array[this.position++];
				throw new NoSuchElementException();
			}
		};
	}
}
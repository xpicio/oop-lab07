package it.unibo.inner.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterablePlainImpl<T> implements IterableWithPolicy<T> {
    private final T[] items;

    public IterablePlainImpl(final T[] items) {
        this.items = Arrays.copyOf(items, items.length);
    }

    private static class InternalIterator<T> implements Iterator<T> {
        private final T[] items;
        private int currentItem;

        public InternalIterator(final T[] items) {
            this.items = items;
            this.currentItem = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currentItem < this.items.length;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("There is not any more elements to iterate");
            }

            return this.items[this.currentItem++];
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new IterablePlainImpl.InternalIterator<T>(this.items);
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIterationPolicy'");
    }
}

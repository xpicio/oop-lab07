package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final T[] items;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] items) {
        this.items = Arrays.copyOf(items, items.length);
    }

    public IterableWithPolicyImpl(final T[] items, final Predicate<T> filter) {
        this(items);
        this.filter = filter;
    }

    private List<T> filteredItems(final T[] items, final Predicate<T> filter) {
        final List<T> filteredItems = new ArrayList<>();

        for (T item : items) {
            if (filter == null || filter.test(item)) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }

    private static class InternalIterator<T> implements Iterator<T> {
        private final List<T> items;
        private int currentItem;

        public InternalIterator(final List<T> items) {
            this.items = items;
            this.currentItem = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currentItem < this.items.size();
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("There is not any more elements to iterate");
            }

            return this.items.get(this.currentItem++);
        }

    }

    @Override
    public Iterator<T> iterator() {
        List<T> filteredItems = this.filteredItems(this.items, this.filter);

        return new IterableWithPolicyImpl.InternalIterator<T>(filteredItems);
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }
}

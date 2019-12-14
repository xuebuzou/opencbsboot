package cn.com.bocd.opencbsboot.tool.compositedata.rocoll;

import java.util.ListIterator;

public class ReadOnlyListIter<T> implements ListIterator<T> {
    private boolean readOnly;
    private ListIterator<T> iter;

    public ReadOnlyListIter(boolean readOnly, ListIterator<T> iter) {
        this.readOnly = readOnly;
        this.iter = iter;
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public T next() {
        return iter.next();
    }

    @Override
    public boolean hasPrevious() {
        return iter.hasPrevious();
    }

    @Override
    public T previous() {
        return iter.previous();
    }

    @Override
    public int nextIndex() {
        return iter.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iter.previousIndex();
    }

    @Override
    public void remove() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        iter.remove();
    }

    @Override
    public void set(T t) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        iter.set(t);
    }

    @Override
    public void add(T t) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        iter.add(t);
    }
}

package cn.com.bocd.opencbsboot.cddata.rocoll;

import java.util.Collection;
import java.util.Iterator;

public class ReadOnlyCollection<T> implements Collection<T> {
    private boolean readOnly;
    private Collection<T> coll;

    public ReadOnlyCollection(boolean readOnly, Collection<T> c) {
        this.readOnly = readOnly;
        this.coll = c;
    }

    @Override
    public int size() {
        return coll.size();
    }

    @Override
    public boolean isEmpty() {
        return coll.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return coll.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIter<>(readOnly, coll.iterator());
    }

    @Override
    public Object[] toArray() {
        return coll.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return coll.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return coll.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return coll.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return coll.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return coll.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return coll.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return coll.retainAll(c);
    }

    @Override
    public void clear() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        coll.clear();
    }
}

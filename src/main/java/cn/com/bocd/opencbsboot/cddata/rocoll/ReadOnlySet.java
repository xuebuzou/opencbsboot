package cn.com.bocd.opencbsboot.cddata.rocoll;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ReadOnlySet<T> implements Set<T> {
    private boolean readOnly;
    private Set<T> set;

    public ReadOnlySet(boolean readOnly, Set<T> set) {
        this.readOnly = readOnly;
        this.set = set;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIter<>(readOnly, set.iterator());
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return set.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return set.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return set.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return set.removeAll(c);
    }

    @Override
    public void clear() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        set.clear();
    }
}

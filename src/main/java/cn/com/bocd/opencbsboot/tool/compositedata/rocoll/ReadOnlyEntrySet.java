package cn.com.bocd.opencbsboot.tool.compositedata.rocoll;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReadOnlyEntrySet<K, V> implements Set<Map.Entry<K, V>> {
    private boolean readOnly;
    private Set<Map.Entry<K, V>> set;

    public ReadOnlyEntrySet(boolean readOnly, Set<Map.Entry<K, V>> set) {
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
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ReadOnlyMapEntryIter<K, V>(readOnly, set.iterator());
    }

    @Override
    public Object[] toArray() {
        Object[] t = set.toArray();
        Object[] r = new Object[t.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = new ReadOnlyMapEntry<>(readOnly, (Map.Entry) t[i]);
        }
        return r;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (set.size() > a.length) {
            Object[] t = set.toArray();
            Object[] r = new Object[t.length];
            for (int i = 0; i < r.length; i++) {
                r[i] = new ReadOnlyMapEntry<>(readOnly, (Map.Entry) t[i]);
            }
            return (T[]) r;
        } else {
            Object[] t = set.toArray();
            for (int i = 0; i < t.length; i++) {
                a[i] = (T) new ReadOnlyMapEntry<>(readOnly, (Map.Entry) t[i]);
            }
            if (set.size() < a.length) {
                a[set.size()] = null;
            }
        }
        return a;
    }

    @Override
    public boolean add(Map.Entry<K, V> kvEntry) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return set.add(kvEntry);
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
    public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
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

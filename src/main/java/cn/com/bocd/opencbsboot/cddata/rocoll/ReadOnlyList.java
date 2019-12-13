package cn.com.bocd.opencbsboot.cddata.rocoll;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReadOnlyList<T> implements List<T> {
    private boolean readOnly;
    private List<T> lst;

    public ReadOnlyList(boolean readOnly, List<T> lst) {
        this.readOnly = readOnly;
        this.lst = lst;
    }

    @Override
    public int size() {
        return lst.size();
    }

    @Override
    public boolean isEmpty() {
        return lst.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return lst.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIter<>(readOnly, lst.iterator());
    }

    @Override
    public Object[] toArray() {
        return lst.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return lst.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return lst.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.retainAll(c);
    }

    @Override
    public void clear() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        lst.clear();
    }

    @Override
    public T get(int index) {
        return lst.get(index);
    }

    @Override
    public T set(int index, T element) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        lst.add(index, element);
    }

    @Override
    public T remove(int index) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return lst.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return lst.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return lst.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ReadOnlyListIter<>(readOnly, lst.listIterator());
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ReadOnlyListIter<>(readOnly, lst.listIterator(index));
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return new ReadOnlyList<>(readOnly, lst.subList(fromIndex, toIndex));
    }
}

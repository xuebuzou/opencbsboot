package cn.com.bocd.opencbsboot.cddata.rocoll;

import java.util.Iterator;

public class ReadOnlyIter<T> implements Iterator<T> {
    private boolean readOnly;
    private Iterator<T> iter;

    public ReadOnlyIter(boolean readOnly, Iterator<T> iter) {
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
    public void remove() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        iter.remove();
    }
}

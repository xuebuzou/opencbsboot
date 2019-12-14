package cn.com.bocd.opencbsboot.tool.compositedata.rocoll;

import java.util.Iterator;
import java.util.Map;

public class ReadOnlyMapEntryIter<K, V> implements Iterator<Map.Entry<K, V>> {
    private boolean readOnly;
    private Iterator<Map.Entry<K, V>> iter;

    public ReadOnlyMapEntryIter(boolean readOnly, Iterator<Map.Entry<K, V>> iter) {
        this.readOnly = readOnly;
        this.iter = iter;
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public Map.Entry<K, V> next() {
        return new ReadOnlyMapEntry<>(readOnly, iter.next());
    }

    @Override
    public void remove() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        iter.remove();
    }
}

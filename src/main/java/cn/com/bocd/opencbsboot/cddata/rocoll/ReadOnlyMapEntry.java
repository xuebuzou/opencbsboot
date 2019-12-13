package cn.com.bocd.opencbsboot.cddata.rocoll;

import java.util.Map;

public class ReadOnlyMapEntry<K, V> implements Map.Entry<K, V> {
    private boolean readOnly;
    private Map.Entry<K, V> entry;

    public ReadOnlyMapEntry(boolean readOnly, Map.Entry<K, V> entry) {
        this.readOnly = readOnly;
        this.entry = entry;
    }

    @Override
    public K getKey() {
        return entry.getKey();
    }

    @Override
    public V getValue() {
        return entry.getValue();
    }

    @Override
    public V setValue(V value) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return entry.setValue(value);
    }
}

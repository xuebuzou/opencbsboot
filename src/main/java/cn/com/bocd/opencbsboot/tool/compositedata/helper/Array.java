package cn.com.bocd.opencbsboot.tool.compositedata.helper;


import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlyException;
import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlyIter;
import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlyList;
import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlyListIter;

import java.util.*;

public final class Array extends AtomData implements List<AtomData> {
    private ArrayList<AtomData> data;
    private final static int POINTCODE;
    private final static int LPCODE;
    private final static int RPCODE;

    static {
        POINTCODE = ".".codePointAt(0);
        LPCODE = "[".codePointAt(0);
        RPCODE = "]".codePointAt(0);
    }

    public Array() {
        data = new ArrayList<>();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<AtomData> iterator() {
        return new ReadOnlyIter<>(readOnly, data.iterator());
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(AtomData value) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.add(value);
    }

    @Override
    public boolean remove(Object o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends AtomData> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        for (Object value : c) {
            if (!(value instanceof CompositeData) && !(value instanceof Field)
                    && !(value instanceof Array)) {
                throw new InvalidTypeException();
            }
        }
        return data.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends AtomData> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        for (Object value : c) {
            if (!(value instanceof CompositeData) && !(value instanceof Field)
                    && !(value instanceof Array)) {
                throw new InvalidTypeException();
            }
        }
        return data.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.retainAll(c);
    }

    @Override
    public void clear() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        data.clear();
    }

    @Override
    public AtomData get(int index) {
        return data.get(index);
    }

    @Override
    public AtomData set(int index, AtomData value) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.set(index, value);
    }

    @Override
    public void add(int index, AtomData value) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        data.add(index, value);
    }

    @Override
    public AtomData remove(int index) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<AtomData> listIterator() {
        return new ReadOnlyListIter<>(readOnly, data.listIterator());
    }

    @Override
    public ListIterator<AtomData> listIterator(int index) {
        return new ReadOnlyListIter<>(readOnly, data.listIterator(index));
    }

    @Override
    public List<AtomData> subList(int fromIndex, int toIndex) {
        return new ReadOnlyList<>(readOnly, data.subList(fromIndex, toIndex));
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<AtomData> e1 = listIterator();
        ListIterator e2 = ((List) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            Object o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    @Override
    public void makeReadOnly() {
        this.readOnly = true;
        this.data.forEach(AtomData::makeReadOnly);
    }

    /**
     * @param fullname : [i]...
     */
    @Override
    public AtomData mGet(String fullname) {
        if (fullname == null || "".equals(fullname.trim())) {
            return null;
        }
        fullname = fullname.trim();
        if (fullname.codePointAt(0) != LPCODE) {
            return null;
        }
        int ri = fullname.indexOf(RPCODE);
        if (ri == -1) {
            return null;
        }
        String sidx = fullname.substring(1, ri);
        try {
            int idx = Integer.parseInt(sidx);
            if (idx < 0 || idx >= this.size()) {
                return null;
            }
            AtomData par = this.get(idx);
            if (ri == fullname.length() - 1 || par == null) {
                return par;
            } else if (fullname.codePointAt(ri + 1) == POINTCODE) {
                if (!(par instanceof CompositeData)) {
                    return null;
                }
                return par.mGet(fullname.substring((ri + 2)));
            } else if (fullname.codePointAt(ri + 1) == LPCODE) {
                if (!(par instanceof Array)) {
                    return null;
                }
                return par.mGet(fullname.substring(ri + 1));
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected AtomData setMultiLevel(String fullname, boolean isCD) throws PutValueFailedException {
        if (fullname == null || "".equals(fullname.trim())) {
            throw new PutValueFailedException("name is null or empty");
        }
        fullname = fullname.trim();
        if (fullname.codePointAt(0) != LPCODE) {
            throw new PutValueFailedException("wrong name format for array: " + fullname);
        }
        int ri = fullname.indexOf(RPCODE);
        if (ri == -1) {
            throw new PutValueFailedException("wrong name format for array: " + fullname);
        }
        String sidx = fullname.substring(1, ri);
        try {
            int idx = Integer.parseInt(sidx);
            if (idx < 0 || idx > this.size()) {
                throw new PutValueFailedException("out of array bound");
            }
            AtomData par = this.get(idx);

            if (ri == fullname.length() - 1) {
                if (par == null) {
                    if (isCD) {
                        CompositeData cd = new CompositeData();
                        this.set(idx, cd);
                        return cd;
                    } else {
                        Array arr = new Array();
                        this.set(idx, arr);
                        return arr;
                    }
                } else {
                    if ((isCD && (par instanceof CompositeData)) || (!isCD && (par instanceof Array))) {
                        return par;
                    } else {
                        throw new PutValueFailedException("wrong type");
                    }
                }
            } else if (fullname.codePointAt(ri + 1) == POINTCODE) {
                if (par == null) {
                    par = new CompositeData();
                    this.set(idx, par);
                    return par.setMultiLevel(fullname.substring(ri + 1), isCD);
                } else if (!(par instanceof CompositeData)) {
                    throw new PutValueFailedException("wrong type");
                }
                return par.setMultiLevel(fullname.substring((ri + 1)), isCD);
            } else if (fullname.codePointAt(ri + 1) == LPCODE) {
                if (par == null) {
                    par = new Array();
                    this.set(idx, par);
                    return par.setMultiLevel(fullname.substring(ri + 1), isCD);
                } else if (!(par instanceof Array)) {
                    throw new PutValueFailedException("wrong type");
                }
                return par.setMultiLevel(fullname.substring(ri + 1), isCD);
            } else {
                throw new PutValueFailedException("wrong name: " + fullname);
            }
        } catch (NumberFormatException e) {
            throw new PutValueFailedException("wrong index: " + fullname);
        }
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Array [").append(this.size()).append("] {\n");
        int i = 0;
        for (AtomData data : this) {
            for (int j = 0; j <= tabcnt; j++) {
                sb.append(tab);
            }
            sb.append(i).append("\t: ");
            data.toString(sb, tab, tabcnt + 1);
            sb.append("\n");
            i++;
        }
        for (int j = 0; j < tabcnt; j++) {
            sb.append(tab);
        }
        sb.append("}");
    }

    @Override
    public Array deepCopy() {
        Array cp = new Array();
        for (AtomData d : this.data) {
            if (d != null) {
                cp.add(d.deepCopy());
            } else {
                cp.add(null);
            }
        }
        return cp;
    }
}

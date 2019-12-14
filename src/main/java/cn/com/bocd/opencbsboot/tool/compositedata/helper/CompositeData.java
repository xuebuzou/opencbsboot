package cn.com.bocd.opencbsboot.tool.compositedata.helper;


import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlyCollection;
import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlyEntrySet;
import cn.com.bocd.opencbsboot.tool.compositedata.rocoll.ReadOnlySet;
import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class CompositeData extends AtomData implements
        Map<String, AtomData> {
    private final static int POINTCODE;
    private final static int LPCODE;
    private final static int RPCODE;

    static {
        POINTCODE = ".".codePointAt(0);
        LPCODE = "[".codePointAt(0);
        RPCODE = "]".codePointAt(0);
    }

    private Map<String, AtomData> data;

    public CompositeData() {
        data = new HashMap<>();
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
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public AtomData get(Object key) {
        return data.get(key);
    }

    public <T extends AtomData> T iGet(String key) {
        return (T) data.get(key);
    }

    @Override
    public AtomData put(String key, AtomData value) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.put(key, value);
    }

    @Override
    public AtomData remove(Object key) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        return data.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends AtomData> m) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        data.putAll(m);
    }

    @Override
    public void clear() {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        data.clear();
    }

    @Override
    public Set<String> keySet() {
        return new ReadOnlySet<>(readOnly, data.keySet());
    }

    @Override
    public Collection<AtomData> values() {
        return new ReadOnlyCollection<>(readOnly, data.values());
    }

    @Override
    public Set<Entry<String, AtomData>> entrySet() {
        return new ReadOnlyEntrySet<>(readOnly, data.entrySet());
    }

    @Override
    public void makeReadOnly() {
        this.readOnly = true;
        this.data.values().stream().filter(d -> d != null).forEach(AtomData::makeReadOnly);
    }

    /**
     * @param fullname : a.b.c, a[i].b.c
     * @return null or AtomData, no exception throw up
     */
    @Override
    public AtomData mGet(String fullname) {
        if (fullname == null || "".equals(fullname.trim())) {
            return null;
        }
        fullname = fullname.trim();
        if (fullname.codePointAt(0) == POINTCODE
                || fullname.codePointAt(0) == LPCODE) {
            return null;
        }
        int pi = fullname.indexOf(POINTCODE);
        int li = fullname.indexOf(LPCODE);
        if (pi == -1 && li == -1) {
            return this.get(fullname);
        } else if ((pi > 0 && pi < li) || (pi > 0 && li == -1)) {
            String name = fullname.substring(0, pi);
            String leftname = fullname.substring(pi + 1);
            AtomData par = this.get(name);
            if (par == null || !(par instanceof CompositeData)) {
                return null;
            }
            return par.mGet(leftname);
        } else if ((li > 0 && li < pi) || (li > 0 && pi == -1)) {
            int ri = fullname.indexOf(RPCODE);
            if (li > ri) {
                return null;
            }
            String name = fullname.substring(0, li);
            String leftname = fullname.substring(li);
            AtomData par = this.get(name);
            if (par == null || !(par instanceof Array)) {
                return null;
            }
            return par.mGet(leftname);
        }
        return null;
    }

    @Override
    protected AtomData setMultiLevel(String fullname, boolean isCD) throws PutValueFailedException {
        if (fullname.codePointAt(0) == POINTCODE) {
            fullname = fullname.substring(1);
        }
        if (fullname.codePointAt(0) == POINTCODE
                || fullname.codePointAt(0) == LPCODE) {
            throw new PutValueFailedException("invalid name");
        }
        int pi = fullname.indexOf(POINTCODE);
        int li = fullname.indexOf(LPCODE);
        if (pi == -1 && li == -1) {
            if (isCD) {
                CompositeData cd = new CompositeData();
                this.put(fullname, cd);
                return cd;
            } else {
                Array arr = new Array();
                this.put(fullname, arr);
                return arr;
            }
        } else if ((pi > 0 && pi < li) || (pi > 0 && li == -1)) {
            String name = fullname.substring(0, pi);
            String leftname = fullname.substring(pi + 1);
            AtomData par = this.get(name);
            if (par == null) {
                CompositeData cd = new CompositeData();
                this.put(name, cd);
                return cd.setMultiLevel(leftname, isCD);
            }
            if (!(par instanceof CompositeData)) {
                throw new PutValueFailedException(name + " is not CompositeData");
            }
            return par.setMultiLevel(leftname, isCD);
        } else if ((li > 0 && li < pi) || (li > 0 && pi == -1)) {
            int ri = fullname.indexOf(RPCODE);
            if (li > ri) {
                throw new PutValueFailedException("wrong name format: " + fullname);
            }
            String name = fullname.substring(0, li);
            String leftname = fullname.substring(li);
            AtomData par = this.get(name);
            if (par == null) {
                Array arr = new Array();
                this.put(name, arr);
                return arr.setMultiLevel(leftname, isCD);
            }
            if (!(par instanceof Array)) {
                throw new PutValueFailedException(name + " is not Array");
            }
            return par.setMultiLevel(leftname, isCD);
        } else {
            throw new PutValueFailedException("should never happen");
        }
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Map))
            return false;
        Map m = (Map) o;
        if (m.size() != size())
            return false;

        try {
            for (Entry<String, AtomData> e : entrySet()) {
                String key = e.getKey();
                Object value = e.getValue();
                if (value == null) {
                    if (!(m.get(key) == null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

        return true;
    }

    /**
     * @param fullname 全路径名
     * @param value    用于替换的值
     * @return 被替换的值
     * @throws PutValueFailedException 如果在fullname路径上出现类型不匹配的情况，或者fullname格式错误，则抛出异常
     */
    public AtomData mPut(String fullname, AtomData value)
            throws PutValueFailedException {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        // a.b.c[i]
        if (fullname.codePointAt(fullname.length() - 1) == RPCODE) {
            int li = fullname.lastIndexOf(LPCODE);
            if (li == -1) {
                throw new PutValueFailedException("invalid fullname");
            }
            String sidx = fullname.substring(li + 1, fullname.length() - 1);
            try {
                int idx = Integer.parseInt(sidx);
                if (idx < 0) {
                    throw new PutValueFailedException("negative index");
                }
                String parname = fullname.substring(0, li);
                AtomData par = this.mGet(parname);
                if (par == null) {
                    par = this.setMultiLevel(parname, false);
                }
                if (!(par instanceof Array)) {
                    throw new PutValueFailedException("not array");
                }
                Array arrpar = (Array) par;
                if (idx < arrpar.size()) {
                    return arrpar.set(idx, value);
                } else if (idx == arrpar.size()) {
                    arrpar.add(value);
                    return null;
                } else {
                    throw new PutValueFailedException("out of array bound");
                }
            } catch (NumberFormatException e) {
                throw new PutValueFailedException(e);
            }
        } else {// a.b.c
            int pi = fullname.lastIndexOf(POINTCODE);
            if (pi == -1) {
                return this.put(fullname, value);
            } else {
                if (pi == fullname.length() - 1) {
                    throw new PutValueFailedException("last char is point");
                }
                String parname = fullname.substring(0, pi);
                AtomData par = this.mGet(parname);
                if (par == null) {
                    par = this.setMultiLevel(parname, true);
                }
                if (!(par instanceof CompositeData)) {
                    throw new PutValueFailedException("not compositedata");
                }
                CompositeData cdpar = (CompositeData) par;
                String subname = fullname.substring(pi + 1);
                return cdpar.put(subname, value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.toString(sb, "\t", 0);
        return sb.toString();
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("CompositeData {\n");
        for (Entry<String, AtomData> entry : this.entrySet()) {
            for (int i = 0; i <= tabcnt; i++) {
                sb.append(tab);
            }
            sb.append("\"").append(entry.getKey()).append("\"\t: ");
            entry.getValue().toString(sb, tab, tabcnt + 1);
            sb.append("\n");
        }
        for (int i = 0; i < tabcnt; i++) {
            sb.append(tab);
        }
        sb.append("}");
    }

    @Override
    public CompositeData deepCopy() {
        CompositeData cp = new CompositeData();
        for (Entry<String, AtomData> entry : this.data.entrySet()) {
            if (entry.getValue() != null) {
                cp.put(entry.getKey(), entry.getValue().deepCopy());
            } else {
                cp.put(entry.getKey(), null);
            }
        }
        return cp;
    }

}

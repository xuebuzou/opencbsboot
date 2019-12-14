package cn.com.bocd.opencbsboot.tool.compositedata.helper;

public abstract class AtomData {
    protected boolean readOnly;

    public abstract void makeReadOnly();

    public abstract AtomData mGet(String fullname);

    protected abstract AtomData setMultiLevel(String fullname, boolean isCD) throws PutValueFailedException;

    protected abstract void toString(StringBuilder sb, String tab, int tabcnt);

    public abstract <T extends AtomData> T deepCopy();
}

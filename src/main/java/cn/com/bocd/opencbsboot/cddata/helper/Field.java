package cn.com.bocd.opencbsboot.cddata.helper;

public abstract class Field<X> extends AtomData {
    public abstract X getValue();

    public abstract void setValue(X o);

    private final static int magic = 9527;

    @Override
    public void makeReadOnly() {
        this.readOnly = true;
    }

    @Override
    public AtomData mGet(String fullname) {
        if (fullname == null || "".equals(fullname)
                || "".equals(fullname.trim())) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    protected AtomData setMultiLevel(String fullname, boolean isCD) throws PutValueFailedException {
        throw new PutValueFailedException("wrong");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Field field = (Field) o;
        Object thisv = this.getValue();
        Object otherv = field.getValue();
        return (thisv == null && otherv == null) || (thisv != null && thisv.equals(otherv));
    }

    @Override
    public int hashCode() {
        Object thisv = this.getValue();
        if (thisv != null) {
            return thisv.hashCode();
        } else {
            return magic;
        }
    }

}

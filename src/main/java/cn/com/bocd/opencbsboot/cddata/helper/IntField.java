package cn.com.bocd.opencbsboot.cddata.helper;

import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

public final class IntField extends Field<Integer> {
    private Integer v;

    public IntField(Integer v) {
        this.v = v;
    }

    @Override
    public Integer getValue() {
        return v;
    }

    @Override
    public void setValue(Integer o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        v = o;
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Field {  type=FieldType[int]  value={").append(v)
                .append("}  }");
    }

    @Override
    public IntField deepCopy() {
        return new IntField(this.v);
    }
}

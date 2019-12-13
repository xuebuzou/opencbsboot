package cn.com.bocd.opencbsboot.cddata.helper;

import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

public final class FloatField extends Field<Float> {
    private Float v;

    public FloatField(Float v) {
        this.v = v;
    }

    @Override
    public Float getValue() {
        return v;
    }

    @Override
    public void setValue(Float o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        v = o;
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Field {  type=FieldType[float]  value={").append(v)
                .append("}  }");
    }

    @Override
    public FloatField deepCopy() {
        return new FloatField(this.v);
    }
}

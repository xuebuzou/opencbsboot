package cn.com.bocd.opencbsboot.cddata.helper;


import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

public final class DoubleField extends Field<Double> {
    private Double v;

    public DoubleField(Double v) {
        this.v = v;
    }

    @Override
    public Double getValue() {
        return v;
    }

    @Override
    public void setValue(Double o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        v = o;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Field {  type=FieldType[double]  value={").append(v).append("}  }");
    }

    @Override
    public DoubleField deepCopy() {
        return new DoubleField(this.v);
    }

}

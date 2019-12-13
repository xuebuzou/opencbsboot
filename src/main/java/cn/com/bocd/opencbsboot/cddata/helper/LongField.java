package cn.com.bocd.opencbsboot.cddata.helper;

//import cn.com.bocd.compositedata.rocoll.ReadOnlyException;
import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

public final class LongField extends Field<Long> {
    private Long v;

    public LongField(Long v) {
        this.v = v;
    }

    @Override
    public Long getValue() {
        return v;
    }

    @Override
    public void setValue(Long o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        v = o;
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Field {  type=FieldType[long]  value={").append(v)
                .append("}  }");
    }

    @Override
    public LongField deepCopy() {
        return new LongField(this.v);
    }
}

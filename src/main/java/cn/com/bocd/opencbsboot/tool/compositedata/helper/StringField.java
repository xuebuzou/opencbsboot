package cn.com.bocd.opencbsboot.tool.compositedata.helper;

//import cn.com.bocd.compositedata.rocoll.ReadOnlyException;
import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

public final class StringField extends Field<String> {
    private String v;

    public StringField(String v) {
        this.v = v;
    }

    @Override
    public String getValue() {
        return v;
    }

    @Override
    public void setValue(String o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        v = o;
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Field {  type=FieldType[string]  value={").append(v)
                .append("}  }");
    }

    @Override
    public StringField deepCopy() {
        return new StringField(this.v);
    }
}

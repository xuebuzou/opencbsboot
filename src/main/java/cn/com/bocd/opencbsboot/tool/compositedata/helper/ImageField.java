package cn.com.bocd.opencbsboot.tool.compositedata.helper;

import cn.com.bocd.opencbsboot.exception.other.ReadOnlyException;

import java.util.Arrays;

public final class ImageField extends Field {
    private static Class bytearray;

    static {
        bytearray = (new byte[0]).getClass();
    }

    private byte[] v;

    public ImageField(byte[] v) {
        this.v = v;
    }

    @Override
    public Object getValue() {
        if (readOnly) {
            if (v != null) {
                byte[] cp = new byte[v.length];
                System.arraycopy(v, 0, cp, 0, v.length);
                return cp;
            }
        }
        return v;
    }

    @Override
    public void setValue(Object o) {
        if (readOnly) {
            throw new ReadOnlyException();
        }
        if (o == null) {
            v = null;
        } else if (o.getClass() == bytearray) {
            v = (byte[]) o;
        } else {
            throw new InvalidFieldTypeException("invalid image field type");
        }
    }

    @Override
    protected void toString(StringBuilder sb, String tab, int tabcnt) {
        sb.append("Field {  type=FieldType[image]  value={").append(Arrays.toString(v))
                .append("}  }");
    }

    @Override
    public ImageField deepCopy() {
        byte[] cpv = new byte[this.v.length];
        System.arraycopy(this.v, 0, cpv, 0, this.v.length);
        return new ImageField(cpv);
    }
}

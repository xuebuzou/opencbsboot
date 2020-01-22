package generate;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ZG_DEP
 * @author 
 */
public class ZgDep implements Serializable {
    private String depCode;

    private String depDesc;

    private BigDecimal depId;

    private static final long serialVersionUID = 1L;

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepDesc() {
        return depDesc;
    }

    public void setDepDesc(String depDesc) {
        this.depDesc = depDesc;
    }

    public BigDecimal getDepId() {
        return depId;
    }

    public void setDepId(BigDecimal depId) {
        this.depId = depId;
    }
}
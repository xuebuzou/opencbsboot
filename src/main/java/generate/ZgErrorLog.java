package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * ZG_ERROR_LOG
 * @author 
 */
@Data
public class ZgErrorLog implements Serializable {
    private String msgType;

    private String msgCode;

    private String srcType;

    private String userId;

    private String stackMsg;

    private String retMsg;

    private String seqNo;

    private String logTime;

    private String reqBody;

    private String sysHead;

    private String appHead;

    private String errorType;

    private static final long serialVersionUID = 1L;
}
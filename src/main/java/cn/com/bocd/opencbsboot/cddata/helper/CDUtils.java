package cn.com.bocd.opencbsboot.cddata.helper;


import cn.com.bocd.opencbsboot.exception.other.BzRollBackException;
import cn.com.bocd.opencbsboot.util.toolkit.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.*;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CDUtils {
    private static Logger logger = LogManager.getLogger(CDUtils.class.getName());
    private static final Base64 BASE64 = new Base64();

    public static Date cvt8str(String yyyyMMdd) {
        if (yyyyMMdd == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        try {
            return f.parse(yyyyMMdd);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String date8str(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        return f.format(d);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFValue(CompositeData data, String name) {
        Field<T> f = (Field<T>) data.mGet(name);
        if (f == null) {
            return null;
        }
        return f.getValue();
    }

    public static void setStringField(CompositeData src, String path, String val) throws PutValueFailedException {
        src.mPut(path, new StringField(val));
    }

    public static void setData(CompositeData src, String path, AtomData data) throws PutValueFailedException {
        src.mPut(path, data);
    }

    public static CompositeData getRespFromReq(CompositeData req) {
        CompositeData resp = new CompositeData();
        CompositeData syshead = (CompositeData) req.get("SYS_HEAD");
        if (syshead != null) {
            CompositeData cp = syshead.deepCopy();
            cp.remove("RET_STATUS");
            resp.put("SYS_HEAD", cp);
            StringField reqSrc = (StringField) cp.get("SOURCE_BRANCH_NO");
            StringField reqDest = (StringField) cp.get("DEST_BRANCH_NO");
            cp.remove("SOURCE_BRANCH_NO");
            cp.remove("DEST_BRANCH_NO");
            if (null != reqDest)
                cp.put("SOURCE_BRANCH_NO", reqDest);
            else
                cp.put("SOURCE_BRANCH_NO", new StringField("000002"));
            if (null != reqSrc)
                cp.put("DEST_BRANCH_NO", reqSrc);
        }
        CompositeData apphead = (CompositeData) req.get("APP_HEAD");
        if (apphead != null) {
            resp.put("APP_HEAD", apphead.deepCopy());
        }
        StringField fmt = (StringField) resp.mGet("SYS_HEAD.MESSAGE_TYPE");
        if (fmt != null) {
            String s = fmt.getValue();
            if (s != null) {
                String srcType = getFValue(resp, "SYS_HEAD.SOURCE_TYPE");
                try {
                    int msgType = Integer.valueOf(s);
                    fmt.setValue((msgType + 10) + "");
                } catch (NumberFormatException e) {
                    fmt.setValue(s + "RE");
                }
            }
        }

        return resp;
    }

    public static boolean mergeLocalHeadToSysHead(CompositeData req, CompositeData resp) {
        Array shret = (Array) resp.mGet("SYS_HEAD.RET");
        if (shret == null) {
            shret = new Array();
            try {
                resp.mPut("SYS_HEAD.RET", shret);
            } catch (PutValueFailedException e) {
//                if (logger.isWarnEnabled()) {
//                    logger.warn(e);
//                }
                return false;
            }
        }
        CompositeData lh = (CompositeData) req.get("LOCAL_HEAD");
        if (lh != null) {
            Array ret = (Array) lh.get("RET");
            if (ret != null) {
                for (AtomData d : ret) {
                    if (d instanceof CompositeData) {
                        shret.add(d.deepCopy());
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param retStatus: S－交易成功 F－交易失败 O－交易授权 C－交易确认 B－授权＋确认
     */
    public static void setRespStatus(CompositeData resp, String retStatus, String retCode, String retMessage) {
        CompositeData syshead = (CompositeData) resp.get("SYS_HEAD");
        syshead.put("RET_STATUS", new StringField(retStatus));
        Array ret = (Array) syshead.get("RET");
        if (ret == null) {
            ret = new Array();
            syshead.put("RET", ret);
        }

        CompositeData cd = new CompositeData();
        cd.put("RET_CODE", new StringField(retCode));
        cd.put("RET_MSG", new StringField(retMessage));
        if (0 == ret.size()) {
            ret.add(cd);
        } else {
            ret.set(0, cd);
        }
    }

    public static void setSuccessRespStatus(CompositeData resp) {
        setRespStatus(resp, "S", "000000", "Success");
    }

    public static CompositeData fromFile(File xmlFile) {
        if (!xmlFile.exists() || !xmlFile.isFile())
            return null;
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(xmlFile);
            return fromDoc(doc);
        } catch (DocumentException e) {
            throw new ParseXmlException(e);
        }
    }

    public static CompositeData fromXml(String xml) {
        try {
            Document doc = DocumentHelper.parseText(xml);
            return fromDoc(doc);
        } catch (DocumentException e) {
            throw new ParseXmlException(e);
        }

    }

    private static CompositeData fromDoc(Document doc) {
        CompositeData rt = new CompositeData();
        Element root = doc.getRootElement();
        Element sysheader = root.element("sys-header");
        if (sysheader != null) {
            Element data = sysheader.element("data");
            if (data != null) {
                Element struct = data.element("struct");
                if (struct != null) {
                    rt.put("SYS_HEAD", fromStruct(struct));
                }
            }
        }
        Element appheader = root.element("app-header");
        if (appheader != null) {
            Element data = appheader.element("data");
            if (data != null) {
                Element struct = data.element("struct");
                if (struct != null) {
                    rt.put("APP_HEAD", fromStruct(struct));
                }
            }
        }
        Element localheader = root.element("local-header");
        if (localheader != null) {
            Element data = localheader.element("data");
            if (data != null) {
                Element struct = data.element("struct");
                if (struct != null) {
                    rt.put("LOCAL_HEAD", fromStruct(struct));
                }
            }
        }
        Element body = root.element("body");
        if (body != null) {
            for (Object sub : body.elements()) {
                Element subele = (Element) sub;
                if (!"data".equals(subele.getName())) {
                    throw new ParseXmlException("wrong xml format, expect data, but got:" + subele.getName());
                }
                String name = subele.attributeValue("name");
                if (name == null || "".equals(name)) {
                    throw new ParseXmlException("wrong xml format, need data name attribute");
                }
                if (name.equals("SYS_HEAD") || name.equals("APP_HEAD") || name.equals("LOCAL_HEAD")) {
                    continue;
                }
                List datasubs = subele.elements();
                if (datasubs.size() != 1) {
                    throw new ParseXmlException(" number of data subelements can only be one");
                }
                Element datasub = (Element) datasubs.get(0);
                parseRecursive(rt, name, datasub);
            }
        }
        return rt;
    }

    private static CompositeData fromStruct(Element struct) {
        CompositeData cd = new CompositeData();
        for (Object sub : struct.elements()) {
            Element subele = (Element) sub;
            if (!"data".equals(subele.getName())) {
                throw new ParseXmlException("wrong xml format, expect data, but got:" + subele.getName());
            }
            String name = subele.attributeValue("name");
            if (name == null || "".equals(name)) {
                throw new ParseXmlException("wrong xml format, need data name attribute");
            }
            List datasubs = subele.elements();
            if (datasubs.size() != 1) {
                throw new ParseXmlException(" number of data subelements can only be one");
            }
            Element datasub = (Element) datasubs.get(0);
            parseRecursive(cd, name, datasub);
        }
        return cd;
    }

    private static void parseRecursive(CompositeData rt, String name, Element datasub) {

        if ("field".equals(datasub.getName())) {
            rt.put(name, fromField(datasub));
        } else if ("array".equals(datasub.getName())) {
            rt.put(name, fromArray(datasub));
        } else if ("struct".equals(datasub.getName())) {
            rt.put(name, fromStruct(datasub));
        } else {
            throw new ParseXmlException("wrong xml format");
        }
    }

    private static Array fromArray(Element array) {
        Array arr = new Array();
        for (Object o : array.elements()) {
            Element subele = (Element) o;
            if ("field".equals(subele.getName())) {
                arr.add(fromField(subele));
            } else if ("array".equals(subele.getName())) {
                arr.add(fromArray(subele));
            } else if ("struct".equals(subele.getName())) {
                arr.add(fromStruct(subele));
            } else {
                throw new ParseXmlException("wrong xml format");
            }
        }
        return arr;
    }

    private static Field fromField(Element field) {
        String type = field.attributeValue("type");
        if ("int".equals(type)) {
            String txt = null;
            if (field.hasContent()) {
                txt = field.getText();
            }
            Integer v;
            if (txt == null) {
                v = null;
            } else {
                try {
                    v = Integer.valueOf(txt);
                } catch (NumberFormatException e) {
                    throw new ParseXmlException(e);
                }
            }
            return new IntField(v);
        } else if ("long".equals(type)) {
            String txt = null;
            if (field.hasContent()) {
                txt = field.getText();
            }
            Long v;
            if (txt == null) {
                v = null;
            } else {
                try {
                    v = Long.valueOf(txt);
                } catch (NumberFormatException e) {
                    throw new ParseXmlException(e);
                }
            }
            return new LongField(v);
        } else if ("float".equals(type)) {
            String txt = null;
            if (field.hasContent()) {
                txt = field.getText();
            }
            Float v;
            if (txt == null) {
                v = null;
            } else {
                try {
                    v = Float.valueOf(txt);
                } catch (NumberFormatException e) {
                    throw new ParseXmlException(e);
                }
            }
            return new FloatField(v);
        } else if ("double".equals(type)) {
            String txt = null;
            if (field.hasContent()) {
                txt = field.getText();
            }
            Double v;
            if (txt == null) {
                v = null;
            } else {
                try {
                    v = Double.valueOf(txt);
                } catch (NumberFormatException e) {
                    throw new ParseXmlException(e);
                }
            }
            return new DoubleField(v);
        } else if ("string".equals(type)) {
            String txt = null;
            if (field.hasContent()) {
                txt = field.getText();
            }
            String v;
            if (txt == null) {
                v = null;
            } else {
                try {
                    v = txt;
                } catch (NumberFormatException e) {
                    throw new ParseXmlException(e);
                }
            }
            return new StringField(v);
        } else if ("image".equals(type)) {
            String txt = null;
            if (field.hasContent()) {
                txt = field.getText();
            }
            byte[] v;
            if (txt == null) {
                v = null;
            } else {
                v = BASE64.decode(txt);
            }
            return new ImageField(v);
        } else {
            throw new ParseXmlException("not support type:" + type);
        }
    }

    public static Document toDoc(CompositeData cd) {
        Document doc = DocumentFactory.getInstance().createDocument();
        doc.setXMLEncoding("utf-8");
        Element root = doc.addElement("service");
        Element sysheader = root.addElement("sys-header");
        Element sh = sysheader.addElement("data");
        sh.addAttribute("name", "SYS_HEAD");
        addCD(sh, (CompositeData) cd.get("SYS_HEAD"));

        Element appheader = root.addElement("app-header");
        Element ah = appheader.addElement("data");
        ah.addAttribute("name", "APP_HEAD");
        addCD(ah, (CompositeData) cd.get("APP_HEAD"));

        Element localheader = root.addElement("local-header");
        Element lh = localheader.addElement("data");
        lh.addAttribute("name", "LOCAL_HEAD");
        addCD(lh, (CompositeData) cd.get("LOCAL_HEAD"));

        Element body = root.addElement("body");
        for (Map.Entry<String, AtomData> entry : cd.entrySet()) {
            String name = entry.getKey();
            if ("APP_HEAD".equals(name) || "SYS_HEAD".equals(name) || "LOCAL_HEAD".equals(name)) {
                continue;
            }
            AtomData v = entry.getValue();
            Element sub = body.addElement("data");
            sub.addAttribute("name", name);
            addRecursive(sub, v);
        }
        return doc;
    }

    private static void addRecursive(Element sub, AtomData v) {
        if (v == null) {
            addField(sub, new StringField(null));
            logger.debug("null value, ignored.");
        } else if (v instanceof CompositeData) {
            addCD(sub, (CompositeData) v);
        } else if (v instanceof Array) {
            addArr(sub, (Array) v);
        } else if (v instanceof Field) {
            addField(sub, (Field) v);
        }
    }

    public static String toXml(CompositeData cd) {
        return toXml(cd, false);
    }

    public static String toXml(CompositeData cd, boolean formatted) {
        Document doc = toDoc(cd);
        if (formatted) {
            StringWriter stringWriter = new StringWriter();
            OutputFormat xmlFormat = new OutputFormat();
            xmlFormat.setEncoding("UTF-8");
            xmlFormat.setNewlines(true);
            xmlFormat.setIndent(true);
            xmlFormat.setIndent("    ");

            XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);
            //写入文件
            try {
                xmlWriter.write(doc);
            } catch (IOException ignored) {
                //ignored
            } finally {
                try {
                    xmlWriter.close();
                } catch (IOException ignored) {
                    //ignored
                }
            }
            return stringWriter.toString();
        }
        else {
            return doc.asXML();
        }
    }


    private static void addCD(Element root, CompositeData cd) {
        Element struct = root.addElement("struct");
        if (cd == null) {
            return;
        }
        for (Map.Entry<String, AtomData> entry : cd.entrySet()) {
            String name = entry.getKey();
            AtomData v = entry.getValue();
            Element sub = struct.addElement("data");
            sub.addAttribute("name", name);
            addRecursive(sub, v);
        }
    }

    private static void addArr(Element root, Array arr) {
        Element arrele = root.addElement("array");
        if (arr == null) {
            return;
        }
        for (AtomData v : arr) {
            addRecursive(arrele, v);
        }
    }

    private static void addField(Element root, Field f) {

        if (null == f.getValue()) {
            root.getParent().remove(root);
            return;
        }

        Element fele = root.addElement("field");
        if (f instanceof StringField) {
            StringField sf = (StringField) f;
            String v = sf.getValue();
            if (v == null) {
                fele.addAttribute("length", "0");
            } else {
                fele.addAttribute("length", "" + v.getBytes().length);
                fele.addText(v);
            }
            fele.addAttribute("scale", "0");
            fele.addAttribute("type", "string");
        } else if (f instanceof IntField) {
            IntField intf = (IntField) f;
            Integer v = intf.getValue();
            if (v == null) {
                fele.addAttribute("length", "32");
            } else {
                fele.addAttribute("length", "32");
                fele.addText(Integer.toString(v));
            }
            fele.addAttribute("scale", "0");
            fele.addAttribute("type", "int");
        } else if (f instanceof LongField) {
            LongField longf = (LongField) f;
            Long v = longf.getValue();
            if (v == null) {
                fele.addAttribute("length", "64");
            } else {
                fele.addAttribute("length", "64");
                fele.addText(Long.toString(v));
            }
            fele.addAttribute("scale", "0");
            fele.addAttribute("type", "long");
        } else if (f instanceof FloatField) {
            FloatField floatf = (FloatField) f;
            Float v = floatf.getValue();
            if (v == null) {
                fele.addAttribute("length", "32");
            } else {
                fele.addAttribute("length", "32");
                fele.addText(String.format("%.2f", v));
            }
            fele.addAttribute("scale", "0");
            fele.addAttribute("type", "float");
        } else if (f instanceof DoubleField) {
            DoubleField doublef = (DoubleField) f;
            Double v = doublef.getValue();
            if (v == null) {
                fele.addAttribute("length", "64");
            } else {
                fele.addAttribute("length", "64");
                fele.addText(String.format("%.2f", v));
            }
            fele.addAttribute("scale", "0");
            fele.addAttribute("type", "double");
        } else if (f instanceof ImageField) {
            ImageField imagef = (ImageField) f;
            byte[] v = (byte[]) imagef.getValue();
            if (v == null) {
                fele.addAttribute("length", "0");
            } else {
                String ev = BASE64.encodeToString(v);
                fele.addAttribute("length", "" + ev.length());
                fele.addText(ev);
            }
            fele.addAttribute("scale", "0");
            fele.addAttribute("type", "image");
        }
    }

    public static void copyBody(CompositeData src, CompositeData dest) {
        CompositeData temp = src.deepCopy();
        temp.remove("SYS_HEAD");
        temp.remove("APP_HEAD");
        temp.remove("LOCAL_HEAD");
        dest.putAll(temp);
    }

    public static CompositeData getCopyWithHeads(CompositeData src) {
        CompositeData cd = new CompositeData();
        cd.put("SYS_HEAD", src.get("SYS_HEAD").deepCopy());
        cd.put("APP_HEAD", src.get("APP_HEAD").deepCopy());
        cd.put("LOCAL_HEAD", src.get("LOCAL_HEAD").deepCopy());
        return cd;
    }

    interface AtomDataValidator {
        Object validateAndGetValue(AtomData data);
    }

    private static final AtomDataValidator NOT_EMPTY_ATOMDATA_VALIDATOR = data -> {
        if (data instanceof Field) {
            Object val = ((Field) data).getValue();
            if (null != val && StrKit.isBlank(val.toString()))
                return null;
            return val;
        }
        return data;
    };

    private static Object[] checkAndGetCompulsoryFieldValues(CompositeData cd, String[] fieldNames, AtomDataValidator validator) {
        if (null == cd || null == fieldNames || 0 == fieldNames.length) return null;
        Object[] values = new Object[fieldNames.length];
        int idx = 0;
        for (String fieldName : fieldNames) {
            AtomData data = cd.mGet(fieldName);
            Object val = validator.validateAndGetValue(data);
            if (null != val) {
                values[idx++] = val;
            }
            else return null;
        }
        return values;
    }

    public static Object[] checkAndGetCompulsoryFieldValues(CompositeData cd, String[] fieldNames) {
        return checkAndGetCompulsoryFieldValues(cd, fieldNames, NOT_EMPTY_ATOMDATA_VALIDATOR);
    }

    /**
     * 分页查询
     * @param reqAppHead 应用头中用来控制分页的相关参数
     * @param pageable 分页提供的DAO对象
     * @param emptyAlertFlag 没有记录返回时是否抛出异常
     * @param <T> 数据库表映射实体
     * @return 记录列表
     * @throws BzRollBackException 参数或其他错误以异常方式抛出
     */
    public static <T> List<T> pagination(CompositeData reqAppHead, CompositeData respAppHead, Pageable<T> pageable, String emptyAlertFlag) throws BzRollBackException{

        String pageUpDownFlag = CDUtils.getFValue(reqAppHead, "PGUP_OR_PGDN");
        String pageStart = CDUtils.getFValue(reqAppHead, "PAGE_START");
        String pageEnd = CDUtils.getFValue(reqAppHead, "PAGE_END");
        String totalNum = CDUtils.getFValue(reqAppHead, "TOTAL_NUM");
        String currentNum = CDUtils.getFValue(reqAppHead, "CURRENT_NUM");
        String totalRows = CDUtils.getFValue(reqAppHead, "TOTAL_ROWS");
        String totalPages = CDUtils.getFValue(reqAppHead, "TOTAL_PAGES");
        String totalFlag = CDUtils.getFValue(reqAppHead, "TOTAL_FLAG");

        int upDownFlag, pStart, pEnd, tNum, cNum = 0, tRows = 0, tPages = 0, vRows = 0;
        boolean isTotalRPPrint = true;
        if (StrKit.notBlank(pageUpDownFlag)) {
            upDownFlag = Integer.valueOf(pageUpDownFlag);
            if (0 != (upDownFlag & 0xFE))
                throw new BzRollBackException("0000304", "APP_HEAD中翻页标志PGUP_OR_PGDN错误");
        } else
            upDownFlag = 1;

        if (StrKit.notBlank(totalFlag)) {
            if (!"E".equals(totalFlag) && ! "O".equals(totalFlag) && !"N".equals(totalFlag))
                throw new BzRollBackException("0000304", "APP_HEAD中汇总标志TOTAL_FLAG错误");
        } else totalFlag = "N";

        if (StrKit.notBlank(totalNum)) {
            tNum = Integer.valueOf(totalNum);
            if (0 == tNum || -1 > tNum)
                throw new BzRollBackException("0000303", "APP_HEAD中每页记录数TOTAL_NUM错误");
        } else
            throw new BzRollBackException("0000303", "APP_HEAD中TOTAL_NUM不能为空");

        if (StrKit.notBlank(currentNum)) cNum = Integer.valueOf(currentNum);
        if (StrKit.notBlank(totalRows)) tRows = Integer.valueOf(totalRows);
        if (StrKit.notBlank(totalPages)) tPages = Integer.valueOf(totalPages);

        // 一页返回全部
        if (-1 == tNum) {
            vRows = pageable.total();
            if (0 == vRows && StrKit.isBlank(emptyAlertFlag)) {
                throw new BzRollBackException("0000305", "查询无返回记录");
            }
            upDownFlag = pStart = tPages = 1;
            pEnd = tRows = vRows;
            // 每页返回固定条数(tNum)
        } else {
            //根据当前记录(cNum)，计算出当前页，当前页首记录，当前页尾记录
            int cPage = (int)Math.floor((double)(cNum - 1) / tNum) + 1;
            pStart = (cPage - 1) * tNum + 1;
            pEnd = cPage * tNum;
            //下翻页
            if (1 == upDownFlag) {
                if (0 == cNum) {
                    //默认翻到第一页
                    pStart = 1;
                    pEnd = tNum;
                } else {
                    pStart += tNum;
                    pEnd += tNum;
                }
            }
            //上翻页
            else {
                pStart -= tNum;
                pEnd -= tNum;
            }
            //根据汇总标志条件，计算总笔数、总页面
            if ("E".equals(totalFlag)) {
                tRows = pageable.total();
                tPages = (int)Math.ceil((double)tRows / tNum);
            } else if ("O".equals(totalFlag)) {
                if (0 == tRows) tRows = pageable.total();
                tPages = (int)Math.ceil((double)tRows / tNum);
            } else if ("N".equals(totalFlag)) {
                vRows = pageable.total(pStart, pEnd);
                isTotalRPPrint = false;
            }
            if (0 == vRows) {
                if (0 >= pStart || pStart > tRows) {
                    throw new BzRollBackException("0000305", "查询无返回记录");
                }
                if (pEnd > tRows) pEnd = tRows;
            } else {
                if (0 >= pStart || 0 >= vRows) {
                    throw new BzRollBackException("0000305", "查询无返回记录");
                }
                pEnd = pStart + vRows - 1;
            }
        }
        List<T> rows = pageable.slice(pStart, pEnd);
        cNum = pStart;

        respAppHead.put("PGUP_OR_PGDN", new StringField(upDownFlag + ""));
        respAppHead.put("TOTAL_NUM", new StringField(tNum + ""));
        respAppHead.put("CURRENT_NUM", new StringField(cNum + ""));
        respAppHead.put("PAGE_START", new StringField(pStart + ""));
        respAppHead.put("PAGE_END", new StringField(pEnd + ""));
        if (isTotalRPPrint) {
            respAppHead.put("TOTAL_ROWS", new StringField(tRows + ""));
            respAppHead.put("TOTAL_PAGES", new StringField(tPages + ""));
        } else {
            respAppHead.remove("TOTAL_ROWS");
            respAppHead.remove("TOTAL_PAGES");
        }

        return rows;
    }

    /**
     * 遍历pojo里面的get*或者is*方法，将属性转化成CompositeData，同时将驼峰命名的属性名转化为下划线连接的大写字符串
     * @param pojo pojo
     * @param cd cd
     */
    public static void extractPOJOToFields(Object pojo, CompositeData cd) {

        //ignore inherited method, public getter only
        Method[] getMethods = pojo.getClass().getDeclaredMethods();
        for (Method m : getMethods) {
            if (Modifier.isPublic(m.getModifiers())) {
                String methodName = m.getName();
                if (methodName.startsWith("get") || methodName.startsWith("is")) {
                    // getter method may start with "is" or "get"
                    String attrName = methodName.startsWith("is") ? methodName.substring(2) :
                            methodName.substring(3);
                    String fieldName = StrKit.camel2Underline(attrName, true);
                    try {
                        Object value = m.invoke(pojo);
                        if (null != value) {
                            cd.put(fieldName, new StringField(value.toString()));
                        }
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                }
            }
        }
    }

    public static Array pojolistToStructArray(List<?> pojoList) {

        Array arr = null;
        if (null != pojoList && 0 < pojoList.size()) {
            arr = new Array();
            CompositeData infoStruct;
            for (Object pojo : pojoList) {
                infoStruct = new CompositeData();
                extractPOJOToFields(pojo, infoStruct);
                arr.add(infoStruct);
            }
        }
        return arr;
    }

}

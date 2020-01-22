//package cn.com.bocd.opencbsboot.web.tcp.util.rpc.impl;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.input.SAXBuilder;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;
//
//import com.dcfs.teller.common.appparam.AppParamConfig;
//import com.dcfs.teller.common.data.Array;
//import com.dcfs.teller.common.data.CompositeData;
//import com.dcfs.teller.common.data.DataFactory;
//import com.dcfs.teller.common.data.Field;
//import com.dcfs.teller.common.data.FieldType;
//import com.dcfs.teller.spi.logicframe.channel.IInputPacket;
//import com.dcfs.teller.spi.logicframe.channel.IOutputPacket;
///**
// * <p>Title：青海银行报文装换实现</p>
// * <p>Description:和青海银行通信报文格式转换实现</p>
// * @date  2016-09-08
// * @update zhuwhc
// */
//public class JsPackConverterImpl implements PackConverterInterface {
//
//	private static Log log = LogFactory.getLog(JsPackConverterImpl.class);
//    private static String SUCCESS_RET_CODE = "000000";
//	private boolean flag = false;
//	private static String pin = "false";
//
//	public  void CompositeDataToOtherPack(IOutputPacket packet,CompositeData compositeData) {
//		CompositeData transferCd = transferData(compositeData);
//		byte[] xmlData = this.convert(transferCd);
//		packet.setBuff(xmlData);
//		packet.setOffset(xmlData.length);
//	}
//	 /**
//	  * 转换CompositeData 到其它报文
//	  * @param compositeData
//	  * @return
//	  */
//	private  byte[] convert(CompositeData compositeData){
//	    this.flag = false;
//	    byte[] bytes = null;
//	    StringBuffer sb = new StringBuffer(5120);
//	    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//	    sb.append("<service>\n");
//	    /**
//	     * 系统头部CompositeData
//	     */
//	    CompositeData sysHeader = DataFactory.getCompositeData();
//	    if(compositeData.contains("SYS_HEAD")){
//	    	sysHeader = compositeData.getStruct("SYS_HEAD");
//	    	if(sysHeader.contains("JSSEQ_NO")){
//	    		sysHeader.addField("SEQ_NO",sysHeader.getField("JSSEQ_NO"));
//	    		sysHeader.removeObject("JSSEQ_NO");
//	    	}
//		    sb.append("<sys-header>\n");
//		    sb.append("<data name=\"SYS_HEAD\">\n");
//		    packCompositeData(sb, sysHeader);
//		    sb.append("</data>\n");
//		    sb.append("</sys-header>\n");
//	    }else{
//	       if(log.isDebugEnabled()){
//	    	   log.debug("缺少系统头部SYS_HEAD");
//	       }
//	    }
//	    /**
//	     * 应用头部CompositeData
//	     */
//	    CompositeData appHeader = DataFactory.getCompositeData();
//	    if(compositeData.contains("APP_HEAD")){
//	    	appHeader  =compositeData.getStruct("APP_HEAD");
//	    	sb.append("<app-header>\n");
//	 	    sb.append("<data name=\"APP_HEAD\">\n");
//	 	    packCompositeData(sb, appHeader);
//	 	    sb.append("</data>\n");
//	 	    sb.append("</app-header>\n");
//	    }else{
//	    	if(log.isDebugEnabled()){
//		    	 log.debug("缺少应用头部APP_HEAD");
//		       }
//	    }
//	    /**
//	     * Local CompositeData
//	     */
//	    CompositeData localHeader = DataFactory.getCompositeData();
//	    if(compositeData.contains("LOCAL_HEAD")){
//	    	localHeader = compositeData.getStruct("LOCAL_HEAD");
//	    	sb.append("<local-header>\n");
//	  	    sb.append("<data name=\"LOCAL_HEAD\">\n");
//	  	    packCompositeData(sb, localHeader);
//	  	    sb.append("</data>\n");
//	  	    sb.append("</local-header>\n");
//	    }else{
//	    	if(log.isDebugEnabled()){
//	    		log.debug("缺少本地头部LOCAL_HEAD");
//	    	}
//	    }
//	    /**
//	     * Body CompositeData
//	     */
//	    CompositeData bodyCompositeData  = DataFactory.getCompositeData();
//	    if(compositeData.contains("Body")){
//	    	compositeData.getStruct("Body").removeObject("TRAN_HIST_DETAIL");
//	    	compositeData.getStruct("Body").removeObject("FILENAMESS");
//	    	bodyCompositeData = compositeData.getStruct("Body");
//	    	removeObjectForDynamicJournal(bodyCompositeData);
//
//
//	    	sb.append("<body>\n");
//	    	packBodyCompositeData(sb, bodyCompositeData);
//	 	    sb.append("</body>\n");
//	    }else{
//	    	if(log.isDebugEnabled()){
//	    		log.debug("Body 中没有数据");
//	    	}
//	    }
//	    this.flag = true;
//	    sb.append("</service>\n");
//	    try {
//	    	bytes = sb.toString().getBytes("UTF-8");
//	    } catch (Exception e) {
//	        log.error("组包出错",e);
//	    }
//	    return bytes;
//	  }
//
//	/**
//	 * 为实现动态流水生成 移除动态字段
//	 * Add By qingt
//	 * @param bodyCompositeData
//	 */
//	private void removeObjectForDynamicJournal(CompositeData bodyCompositeData) {
//		// 备注
//		/*if(bodyCompositeData.contains("REMARK")){
//			bodyCompositeData.removeObject("REMARK");
//		}*/
//		// 流水通用卡号
//		if(bodyCompositeData.contains("JournalCARDNO")){
//			bodyCompositeData.removeObject("JournalCARDNO");
//		}
//		// 流水通用证件类型
//		if(bodyCompositeData.contains("JournalDOCUMENTTYPE")){
//			bodyCompositeData.removeObject("JournalDOCUMENTTYPE");
//		}
//		// 流水通用证件号码
//		if(bodyCompositeData.contains("JournalDOCUMENTNO")){
//			bodyCompositeData.removeObject("JournalDOCUMENTNO");
//		}
//		// 跨行或者同行转账是 转入账户 的 卡号
//		if(bodyCompositeData.contains("JournalTLFACCTNO")){
//			bodyCompositeData.removeObject("JournalTLFACCTNO");
//		}
//		//账户姓名
//		if(bodyCompositeData.contains("ACCTNAME")){
//			bodyCompositeData.removeObject("ACCTNAME");
//		}
//		//账户本人电话号码
//		if(bodyCompositeData.contains("TELEPHONE")){
//			bodyCompositeData.removeObject("TELEPHONE");
//		}
//		//对方账户名称
//		if(bodyCompositeData.contains("TLFAcctName")){
//			bodyCompositeData.removeObject("TLFAcctName");
//		}
//		// 交易金额
//		if(bodyCompositeData.contains("JournalAMT")){
//			bodyCompositeData.removeObject("JournalAMT");
//		}
//	}
//	/**
//	   * CompositeData 类型转换
//	   * @param sb
//	   * @param data
//	   * @param xmlConvert
//	   */
//	private void packCompositeData(StringBuffer sb, CompositeData data){
//		    sb.append("<struct>\n");
//		    Iterator<String> it = (Iterator<String>) data.iterator();
//		    while (it.hasNext()) {
//		      String curr_name = (String)it.next();
//		      Object curr_field = data.getObject(curr_name);
//		      if (this.flag) {
//		          packObject(sb, curr_name, curr_field);
//		      }else {
//		    	  packObject(sb, curr_name, curr_field);
//		      }
//		    }
//		    sb.append("</struct>\n");
//		  }
//	  /**
//	   * Body 中 CompositeData 的转换
//	   * @param sb
//	   * @param data
//	   * @param xmlConvert
//	   */
//	private void packBodyCompositeData(StringBuffer sb, CompositeData data){
//			  Iterator<String> it = (Iterator<String>) data.iterator();
//			    while (it.hasNext()) {
//			      String curr_name = (String)it.next();
//			      Object curr_field = data.getObject(curr_name);
//			      packBodyObject(sb, curr_name, curr_field);
//	    	    }
//    }
//	  /**
//	   * Array 类型转换
//	   * @param sb
//	   * @param dataname
//	   * @param data
//	   * @param xmlConvert
//	   */
//	private void packArrayData(StringBuffer sb, String dataname, Array data){
//		    sb.append("<data name=\"" + dataname + "\">\n");
//		    sb.append("<array>\n");
//		    int i = 0; for (int size = data.size(); i < size; i++) {
//		      Object item = data.getObject(i);
//		      if ((item instanceof CompositeData)){
//		        packObject(sb, "struct", item);
//		      }else if ((item instanceof Array)){
//		        packObject(sb, "array", item);
//		      }else if ((item instanceof Field)){
//		        packObject(sb, "field", item);
//		      }
//		    }
//		    sb.append("</array>\n");
//		    sb.append("</data>\n");
//   }
//      /**
//       * Filed 类型转换
//       * @param sb
//       * @param dataname
//       * @param data
//       */
//    private void packFieldData(StringBuffer sb, String dataname, Field data){
//		    String value = "";
//		    int len = 0;
//		    int scale = 0;
//		    if (data != null) {
//		      len = data.getAttr().getLength();
//		      scale = data.getAttr().getScale();
//		      value = data.getValue().toString();
//		    }
//		    sb.append("<data name=\"" + dataname + "\">\n");
//		    /*// 此处是为了组装工作秘钥的节点   按照各项目实际情况做调整
//		    if("WS_ID".equals(dataname)){
//		    	Map<String,String> map=AppParamConfig.getInstance().getParam("OutSecretParam");
//		    	String design_id = map.get("DESIGN_ID").toString();
//		    	value=design_id+"."+value+".zpk";
//		    }*/
//		    sb.append("<field type=\"" + getFieldType(data) + "\" length=\"" + len + "\" scale=\"" + scale + "\">" + value + "</field>\n");
//		    sb.append("</data>\n");
//    }
//    /**
//	 * 功能：获取域的数据类型
//	 *
//	 * @param field
//	 * @return
//	 */
//	private String getFieldType(Field field){
//		if (field.getFieldType() == FieldType.FIELD_STRING){
//			return "string";
//		}else if (field.getFieldType() == FieldType.FIELD_INT)
//			return "int";
//		else if (field.getFieldType() == FieldType.FIELD_DOUBLE){
//			return "double";
//		}else if (field.getFieldType() == FieldType.FIELD_LONG){
//			return "long";
//		}else if (field.getFieldType() == FieldType.FIELD_IMAGE){
//			return "image";
//		}else if (field.getFieldType() == FieldType.FIELD_BYTE){
//			return "byte";
//		}else if (field.getFieldType() == FieldType.FIELD_SHORT){
//			return "short";
//		}else if (field.getFieldType() == FieldType.FIELD_INT24){
//			return "int24";
//		}else if (field.getFieldType() == FieldType.FIELD_FLOAT){
//			return "float";
//		}else
//			return "string";
//	 }
//      /**
//       * 判断类型
//       * @param sb
//       * @param name
//       * @param obj
//       * @param xmlConvert
//       */
//    private void packObject(StringBuffer sb, String name, Object obj){
//		    if (((obj instanceof Field)) || (obj == null)) {
//		      packFieldData(sb, name, (Field)obj);
//		    }else if ((obj instanceof Array)){
//		      packArrayData(sb, name, (Array)obj);
//		    }else if ((obj instanceof CompositeData)) {
//		      packCompositeData(sb, (CompositeData)obj);
//		    }
//    }
//      /**
//       * Body 体中类型判断
//       * @param sb
//       * @param name
//       * @param obj
//       * @param xmlConvert
//       */
//   private void packBodyObject(StringBuffer sb, String name, Object obj){
//		    if (((obj instanceof Field)) || (obj == null)) {
//		    	packFieldData(sb, name, (Field)obj);
//		    }else if ((obj instanceof CompositeData)){
//		      packCompositeData(sb, (CompositeData)obj);
//		    }else if ((obj instanceof Array)) {
//		      packArrayData(sb, name, (Array)obj);
//		    }
//   }
//	@Override
//   public void OtherPackCompositeData(IInputPacket packet,CompositeData compositeData) {
//		parse(packet.getBuff(),compositeData);
//   }
//	/**
//	 * 解析XML
//	 * @param xml
//	 * @return
//	 */
//   private CompositeData parse(byte[] xml,CompositeData data){
//	    Map<String, Field> list = new HashMap<String, Field>();
//	    try {
//	      SAXBuilder builder = new SAXBuilder();
//	      Document doc = builder.build(new InputStreamReader(new ByteArrayInputStream(xml)));
//	      Element root = doc.getRootElement();
//	      parseToCd(root, list, data);
//	    } catch (Exception e) {
//	      log.error("拆包异常", e);
//	    }
//	    if(data.contains("SYS_HEAD")){
//	    	CompositeData sysHeadCompositeData = data.getStruct("SYS_HEAD");
//	    	if(sysHeadCompositeData.contains("RET")){
//	    		CompositeData retCompositeData = sysHeadCompositeData.getStruct("RET");
//	    		if(SUCCESS_RET_CODE.equals(retCompositeData.getField("RET_CODE").strValue())){
//	    			data.getStruct("SYS_HEAD").addField("RET_STATUS", DataFactory.getStringField("S"));
//	    		}else{
//    			 data.getStruct("SYS_HEAD").addField("RET_STATUS", DataFactory.getStringField("F"));
//    			 /**
//    			  * 解决失败以后事件层次不能够再RET 中获取RET_MSG和RET_CODE的问题
//    			  */
//    			 String retMsg = data.getStruct("SYS_HEAD").getStruct("RET").getField("RET_MSG").strValue();
//    			 String retCode = data.getStruct("SYS_HEAD").getStruct("RET").getField("RET_CODE").strValue();
//    			 /**
//    			  * 增加调用接口失败时错误信息添加渠道信息
//    			  */
//    			 if (!"000000".equals(retCode)) {
//    				 String serviceCode = sysHeadCompositeData.getField("SERVICE_CODE").strValue();
//    				 Map<String, String> map = AppParamConfig.getInstance().getParam("MESSAGE_CHANNEL_MAPPING");
//    				 String channelMap = map.get(serviceCode);
//    				 retMsg = channelMap + "-" + retCode + "-" + retMsg;
//    			 }
//    			 CompositeData compositeData = DataFactory.getCompositeData();
//
//    			 compositeData.addField("RET_MSG", DataFactory.getField(retMsg));
//    			 compositeData.addField("RET_CODE", DataFactory.getField(retCode));
//
//    			 data.getStruct("SYS_HEAD").addStruct("RET",compositeData);
//	    		}
//	    	}
//	    }
//	    return data;
//   }
//	/**
//	 * 解析报文
//	 * @param root
//	 * @param list
//	 * @param data
//	 */
//	private void parseToCd(Element root, Map<String, Field> list, CompositeData data){
//	    String[] structNames = { "SYS_HEAD", "APP_HEAD", "LOCAL_HEAD","BODY" };
//	    String[] structs = { "sys-header", "app-header", "local-header", "body" };
//
//	    for (int i = 0; i < 3; i++) {
//	      data.addStruct(structNames[i], DataFactory.getCompositeData());
//	    }
//	    if (root.getChild("sys-header").getChild("data") != null) {
//
//	      Element elBody = root.getChild("sys-header").getChild("data").getChild("struct");
//	      parseSysStruct(data, elBody);
//	    } else {
//	      return;
//	    }
//	    for (int i = 1; i < structs.length; i++) {
//	    	if(root.getChild(structs[i])!= null)
//	    	{
//		      if (structs[i].equals("body")) {
//		        Element elBody = root.getChild(structs[i]);
//		        parseStruct(data,elBody,structNames[i]);
//		      }else
//		    	  if (root.getChild(structs[i]).getChild("data") != null) {
//		    		  Element elBody = root.getChild(structs[i]).getChild("data").getChild("struct");
//		    		  parseStruct(data, elBody,structNames[i]);
//
//		    	  }
//		    	}
//	    	}
//	  }
//	/**
//	 * 解析SysStruct
//	 * @param data
//	 * @param elBody
//	 * @return
//	 */
//	private void parseSysStruct(CompositeData data, Element elBody){
//	      int num = 0;
//	      String service_code = "";
//	      String service_scene = "";
//	      String service_version = "";
//	      String ret_code = "";
//	      String ret_msg = "";
//	      List<Element> structList = elBody.getChildren();
//	      if (structList.size() > 0) {
//	        for (int i = 0; i < structList.size(); i++) {
//	          Element cl = (Element)structList.get(i);
//	          if ("RET".equals(cl.getAttributeValue("name"))) {
//	              Element ret = cl.getChild("array").getChild("struct");
//	              CompositeData cd = DataFactory.getCompositeData();
//	  			  data.getStruct("SYS_HEAD").addStruct("RET", cd);
//	              List<Element> retlist = ret.getChildren();
//	              for (int j = 0; j < retlist.size(); j++) {
//	              	 Element retcl = (Element)retlist.get(j);
//	              	 if ("RET_CODE".equals(retcl.getAttributeValue("name"))) {
//	              		 ret_code = retcl.getChild("field").getText();
//	              		 cd.addField("RET_CODE",DataFactory.getStringField(ret_code));
//	  			  		 }
//	              	 if ("RET_MSG".equals(retcl.getAttributeValue("name"))) {
//	              		 ret_msg = retcl.getChild("field").getText();
//	              		 cd.addField("RET_MSG",DataFactory.getStringField(ret_msg));
//	  			  	 }
//	              }
//	          }
//	          if ("SERVICE_CODE".equals(cl.getAttributeValue("name"))) {
//	            service_code = cl.getChild("field").getText();
//	            data.getStruct("SYS_HEAD").addField(cl.getAttributeValue("name"),DataFactory.getStringField(cl.getChild("field").getText()));
//	            num++;
//	          } else if ("MESSAGE_TYPE".equals(cl.getAttributeValue("name"))){
//	            service_scene = cl.getChild("field").getText();
//	            num++;
//	          } else if ("MESSAGE_CODE".equals(cl.getAttributeValue("name"))){
//	            service_version = cl.getChild("field").getText();
//	            num++;
//	          }else{
//	        	  if (cl.getChild("field") != null){
//	        		  data.getStruct("SYS_HEAD").addField(cl.getAttributeValue("name"),DataFactory.getStringField(cl.getChild("field").getText()));
//	        	  }
//	          }
//	          /*if (num == 3) {
//	        	  break;
//	          }*/
//	        }
//	        if (num < 3) {
//	          log.error("ESB服务码、场景号、版本号解析异常！");
//	        }
//	      } else {
//	        log.error("ESB服务码、场景号、版本号解析异常！");
//	      }
//	 }
//    /**
//     * 解析Struct
//     * @param data
//     * @param elBody
//     * @param xmlConvert
//     * @param structName
//     */
//	  @SuppressWarnings("unchecked")
//	private void parseStruct(CompositeData data, Element elBody, String structName) {
//	      List<Element> structList = elBody.getChildren();
//	      for (int i = 0; i < structList.size(); i++) {
//	        Element cl = (Element)structList.get(i);
//	        String fieldName = cl.getAttributeValue("name");
//	        if (cl.getChild("field") != null){
//	          parseField(data, cl, structName);
//	        }else if (cl.getChild("array") != null) {
//	          parseArray(data, cl, structName);
//	        }else if (cl.getChild("struct") != null) {
//	        	CompositeData compositeData = DataFactory.getCompositeData();
//	        	compositeData.addStruct(fieldName, DataFactory.getCompositeData());
//	        	parseInStruct(compositeData, cl, fieldName);
//	        	//data.addStruct(structName, DataFactory.getCompositeData());
//	        	data.getStruct(structName).addStruct(fieldName, compositeData.getStruct(fieldName));
//	        }
//	      }
//	    }
//	  /**
//	     * 解析Struct
//	     * @param data
//	     * @param elBody
//	     * @param xmlConvert
//	     * @param structName
//	     */
//		  @SuppressWarnings("unchecked")
//		private void parseInStruct(CompositeData data, Element elBody, String structName) {
//			  List<Element> structList = elBody.getChild("struct").getChildren();
//		      for (int i = 0; i < structList.size(); i++) {
//		        Element cl = (Element)structList.get(i);
//		        String fieldName = cl.getAttributeValue("name");
//		        if (cl.getChild("field") != null){
//		          parseField(data, cl, structName);
//		        }else if (cl.getChild("array") != null) {
//		          parseArray(data, cl, structName);
//		        }else if (cl.getChild("struct") != null) {
//		        	CompositeData compositeData = DataFactory.getCompositeData();
//		        	parseInStruct(compositeData, cl, fieldName);
//		        	data.getStruct(structName).addStruct(fieldName, compositeData.getStruct(fieldName));
//
//
//		        }
//		      }
//		    }
//	/**
//	 * 解析Field
//	 * @param data
//	 * @param cl
//	 * @param scrLocation
//	 * @param xmlConvert
//	 */
//	private void parseField(CompositeData data, Element cl, String scrLocation){
//	      String fieldName = cl.getAttributeValue("name");
//	      //余额明细查询打印修改 --wangm
//	      if ("REFERENCE".equals(fieldName)) {
//	    	  data.getStruct(scrLocation).addField("FILENAMESS",DataFactory.getStringField( cl.getChild("field").getText()));
//	      }else {
//
//	    	  data.getStruct(scrLocation).addField(fieldName,DataFactory.getStringField( cl.getChild("field").getText()));
//	      }
//	}
//    /**
//     * 解析InArray
//     * @param arrayStruct
//     * @param arrayName
//     * @param cl
//     * @param xmlConvert
//     */
//	    @SuppressWarnings("unchecked")
//	private void parseInArray(CompositeData arrayStruct, String arrayName, Element cl){
//	        List<Element> list = cl.getChildren();
//	        for (int i = 0; i < list.size(); i++) {
//	          Element e = (Element)list.get(i);
//	          if (e.getChild("field") != null){
//	          	    String fieldName = e.getAttributeValue("name");
//	                 String dataValue = e.getChild("field").getText();
//	                 arrayStruct.addField(fieldName, DataFactory.getStringField(dataValue));
//	            }else if (e.getChild("array") != null) {
//	  	          parseArray(arrayStruct,  e , arrayName);
//	  	        }
//	  	    }
//	}
//   /**
//    * 解析Array
//    * @param data
//    * @param cl
//    * @param scrLocation
//    * @param xmlConvert
//    */
//	    @SuppressWarnings("unchecked")
//	private void parseArray(CompositeData data, Element cl, String scrLocation){
//	      List<Element> arrayList = cl.getChild("array").getChildren();
//	      String arrayName = cl.getAttributeValue("name");
//	      Array array = DataFactory.getArray();
//          for (int j = 0; j < arrayList.size(); j++) {
//            Element astruct = (Element)arrayList.get(j);
//            CompositeData arrayStruct = DataFactory.getCompositeData();
//            parseInArray(arrayStruct, arrayName, astruct);
//            array.addStruct(j, arrayStruct);
//          }
//           data.getStruct(scrLocation).addArray(arrayName,array);
//    }
//
//    /**
//     * 重组数据
//     * 将映射数据SEND_SYSHEAD、SEND_APPHEAD、SEND_LOCALHEAD放入到SYS_HEAD、APP_HEAD、LOCAL_HEAD
//     * @param sendCd
//     */
//	private CompositeData transferData(CompositeData input){
//		    CompositeData sendCd = DataFactory.getCompositeData();
//			if(input != null){
//				if(input.contains("SEND_SYSHEAD")){
//				    sendCd.addStruct("SYS_HEAD", input.getStruct("SEND_SYSHEAD"));
//				}
//				if(input.contains("SEND_APPHEAD")){
//						sendCd.addStruct("APP_HEAD", input.getStruct("SEND_APPHEAD"));
//
//				}
//				if(input.contains("SEND_LOCALHEAD")){
//					sendCd.addStruct("LOCAL_HEAD", input.getStruct("SEND_LOCALHEAD"));
//
//				}
//				if(input.contains("BODY")){
//					sendCd.addStruct("Body", input.getStruct("BODY"));
//				}
//			}
//			return sendCd;
//		}
//
//}

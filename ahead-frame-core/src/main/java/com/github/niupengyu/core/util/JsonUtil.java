/**
 * 
 */
package com.github.niupengyu.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;

//************************************************************************
//【文件名】　　　　Json对象工具类
/**
 * <p>文件名：JsonUtil.java</p>
 * <p>说     明：Json对象常用方法实现</p>
 *
 * @author		
 * @version　　  	2011-2-28
 */
//************************************************************************
//【作　成】 　　　　 www.sh-db.com.cn　　　　	2011-2-28（R1.00）
//************************************************************************
public class JsonUtil {

    /**
     * <p>方法名：objToJSON </p>.
     * <p>说     明：对象转化JSONObject</p>
     *
     * @param object
     * @return
     * JSONObject
     * @author yuegy
     * @date   2011-2-28
     */
	/*@Deprecated
    public static JSONObject objToJSON(Object object) {
        if (StringUtil.isNull(object)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        if (object instanceof List<?>) {
            jsonObject = listToJSON((List<?>) object);
        } else {
            jsonObject = JSONObject.fromObject(object);
        }
        return jsonObject;
    }

    *//**
     * <p>方法名：listToJSON </p>.
     * <p>说     明：List转化JSONObject</p>
     *
     * @param list
     * @return
     * JSONObject
     * @author yuegy
     * @date   2011-2-28
     *//*
    @Deprecated
    private static JSONObject listToJSON(List<?> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", list.size());
        map.put("root", list);
        return JSONObject.fromObject(map);
    }

    *//**
     * <p>方法名：strToJSON </p>.
     * <p>说     明：字符串转化JSONObject</p>
     *
     * @param str
     * @return
     * JSONObject
     * @author yuegy
     * @date   2011-2-28
     *//*
    @Deprecated
    public static JSONObject strToJSON(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    *//**
     * <p>方法名：objToJSONStr </p>.
     * <p>说     明：对象转化JSONObject字符串</p>
     *
     * @param object
     * @return
     * String
     * @author yuegy
     * @date   2011-3-1
     *//*
    @Deprecated
    public static String objToJSONStr(Object object) {
        JSONObject jsonObject = objToJSON(object);
        if (StringUtil.isNull(jsonObject)) {
            return "";
        } else if (object instanceof List<?>) {
            return ObjUtil.toStr(jsonObject.get("root"));
        } else {
            return ObjUtil.toStr(jsonObject.toString());
        }
    }*/
    /**
	 * 更具所提供属性名获取json牛鹏宇
	 * @param mains
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public static String parseJsonForAndroid(List<?> mains,
			String[] array) throws Exception {
		if(StringUtil.listIsNull(mains))
		{
			return "[]";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		int index=0;
		for(Object obj:mains){
			Class<?> c=obj.getClass();
			sb.append("{");
			int i=0;
			for(String str:array){
				String[] s=str.split("\\.");
				Field pfield=null;
				Object fieldobj=null;
				if(s[0].equals("id")){
					fieldobj=ClassUtil.invokeGetMethod(obj, s[0]);
				}else{
					pfield=c.getDeclaredField(s[0]);
					//属性的get方法
					Method getObjmethod=ClassUtil.getMethod(c, pfield);
					fieldobj=getObjmethod.invoke(obj, new Object[]{});
				}
				if(s.length==1){
					ClassUtil.setMethod(obj.getClass(), pfield);
					if(!StringUtil.isNull(fieldobj)){
//						if(StringUtil.isNull(pfield)){
//							sb.append($(s[0]));
//							sb.append(":");
//							sb.append($(""));
//						}else
						if((!StringUtil.isNull(pfield))&&pfield.getType().getName().equals("java.util.Date")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
								String ds = simpleDateFormat.format(fieldobj);
								sb.append(N.$(s[0]));
								sb.append(":");
								sb.append(N.$(ds));
								
						}else{
							sb.append(N.$(s[0]));
							sb.append(":");
							sb.append(N.$(fieldobj));
						}
					}else{
						sb.append(N.$(s[0]));
						sb.append(":");
						sb.append(N.$(""));
					}
				}else{
					if(!StringUtil.isNull(fieldobj)){
						Field field=fieldobj.getClass().getDeclaredField(s[1]);
						Method getmethod=ClassUtil.getMethod(fieldobj.getClass(), field);
						sb.append(N.$(s[0]+"."+s[1]));
						sb.append(":");
						Object fo=getmethod.invoke(fieldobj, new Object[]{});
						if(!StringUtil.isNull(fo)){
							
							sb.append(N.$(fo));
							
						}else{
							sb.append(N.$(""));
						}
					}else{
						sb.append(N.$(s[0]+"."+s[1]));
						sb.append(":");
						sb.append(N.$(""));
					}
				}
				
				i++;
				if(i<array.length){
					sb.append(",");
				}
			}
			sb.append("}");
			index++;
			if(index<mains.size()){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}


	/**
	 * 将map转换成json
	 * @param classFiledsQuery
	 */
	public static String mapToJson(Map<String, String> classFiledsQuery) {
		if(StringUtil.mapIsNull(classFiledsQuery))
		{
			return "[]";
		}

		StringBuffer sb=new StringBuffer();
		sb.append("[");
			sb.append("{");
			int index=0;
			for(Map.Entry<String, String> entry:classFiledsQuery.entrySet()){
				sb.append(N.$(entry.getKey()));
				sb.append(":");
				sb.append(N.$(entry.getValue()));
				index++;
				if(index<classFiledsQuery.size()){
					sb.append(",");
				}
			}
			sb.append("}");
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 功能描述:讲list转换成json
	 * @param
	 * @param strs
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static String listToJson(List<?> list, String[] strs) throws Exception {
		if(StringUtil.listIsNull(list))
		{
			return "[]";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("[");
			int i=0;
			for(Object obj:list){
				int index=0;
				sb.append("{");
				for(String str:strs){
					sb.append(N.$(str));
					sb.append(":");
					sb.append(N.$(ClassUtil.invokeGetMethod(obj, str)));
					index++;
					if(index<strs.length){
						sb.append(",");
					}
				}
				sb.append("}");
				i++;
				if(i<list.size()){
					sb.append(",");
				}
			}
		sb.append("]");
		return sb.toString();
	}
    /**
	 * 更具所提供属性名获取json牛鹏宇 可自定义名称。
	 * @param mains
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public static String parseJson_For(List<?> mains,
			String[] array) throws Exception {
		if(StringUtil.listIsNull(mains))
		{
			return "[]";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		int index=0;
		for(Object obj:mains){
			sb.append(beanToJson(obj, array));
			index++;
			if(index<mains.size()){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 功能描述:将一个实体类转换成json
	 * @param obj
	 * @param array
	 * @throws Exception
	 */
	public static String beanToJson(Object obj, String[] array) throws Exception {
		StringBuffer sb=new StringBuffer();
		Class<?> c=obj.getClass();
		sb.append("{");
		int i=0;
		for(String str:array){
			String[] s=str.split(":");
			//属性
			String[] st=s[1].split("\\.");
			Field pfield=null;
			Object fieldobj=null;
			if(st[0].equals("id")){
				fieldobj=ClassUtil.invokeGetMethod(obj, st[0]);
			}else{
				pfield=c.getDeclaredField(st[0]);
				//属性的get方法
				Method getObjmethod=ClassUtil.getMethod(c, pfield);
				fieldobj=getObjmethod.invoke(obj, new Object[]{});
			}
			if(st.length==1){
				if(!StringUtil.isNull(fieldobj)){
//					if(StringUtil.isNull(pfield)){
//						sb.append(N.$(s[0]));
//						sb.append(":");
//						sb.append(N.$(""));
//					}else
					if((!StringUtil.isNull(pfield))&&pfield.getType().getName().equals("java.util.Date")){
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
							String ds = simpleDateFormat.format(fieldobj);
							sb.append(N.$(s[0]));
							sb.append(":");
							sb.append(N.$(ds));
							
					}else{
						sb.append(N.$(s[0]));
						sb.append(":");
						sb.append(N.$(fieldobj));
					}
				}else{
					sb.append(N.$(s[0]));
					sb.append(":");
					sb.append(N.$(null));
				}
			}else{
				if(!StringUtil.isNull(fieldobj)){
					Field field=fieldobj.getClass().getDeclaredField(st[1]);
					Method getmethod=ClassUtil.getMethod(fieldobj.getClass(), field);
					sb.append(N.$(s[0]));
					sb.append(":");
					Object fo=getmethod.invoke(fieldobj, new Object[]{});
					if(!StringUtil.isNull(fo)){
						
						sb.append(N.$(fo));
						
					}else{
						sb.append(N.$(""));
					}
				}else{
					sb.append(N.$(s[0]));
					sb.append(":");
					sb.append(N.$(""));
				}
			}
			
			i++;
			if(i<array.length){
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 功能描述:bean转换成json集合
	 * @param role
	 * @param arr
	 * @return
	 * @throws Exception 
	 */
	public static String beanToJsonList(Object role, String[] arr) throws Exception {
		// TODO Auto-generated method stub
		return  "["+beanToJson(role,arr)+"]";
	}
}

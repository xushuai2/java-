package demo.common;

import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import java.util.List;  
import java.util.Map;  
import java.util.Set; 

public class BeanUtils {
	public static <T> T copyNotNullProperties(T source, T target) throws Exception{
		if(target == null)  
            return null;
		Field[] tFields = target.getClass().getDeclaredFields();  
        Field[] sFields = source.getClass().getDeclaredFields();
        try {  
            for (Field field : tFields) {  
                String fieldName = field.getName();  
                if (fieldName.equals("serialVersionUID"))  
                    continue;  
                if (field.getType() == Map.class)  
                    continue;  
                if (field.getType() == Set.class)  
                    continue;  
                if (field.getType() == List.class)  
                    continue;  
                for (Field sField : sFields) {  
                    if (!sField.getName().equals(fieldName)) {  
                        continue;  
                    }  
                    Class type = field.getType();  
                    String setName = getSetMethodName(fieldName);  
                    Method tMethod = target.getClass().getMethod(setName, new Class[] { type });  
                    String getName = getGetMethodName(fieldName);  
                    Method sMethod = source.getClass().getMethod(getName, null);  
                    Object setterValue = sMethod.invoke(source, null);
                    if(setterValue!=null){
                    	tMethod.invoke(target, new Object[] { setterValue });  
                    }else{
                    	break;
                    }
                }  
            }  
        } catch (Exception e) {  
            throw new Exception("设置参数信息发生异常", e);  
        }  
        return target;  
	}
	private static String getGetMethodName(String fieldName) {  
        fieldName = replaceFirstCharToUpper(fieldName);  
        return "get" + fieldName;  
    }  
  
    private static String getSetMethodName(String fieldName) {  
        fieldName = replaceFirstCharToUpper(fieldName);  
        return "set" + fieldName;  
    }  
  
    private static String replaceFirstCharToUpper(String fieldName) {  
        char[] chars = new char[1];  
        chars[0] = fieldName.charAt(0);  
        String temp = new String(chars);  
        if (chars[0] >= 'a' && chars[0] <= 'z') {  
            fieldName = fieldName.replaceFirst(temp, temp.toUpperCase());  
        }  
        return fieldName;  
    }  

}

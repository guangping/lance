package com.ztesoft.common.util.convert;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author AyaKoizumi
 * @date 101029
 * @desc 对象序列化和反序列化,其中传送的字符串为BASE64编码
 * 
 * */

public class ObjectSerialUtils {
	public static void main(String []args) throws IOException, ClassNotFoundException{
		ObjectSerialUtils bo=new ObjectSerialUtils();
		HashMap vo=new HashMap();
		vo.put("test1", "1");
		vo.put("test2", "2");
		String c=bo.writeObjectToString(vo);
		Object vo1=bo.readStringToObject(c);
		System.out.print(vo1);
	}

	public Object readStringToObject(String s) throws IOException, ClassNotFoundException{
		ObjectInputStream ois =null;
		try{
			String _s=s;
	        byte [] data = this.getByteFromBASE64(_s);
	        ois = new ObjectInputStream(new ByteArrayInputStream(data));
	        Object o  = ois.readObject();
	        return o;
		}finally{
			ois.close();
    	}
    }
    public String writeObjectToString(Object obj) throws IOException {
    	ByteArrayOutputStream baos =null;
    	ObjectOutputStream oos =null;
    	try{
	        baos = new ByteArrayOutputStream();
	        oos = new ObjectOutputStream( baos );
	        oos.writeObject(obj);
	        return this.getBASE64(baos.toByteArray());
    	}
    	finally{
    		baos.close();
    		oos.close();
    	}
    }
    
	
	
	//String/byte[]--------->String
	public static String getBASE64(byte[] inByte) { 
		BASE64Encoder enc=new BASE64Encoder();
        String strObj=enc.encode(inByte);
        return strObj;
	}
	public static byte[] getByteFromBASE64(String s) { 
		if (s == null) return null; 
		BASE64Decoder decoder = new BASE64Decoder(); 
		try { 
	    	byte[] b = decoder.decodeBuffer(s); 
	    	return b; 
		} catch (Exception e) { 
			return null; 
		} 
	}
	//------------------------String-----------------------------------
	public static String getBASE64(String s) { 
    	if (s == null) return null; 
    	return (new sun.misc.BASE64Encoder()).encode( s.getBytes() ); 
    }
	public static String getFromBASE64(String s) { 
		if (s == null) return null; 
		BASE64Decoder decoder = new BASE64Decoder(); 
		try { 
	    	byte[] b = decoder.decodeBuffer(s); 
	    	return new String(b); 
		} catch (Exception e) { 
			return null; 
		} 
	}
}

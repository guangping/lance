package com.flickr.test.file;

import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-10-29 16:18
 * To change this template use File | Settings | File Templates.
 */
public class Compare {


    @Test
    public void run() {
        System.out.println("start===>"+System.currentTimeMillis());
        File file = new File("F:\\git\\lance\\trunk\\flickr\\src\\test\\resources\\data\\2input.data");
        File outputFile = new File("F:\\output.data");
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hadle(file, outputFile);
        System.out.println("end===>"+System.currentTimeMillis());
    }


    public void hadle(File inputFile, File outputFile) {
        int i=0;
        Map<String, String> map = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = null;
            String items[] = null;
            while ((line = reader.readLine()) != null) {
                items = line.split(",");
                if(null!=items && items.length==2){
                    map.put(items[0], items[1]);
                }
                i++;
            }
            reader.close();
            //输出文件
            System.out.println("记录数:"+map.size());
            Map sortMap = sortMapByKey(map);
            Set<Map.Entry<String, String>> set = sortMap.entrySet();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile));
            for (Map.Entry<String, String> entry : set) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //比较器类
    public static class MapKeyComparator implements Comparator<String> {
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

}

package com.github.lance;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 21:57
 * To change this template use File | Settings | File Templates.
 */
public class MapSortTest {


    @BeforeClass
    public void setUp() throws Exception {


    }

    @Test
    public void sort(){
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("house",40);
        map.put("max",4);
        map.put("hh",90);
        map.put("sex",45);

        System.out.println("排序前==>"+String.valueOf(map));
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());


        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        for(int i = 0; i < list.size(); i++) {
            String id = list.get(i).toString();
            System.out.println(id);
        }
    }
}

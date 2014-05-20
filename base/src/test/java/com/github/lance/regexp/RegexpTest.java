package com.github.lance.regexp;

import com.framework.utils.RegExpUtils;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-20 22:38
 * To change this template use File | Settings | File Templates.
 */
public class RegexpTest {

    @Test
    public void run(){
        System.out.println(RegExpUtils.compileJAk(RegExpUtils.NO_NG_NUM,"1"));
    }
}

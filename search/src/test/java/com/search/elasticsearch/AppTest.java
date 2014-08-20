package com.search.elasticsearch;

import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-20 10:01
 * To change this template use File | Settings | File Templates.
 */
public class AppTest extends Date {


    public boolean run() {
        int x = 1;
        return x == 1 ? true : false;
    }

    @Test
    public void print(){
        System.out.println("=========>"+super.getClass().getName());
    }
}

interface Playable {
    void play();
}
interface Bounceable {
    void play();
}
interface Rollable extends Playable, Bounceable {
    Ball ball = new Ball("PingPang");
}
class Ball implements Rollable {
    private String name;
    public String getName() {
        return name;
    }
    public Ball(String name) {
        this.name = name;
    }
    public void play() {
        //ball = new Ball("Football");
        System.out.println(ball.getName());
    }
}

interface A{
    String s="";
}

class B implements A{

}
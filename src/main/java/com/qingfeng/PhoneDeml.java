package com.qingfeng;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

public class PhoneDeml {

    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {

//        System.out.println(test("123456789"));
        System.out.println(verify("123456789", "13303"));
    }

    //返回验证码
    public static String test(String phone){
        String round = round();
        //判断是否有这个手机号
        if(jedis.hexists(phone,"num")){
            //没有则加入发送的次数和 验证码
            jedis.hset(phone,"num","1");
            jedis.hset(phone,"yanzhengma",round);
        }else{
            //查看验证码是否超过三次，没过期加入一个新的验证码
            Long aLong = jedis.hincrBy(round(), "num", 1);
            jedis.hset(phone,"yanzhengma",round);
            if(aLong>3) {
                jedis.close();
                return "超出三次了";
            }
        }
        //没超过三次则加入过期时间
        jedis.expire(phone,2*60);
        return  round;
    }

    //验证验证码
    public static String verify(String phone,String code){
        String yanzhengma = jedis.hget(phone, "yanzhengma");
        if(yanzhengma.equals(code)) return "成功";
        return "失败";
    }

    public static String round(){
        Random random = new Random();
        String srt="";
        for (int i = 0; i < 6; i++) {
            srt+= random.nextInt(10);
        }

        return srt;
    }
}

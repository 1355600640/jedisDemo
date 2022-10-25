package com.qingfeng;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class jedis {

    //创建 jedis 对象，传入地址和端口号
    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        //测试连通
        String ping = jedis.ping();
        System.out.println("ping = " + ping);


    }
    //测试字符串
    @Test
    public void test(){
        //创建 jedis 对象，传入地址和端口号
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //传入一个键值对
        jedis.set("user:1","张三");
        //由键输出一个值
        String s = jedis.get("user:1");
        System.out.println(s);

        //插入多个键值对
        jedis.mset("user:2","李四","user:3","王五");
        //由键取出多个值
        List<String> mget = jedis.mget("user:2", "user:3");
        System.out.println(mget);

        //获取所有的键
        Set<String> keys = jedis.keys("*");
        keys.forEach(System.out::println);
    }

    //测试list集合
    @Test
    public void test2(){
        //往里面添加集合
        jedis.lpush("user","张三","李四","王五");

        //取出所有集合
        List<String> user = jedis.lrange("user", 0, -1);
        System.out.println(user);
    }

    //测试set集合
    @Test
    public void test3(){
        //往set集合添加元素,不会添加重复都值
        jedis.sadd("user","张三","李四","张三");

        //删除key中的一个值
        jedis.srem("user","李四");

        //取出key中的所有value
        Set<String> user = jedis.smembers("user");
        System.out.println(user);
    }

    //测试hash
    @Test
    public void test4(){
        //往hash添加元素
        jedis.hset("user:1","name","张三");
        jedis.hset("user:1","age","20");


        //取出所有键和值
        System.out.println(jedis.hkeys("user:1"));
        System.out.println(jedis.hvals("user:1"));

        //或者是可以直接添加一个map集合
        Map<String,String> map = new HashMap<>();
        map.put("name","李四");
        map.put("age","18");
        //输入多个map值
        jedis.hmset("user:2",map);
        //输入一个map值
        Map<String, String> map1 = new HashMap<>();
        map1.put("sex","男");
        jedis.hset("user:2",map1);
        System.out.println(jedis.hkeys("user:2"));
        System.out.println(jedis.hvals("user:2"));
    }

    //测试zset
    @Test
    public void test5(){
        //传入的score 的值，是一个double
        jedis.zadd("china",100d,"上海");

        //取出所有值
        Set<String> china = jedis.zrange("china", 0, 1);
        System.out.println("china = " + china);
    }
}

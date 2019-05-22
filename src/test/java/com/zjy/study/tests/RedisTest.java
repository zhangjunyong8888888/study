package com.zjy.study.tests;

import com.zjy.study.StudyApplicationTests;
import com.zjy.study.modules.redis.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTest extends StudyApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testString() {
        ValueOperations<String,Object> valueOperations =  redisTemplate.opsForValue();

        valueOperations.set("user-1",new User("1","张三",18));
        System.out.println(valueOperations.get("user-1"));
        valueOperations.getOperations().delete("user-1");
        System.out.println(valueOperations.get("user-1"));
        // 第三个参数是 offset，偏移量       kkkkkkkkkkkk
        valueOperations.set("key-1", "kkkkkkkkkkkk", 6);
        System.out.println(valueOperations.get("key-1"));

        // 该方法的特点是如果redis中已有该数据,不保存返回false,不存在该数据，保存返回true
        Boolean isOk1 = valueOperations.setIfAbsent("key-2","22");
        System.out.println(isOk1);
        Boolean isOk2 = valueOperations.setIfAbsent("key-2","22");
        System.out.println(isOk2);

        // 批量 set get
        Map<String,String> valueMap = new HashMap<>(3);
        valueMap.put("key-3","3333");
        valueMap.put("key-4","4444");
        valueMap.put("key-5","5555");
        valueOperations.multiSet(valueMap);
        List<String> keyList = new ArrayList<>(3);
        keyList.add("key-3");
        keyList.add("key-4");
        keyList.add("key-5");
        List<Object> valueList = valueOperations.multiGet(keyList);
        valueList.forEach(s -> System.out.println(s));

        // 计数器 支持整数和浮点数
        valueOperations.set("num-1",100);
        System.out.println(valueOperations.get("num-1"));
        valueOperations.increment("num-1",1);
        System.out.println(valueOperations.get("num-1"));
        valueOperations.increment("num-1",1.01);
        System.out.println(valueOperations.get("num-1"));

        // 如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。
        // 如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
        valueOperations.append("key-6","kkkkk");
        System.out.println(valueOperations.get("key-6"));
        valueOperations.append("key-6","kkkkkkk");
        System.out.println(valueOperations.get("key-6"));

        // 截取key所对应的value字符串
        valueOperations.set("key-7","123456789");
        System.out.println(valueOperations.get("key-7",0,5));
        // 不会报越界错误
        System.out.println(valueOperations.get("key-7",0,20));
        // 也支持这种负数形式，不好用
        System.out.println(valueOperations.get("key-7", -3, -1));
        // 返回key所对应的value值得长度
        System.out.println(valueOperations.size("key-7"));

    }

    @Test
    public void testList(){
        ListOperations<String,Object> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list","1111");
        listOperations.leftPush("list","2222");
        listOperations.leftPush("list","3333");
        listOperations.leftPush("list","4444");
        listOperations.leftPushIfPresent("list1","3333");

        listOperations.set("list", 3, "100");
        // 下边这个会报错 index out of range
        //listOperations.set("list", 100, "100");

        // count> 0：删除等于从头到尾移动的值的一个元素。
        // count <0：删除等于从尾到头移动的值的一个元素。
        // count = 0：删除等于value的所有元素。
        listOperations.remove("list", 1, "3333");

        // 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引,这里会留下三条数据
        listOperations.trim("list", 0, 2);

        listOperations.leftPushAll("list2", Arrays.asList("1","2","3"));

        // 弹出最左边的元素，弹出之后该值在列表中将不复存在
        System.out.println(listOperations.leftPop("list"));

        // 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
        System.out.println(listOperations.leftPop("list", 1000, TimeUnit.MICROSECONDS));

        // 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
        listOperations.rightPopAndLeftPush("list", "newList");

        List<Object> list2 = listOperations.range("list2",0,-1);
        list2.forEach(l -> System.out.println(l));
        System.out.println("--------------------");
        // 获取指定index的list 的值
        System.out.println(listOperations.index("list", 0));
        System.out.println(listOperations.index("list", 100));
    }

    @Test
    public void testHash(){
        HashOperations<String,String,Object> hashOperations=redisTemplate.opsForHash();
        Map<String, Object> map = new HashMap(4);
        map.put("key1","1");
        map.put("key2","2");
        map.put("key3","3");
        map.put("key4","4");
        hashOperations.putAll("key",map);
        // 删除给定的哈希hashKeys
        hashOperations.delete("key", "key4");

        // 确定哈希 hashKey 是否存在
        System.out.println(hashOperations.hasKey("key", "key1"));
        // 从键中的哈希获取给定 hashKey 的值
        System.out.println(hashOperations.get("key", "key1"));
        System.out.println(hashOperations.multiGet("key", Arrays.asList("key1","key2")));

        // 通过给定的delta增加散列hashKey的值 支持整形、浮点型
        hashOperations.put("key","key5",100);
        hashOperations.increment("key", "key5", 1);
        System.out.println(hashOperations.get("key", "key5"));
        // 获取key所对应的散列表的key
        System.out.println(hashOperations.keys("key"));
        // 获取整个哈希存储的值 value
        System.out.println(hashOperations.values("key"));
        // 获取整个哈希存储
        System.out.println(hashOperations.entries("key"));
        // size
        System.out.println(hashOperations.size("key"));

        // 仅当 hashKey 不存在时才设置散列 hashKey 的值。
        Boolean hashIfAbsent = hashOperations.putIfAbsent("key", "key1", "25");
        System.out.println(hashIfAbsent);
    }

    @Test
    public void testSet(){
        SetOperations<String,Object> setOperations = redisTemplate.opsForSet();
        setOperations.add("set",new String[]{"1","2","3"});
        setOperations.add("set","1","4");

        // 移除集合中一个或多个成员
        setOperations.remove("set", "1","4");

        // 移除并返回集合中的一个随机元素
        System.out.println(setOperations.pop("set"));
        System.out.println(setOperations.pop("set",1));
        setOperations.add("set","1","4");
        // 将 1 元素从 set 集合移动到 set1 集合
        setOperations.move("set", "1", "set1");
        // size
        System.out.println(setOperations.size("set"));
        // 判断 member 元素是否是集合 key 的成员
        System.out.println(setOperations.isMember("set", "4"));

        // key 对应的无序集合与 otherKey 对应的无序集合求交集（可以与多个 otherKey 求交集）
        setOperations.add("set","1","2","4");
        setOperations.add("set1","1","2");
        System.out.println(setOperations.intersect("set","set1"));
        setOperations.add("set2","1","2","4","5");
        // 所有set 共同的交集
        System.out.println(setOperations.intersect("set",Arrays.asList("set1","set2")));
        // 求 并集
        System.out.println(setOperations.union("set",Arrays.asList("set1","set2")));


    }
}

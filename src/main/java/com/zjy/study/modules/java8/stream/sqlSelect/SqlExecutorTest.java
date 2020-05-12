package com.zjy.study.modules.java8.stream.sqlSelect;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 类描述：SqlExecutor测试类
 */
public class SqlExecutorTest {

    /**
     * 主方法
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // 构建原始集合数据
        List<Student> list = Lists.newArrayList();
        list.add(new Student(1, "唐一", "男", 11, DateUtils.parseDate("2018-01-01", "yyyy-MM-dd")));
        list.add(new Student(2, "杜二", "女", 12, DateUtils.parseDate("2018-02-02", "yyyy-MM-dd")));
        list.add(new Student(3, "张三", "男", 13, DateUtils.parseDate("2018-03-03", "yyyy-MM-dd")));
        list.add(new Student(4, "李四", "女", 14, DateUtils.parseDate("2018-04-04", "yyyy-MM-dd")));
        list.add(new Student(5, "王五", "女", 15, DateUtils.parseDate("2018-05-05", "yyyy-MM-dd")));

        // SQL语句
        String sql = "SELECT ID, NAME, AGE, BIRTHDAY FROM STUDENT WHERE AGE > 12 OR BIRTHDAY > '2018-04-04 00:00:00' ORDER BY ID DESC LIMIT 1, 2";

        // 使用SQL检索集合数据
        Object object = SqlExecutor.execute(Student.class, list, sql);

        // 输出结果
        System.out.println(JSON.toJSONString(object, true));
    }


    /**
     * STUDENT实体类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Student {
        private Integer id;

        private String name;

        private String gender;

        private Integer age;

        private Date birthday;
    }
}
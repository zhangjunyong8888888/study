package com.zjy.study.modules.java8.stream;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 学生 对象
 */
class Student {
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 班级
     */
    private Grade grade;

    public Student(String name, int age, Gender gender, Grade grade) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", age=" + age + ", gender=" + gender
                + ", grade=" + grade + "]";
    }

}

/**
 * 性别
 */
enum Gender {
    MALE, FEMALE
}

/**
 * 班级
 */
enum Grade {
    ONE, TWO, THREE, FOUR;
}

public class CollectDemo {

    public static void main(String[] args) {
        // 测试数据
        List<Student> students = Arrays.asList(
                new Student("小明", 10, Gender.MALE, Grade.ONE),
                new Student("大明", 9, Gender.MALE, Grade.THREE),
                new Student("小白", 8, Gender.FEMALE, Grade.TWO),
                new Student("小黑", 13, Gender.FEMALE, Grade.FOUR),
                new Student("小红", 7, Gender.FEMALE, Grade.THREE),
                new Student("小黄", 13, Gender.MALE, Grade.ONE),
                new Student("小青", 13, Gender.FEMALE, Grade.THREE),
                new Student("小紫", 9, Gender.FEMALE, Grade.TWO),
                new Student("小王", 6, Gender.MALE, Grade.ONE),
                new Student("小李", 6, Gender.MALE, Grade.ONE),
                new Student("小马", 14, Gender.FEMALE, Grade.FOUR),
                new Student("小刘", 13, Gender.MALE, Grade.FOUR));

        // 得到所有学生的年龄
        // s -> s.getAge() ， Student::getAge 后者性能更好，因为不会多生成一个类似lambda$0这样的函数
        List<Integer> ageList = students.stream().map(Student::getAge).collect(Collectors.toList());
        Set<Integer> ageSet1 = students.stream().map(Student::getAge).collect(Collectors.toSet());
        // 指定集合的实现类型
        Set<Integer> ageSet2 = students.stream().map(Student::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("所有学生年龄为：" + ageList);

        // 统计汇总信息
        IntSummaryStatistics ageSummaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        // 年龄信息汇总为：IntSummaryStatistics{count=12, sum=121, min=6, average=10.083333, max=14}
        System.out.println("年龄信息汇总为：" + ageSummaryStatistics);

        // 分块（特殊的分组，只有两种可能）
        Map<Boolean, List<Student>> collect = students.stream().collect(Collectors.partitioningBy(student -> student.getGender() == Gender.MALE));
        System.out.println("根据性别分块显示：" + collect);

        // 分组
        Map<Grade, List<Student>> collect1 = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        System.out.println("根据班级分组显示：" + collect1);

        // 显示每个班级人个数
        Map<Grade, Long> collect2 = students.stream().collect(Collectors.groupingBy(Student::getGrade,Collectors.counting()));
        System.out.println("根据班级分组显示：" + collect2);

    }

}

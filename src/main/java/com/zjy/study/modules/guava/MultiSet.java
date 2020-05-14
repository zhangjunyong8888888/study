package com.zjy.study.modules.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * @ClassName : MultiSet
 * @Description : 可重复的set
 * @Author : zjy
 * @Date: 2020-05-14 22:45
 */
public class MultiSet {

    public static void main(String[] args) {
        Multiset<Character> characters = HashMultiset.create();
        
        characters.size();
        // 统计 s 出现的次数
        int count = characters.count('s');
    }

}

package com.zjy.study;

import com.zjy.study.elasticsearch.bean.Item;
import com.zjy.study.elasticsearch.repository.ItemRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElasticSearchTest extends StudyApplicationTests{

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createIndex(){
        elasticsearchTemplate.createIndex(Item.class);
    }

    @Test
    public void deleteIndex(){
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    @Test
    public void insert(){
        Item item = new Item();
        item.setId(1L);
        item.setBrand("小米");
        item.setCategory("手机数码");
        item.setImages("http://image.baidu.com/13123.jpg");
        item.setPrice(3499.00);
        item.setTitle("小米2019最新款旗舰手机，速度秒杀");
        itemRepository.save(item);
    }

    @Test
    public void insertList(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.baidu.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void update(){
        Item item = new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.baidu.com/456789.jpg");
        // 接收对象集合，实现批量新增
        itemRepository.save(item);
    }

    @Test
    public void query(){
        Iterable<Item> items =itemRepository.findAll();

        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void findByPriceBetween(){
        List<Item> itemList=itemRepository.findByPriceBetween(3499.1,3699.0);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void filterQuery(){
        Page<Item> itemPage=itemRepository.findByTitleLike("小米", PageRequest.of(1,10));
        itemPage.iterator().forEachRemaining(x->{
            System.out.println(x.toString());
        });
    }
}

package com.zjy.study.modules.elasticsearch.repository;

import com.zjy.study.modules.elasticsearch.bean.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchCrudRepository<Item,Long> {

    List<Item> findByPriceBetween(double price1, double price2);

    Page<Item> findByTitleLike(String title, PageRequest pageRequest);
}

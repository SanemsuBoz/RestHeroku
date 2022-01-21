package com.works.restcontrollers;

import com.works.entities.Note;
import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.utils.ERest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    final ProductRepository repository;
    final CacheManager cacheManager;
    public ProductRestController(ProductRepository repository, CacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
    }

    // add
    @PostMapping("/add")
    public Map<ERest, Object> add(@RequestBody Product obj) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        hm.put(ERest.status, true);
        hm.put(ERest.message, "Add Success");
        hm.put(ERest.result, repository.save(obj) );
        cacheManager.getCache("list").clear();
        return hm;
    }


    // list
    @GetMapping("/list")
    @Cacheable(value = "list")
    public Map<ERest, Object> list() {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        hm.put(ERest.status, true);
        hm.put(ERest.message, "List Success");
        hm.put(ERest.result, repository.findAll() );
        return hm;
    }

    @Scheduled(fixedDelay = 1000 * 10 )
    public void timer() {
        System.out.println("Timer Call");
        cacheManager.getCache("list").clear();
    }

}

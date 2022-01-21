package com.works.restcontrollers;

import com.works.repositories.UserInfoRepository;
import com.works.utils.ERest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class InfoRestController {

    final UserInfoRepository repository;
    public InfoRestController(UserInfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/info")
    public Map<ERest, Object> info() {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        hm.put(ERest.status, true);
        hm.put(ERest.result, repository.findAll() );
        return hm;
    }


}

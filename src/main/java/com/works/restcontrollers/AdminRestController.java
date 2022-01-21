package com.works.restcontrollers;

import com.works.services.UserService;
import com.works.entities.Role;
import com.works.entities.User;
import com.works.repositories.RoleRepository;
import com.works.utils.ERest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AdminRestController {

    final UserService service;
    final RoleRepository rRepo;
    public AdminRestController(UserService service, RoleRepository rRepo) {
        this.service = service;
        this.rRepo = rRepo;
    }

    @GetMapping("/adminAdd")
    public Map<ERest, Object> adminAdd(@RequestBody User u) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        User nu = service.register(u);
        hm.put(ERest.result, nu);
        return hm;
    }


}

package com.works.restcontrollers;

import com.works.services.UserService;
import com.works.entities.Role;
import com.works.entities.User;
import com.works.repositories.RoleRepository;
import com.works.utils.ERest;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Map<ERest, Object> adminAdd() {
        Map<ERest, Object> hm = new LinkedHashMap<>();

        User u = new User();

        Optional<Role> r1 = rRepo.findById(1L);
        Optional<Role> r2 = rRepo.findById(2L);
        List<Role> rLs = new ArrayList<>();
        rLs.add(r1.get());
        rLs.add(r2.get());
        u.setRoles(rLs);

        u.setPassword("12345");
        u.setTokenExpired(true);
        u.setEnabled(true);
        u.setEmail("zehra@mail.com");
        u.setLastName("Bilsin");
        u.setFirstName("Zehra");

        User nu = service.register(u);
        hm.put(ERest.result, nu);
        return hm;
    }


}

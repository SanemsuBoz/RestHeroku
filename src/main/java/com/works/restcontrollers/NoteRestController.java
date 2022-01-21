package com.works.restcontrollers;

import com.works.entities.Note;
import com.works.repositories.NoteRepository;
import com.works.utils.ERest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteRestController {

    final NoteRepository repository;
    public NoteRestController(NoteRepository repository) {
        this.repository = repository;
    }

    // add
    @PostMapping("/add")
    public Map<ERest, Object> add( @RequestBody Note obj) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        hm.put(ERest.status, true);
        hm.put(ERest.message, "Add Success");
        hm.put(ERest.result, repository.save(obj) );
        return hm;
    }


    // list
    @GetMapping("/list")
    public Map<ERest, Object> list() {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        hm.put(ERest.status, true);
        hm.put(ERest.message, "List Success");
        hm.put(ERest.result, repository.findAll() );
        return hm;
    }

}

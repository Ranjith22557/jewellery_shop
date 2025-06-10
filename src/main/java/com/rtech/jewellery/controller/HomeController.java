package com.rtech.jewellery.controller;

import com.rtech.jewellery.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/dashBoard")
    public Map<String,Map<String,Integer>> getGoldCounts(){
        return homeService.getGoldTypeCounts();
    }
}

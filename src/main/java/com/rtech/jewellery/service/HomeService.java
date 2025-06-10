package com.rtech.jewellery.service;

import com.rtech.jewellery.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeService {

    @Autowired
    private final HomeRepository homeRepository;

    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public Map<String,Map<String, Integer>> getGoldTypeCounts(){

        List<Object[]> result = homeRepository.fetchActiveProductCountsByType();
        Map<String,Map<String,Integer>> typeCountMap = new HashMap<>();

        for (Object[] row :result){
            String type = row[0].toString();
            int qty = (int) row[1];
            String gram = row[2].toString();

            typeCountMap.computeIfAbsent(type, k -> new HashMap<>())
                    .put(gram,qty);
        }
        return typeCountMap;
    }
}

package com.gmail.bhaskar.ecommerce.service;

import com.gmail.bhaskar.ecommerce.domain.Perfume;
import com.gmail.bhaskar.ecommerce.service.Impl.PerfumeServiceImpl;

import java.util.List;


public interface PerfumeService {

    Perfume getOne(Long id);


    List<Perfume> findAll();


    List<Perfume> filter(List<String> perfumers, List<String> genders, List<Integer> prices);


    List<Perfume> findByPerfumerOrderByPriceDesc(String perfumer);


    List<Perfume> findByPerfumeGenderOrderByPriceDesc(String perfumeGender);


    void saveProductInfoById(String perfumeTitle, String perfumer, Integer year, String country, String perfumeGender,
                             String fragranceTopNotes, String fragranceMiddleNotes, String fragranceBaseNotes, String description,
                             String filename, Integer price, String volume, String type, Long id);

    Perfume save(Perfume perfume);
}
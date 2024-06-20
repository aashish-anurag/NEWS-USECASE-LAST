package com.whishlist.whishlist.news.service;

import com.whishlist.whishlist.news.entity.Source;
import com.whishlist.whishlist.news.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements SourceService{

    @Autowired
    SourceRepository repository;

    @Override
    public Source saveSource(Source source) {
        return repository.save(source);
    }
}

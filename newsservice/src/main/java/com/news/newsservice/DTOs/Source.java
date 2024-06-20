package com.news.newsservice.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Source {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    public Source() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

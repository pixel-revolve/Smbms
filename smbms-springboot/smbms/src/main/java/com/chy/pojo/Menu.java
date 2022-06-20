package com.chy.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

@Data
@Document(collection = "menus")
public class Menu implements Serializable {

    private HashSet<Integer> roleIds;

    private String path;

    private String component;

    private String name;

    private boolean hidden = false;

    private Meta meta;

    private List<Menu> children;

}


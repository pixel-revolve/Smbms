package com.chy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Provider implements Serializable {
    Long id;
    String code;
    String name;
    String desc;
    String phone;
    String address;
}

package com.chy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysGoods implements Serializable {
    Long id;
    String name;
    Float price;
    String coverPic;
    String comment;
    String brand;
    Long providerId;
    Long stock;
}

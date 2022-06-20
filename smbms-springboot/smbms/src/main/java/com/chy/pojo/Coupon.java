package com.chy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon implements Serializable {
    Long id;
    Float price;
    String comment;
    Long userId;
}

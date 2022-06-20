package com.chy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOrder implements Serializable {
    Long id;
    Long userId;
    Long goodsId;
    Date orderDate;
    Long number;
    Date dateNeeded;
    String payMethod;
    String deliverMethod;
}

package com.bigdata.mypojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caowei
 * @date 2019/1/23 11:31
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable{
    private boolean flag;
    private boolean success;
    private String message;
    public Result(boolean success, String message) {
        this.success=success;
        this.flag = success;
        this.message = message;
    }
}
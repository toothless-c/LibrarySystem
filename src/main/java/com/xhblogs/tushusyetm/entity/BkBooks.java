package com.xhblogs.tushusyetm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("bk_books")
public class BkBooks {
    @TableId(type = IdType.AUTO)
    private Long Id;
    private String name;//名称
    private String bookPlaceNum;
    private String author;//作者
    private String sM;//图书编号
    private String price;//价格
    private Long number;//数量
    private int classifyId;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    //更新时间
    private Date updateTime;
    @TableField(exist = false)
    private BkClassify bkClassify; //分类
    @TableField(exist = false)
    private String classifyName;//分类名称
}

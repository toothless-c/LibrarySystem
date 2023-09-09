package com.xhblogs.tushusyetm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bk_classify")
public class BkClassify {
    @TableId(type = IdType.AUTO)
    private Long classifyId;
    private String classifyName; //分类名称

}
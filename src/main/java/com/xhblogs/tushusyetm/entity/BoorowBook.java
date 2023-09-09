package com.xhblogs.tushusyetm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("boorow_book")
public class BoorowBook {
    private Long id;
    private Long bookId;
    private Long readerId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date returnTime;
    private String checkStatus;
    private String  borrowStatus;
    private String returnStatus;
    private String excepionText;
    @TableField(exist = false)
    private List<BkBooks> bkBooks;
    @TableField(exist = false)
    private List<BkUser> bkUser;
}

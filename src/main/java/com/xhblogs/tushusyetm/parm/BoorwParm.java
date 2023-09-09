package com.xhblogs.tushusyetm.parm;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BoorwParm {
    //借书人id
    private Long readerId;
    //图书id
    private List<Long> bookIds;
    //还书时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date returnTime;
}

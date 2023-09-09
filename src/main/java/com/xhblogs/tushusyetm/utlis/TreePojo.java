package com.xhblogs.tushusyetm.utlis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class TreePojo {
    private String label;
    private List<Map<String,Object>> children;
}

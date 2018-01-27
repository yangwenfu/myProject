package com.xinyunlian.jinfu.schedule.dto.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
public class MSContainerDto implements Serializable{

    private List<MScheduleDto> left = new ArrayList<>();

    private List<MRepayDto> right = new ArrayList<>();

    public List<MScheduleDto> getLeft() {
        return left;
    }

    public void setLeft(List<MScheduleDto> left) {
        this.left = left;
    }

    public List<MRepayDto> getRight() {
        return right;
    }

    public void setRight(List<MRepayDto> right) {
        this.right = right;
    }
}

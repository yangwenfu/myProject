package com.xinyunlian.jinfu.loan.dto.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
public class LoanCUserDto implements Serializable{

    private LoanCUserBaseDto base;

    private List<LoanCUserLinkmanEachDto> linkman = new ArrayList<>();

    private List<LoanCUserStoreEachDto> store = new ArrayList<>();

    public LoanCUserBaseDto getBase() {
        return base;
    }

    public void setBase(LoanCUserBaseDto base1) {
        base = base1;
    }

    public List<LoanCUserLinkmanEachDto> getLinkman() {
        return linkman;
    }

    public void setLinkman(List<LoanCUserLinkmanEachDto> linkman) {
        this.linkman = linkman;
    }

    public List<LoanCUserStoreEachDto> getStore() {
        return store;
    }

    public void setStore(List<LoanCUserStoreEachDto> store) {
        this.store = store;
    }
}

package com.xinyunlian.jinfu.loan.dto.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@Deprecated
public class LoanUserDto implements Serializable{

    private LoanUserBaseDto base;

    private List<LoanUserLinkmanEachDto> linkman = new ArrayList<>();

    private List<LoanUserStoreEachDto> store = new ArrayList<>();

    public LoanUserBaseDto getBase() {
        return base;
    }

    public void setBase(LoanUserBaseDto base1) {
        base = base1;
    }

    public List<LoanUserLinkmanEachDto> getLinkman() {
        return linkman;
    }

    public void setLinkman(List<LoanUserLinkmanEachDto> linkman) {
        this.linkman = linkman;
    }

    public List<LoanUserStoreEachDto> getStore() {
        return store;
    }

    public void setStore(List<LoanUserStoreEachDto> store) {
        this.store = store;
    }
}

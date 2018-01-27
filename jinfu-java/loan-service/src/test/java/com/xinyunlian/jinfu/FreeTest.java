package com.xinyunlian.jinfu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author willwang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class FreeTest {

    @Test
    public void test() throws Exception {
        ApplyContext applyContext = new ApplyContext();
        applyContext.trial();
        applyContext.review();
    }

}

abstract class ApplyState{
    abstract void trial(ApplyContext applyContext) throws Exception;
    abstract void review(ApplyContext applyContext) throws Exception;
}

class DealingState extends ApplyState{
    @Override
    void trial(ApplyContext applyContext) {
        applyContext.setState(new TrialState());
        System.out.println("订单已初审");
    }

    @Override
    void review(ApplyContext applyContext) throws Exception {
        throw new Exception("当前状态无法进行复审");
    }
}

class TrialState extends ApplyState{

    @Override
    void trial(ApplyContext applyContext) {
        applyContext.setState(this);
        System.out.println("订单已初审");
    }

    @Override
    void review(ApplyContext applyContext) throws Exception {
        throw new Exception("当前状态无法进行复审");
    }
}

class ReviewState extends ApplyState{

    @Override
    void trial(ApplyContext applyContext) throws Exception {
        throw new Exception("无法重复进行初审");
    }

    @Override
    void review(ApplyContext applyContext) {
        applyContext.setState(new SucceedState());
        System.out.println("订单已复审");
    }
}

class SucceedState extends ApplyState{

    @Override
    void trial(ApplyContext applyContext) throws Exception {
        throw new Exception("状态无法回退");
    }

    @Override
    void review(ApplyContext applyContext) throws Exception {
        throw new Exception("状态无法回退");
    }
}

class ApplyContext{

    ApplyState state = null;

    public ApplyContext() {
        this.state = new DealingState();
    }

    public void setState(ApplyState state) {
        this.state = state;
    }

    void trial() throws Exception {
        this.state.trial(this);
    }

    void review() throws Exception {
        this.state.review(this);
    }
}
package com.zly.module.b.model;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by Zhuliya on 2018/9/30
 */
public class MbModel {

    /**
     * 查询用户余额
     *
     * @param userId
     * @return
     */
    public Observable<Long> queryMoneyByUserId(int userId) {
        //模拟2秒请求
        return Observable.timer(2, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Long aLong) throws Exception {
                        //构建请求返回数据
                        return Observable.just(66666666L);
                    }
                });
    }
}

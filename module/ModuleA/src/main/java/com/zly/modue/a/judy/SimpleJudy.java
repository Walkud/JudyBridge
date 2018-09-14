package com.zly.modue.a.judy;

import com.zly.judy.api.KeepSource;
import com.zly.judy.api.annontations.JudyBridge;
import com.zly.judy.api.annontations.TargetClass;
import com.zly.judy.lib.bean.Cup;
import com.zly.judy.lib.bean.GlassCup;
import com.zly.judy.lib.bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 调试示例代码
 * (RE) :生成代码时会被移除
 * Created by Zhuliya on 2018/9/5
 */
@JudyBridge
@TargetClass("acbss")//(RE)
public class SimpleJudy extends User implements KeepSource {//继承User(RE)

    /**
     * 常量(RE)
     */
    public static final String CONST = "aaaa";

    /**
     * 属性(RE)
     */
    private boolean isLogin = false;

    /**
     * 返回类型通配符调试
     *
     * @return
     */
    public List<? extends Cup> genericSample1() {
        return new ArrayList<>();
    }

    /**
     * 参数通配符调试
     *
     * @param obs
     */
    public void genericSample2(Observable<List<? super GlassCup>> obs) {
    }

    /**
     * 泛型调式
     *
     * @return
     */
    public Observable<ArrayList<String>> getModuleName() {
        return Observable.just(new ArrayList<String>());
    }

    /**
     * 静态方法(RE)
     *
     * @return
     */
    public static int staticMethod() {
        return 1;
    }

}

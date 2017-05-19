package com.example.draglayout;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.draglayout.bean.TransitionBean;
import com.example.draglayout.utils.SharedElementUtil;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void transittionGson(){
        String s = "12344";
        TransitionBean transitionBean = SharedElementUtil.getTransitionBean(s);
        assertEquals(null, transitionBean);
    }
}
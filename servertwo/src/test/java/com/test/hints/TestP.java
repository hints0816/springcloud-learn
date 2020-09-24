package com.test.hints;


import com.hints.servertwo.ServiceTwoApplication;
import com.hints.servertwo.dao.CronTaskDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by asus1 on 2018/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ServiceTwoApplication.class, TestP.class})
public class TestP {
    @Autowired
    private CronTaskDao cronTaskDao;

    @Test
    public void testProdecutAdd() {
        cronTaskDao.getTest3();
    }
}
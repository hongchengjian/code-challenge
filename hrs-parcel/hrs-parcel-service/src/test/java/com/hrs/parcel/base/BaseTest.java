package com.hrs.parcel.base;

import com.hrs.parcel.ParcelServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@SpringBootTest(classes = ParcelServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public abstract class BaseTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() {
        System.out.println("====== finish ======");
    }
}
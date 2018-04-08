package com.lasse.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadWarTest {

    @Autowired
    DownloadWar downloadWar;
    @Test
    public void down() throws Exception {
        downloadWar.down("123");
    }

}
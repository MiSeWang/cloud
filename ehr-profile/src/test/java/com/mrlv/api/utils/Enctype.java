package com.mrlv.api.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class Enctype {

    @Test
    public void getPassword(){
        Md5Hash md5Hash = new Md5Hash("", "", 1024);
        System.out.println(md5Hash);
    }
}

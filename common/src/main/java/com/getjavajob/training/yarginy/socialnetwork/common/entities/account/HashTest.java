package com.getjavajob.training.yarginy.socialnetwork.common.entities.account;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(md.digest("string".getBytes("UTF-8")));
    }
}

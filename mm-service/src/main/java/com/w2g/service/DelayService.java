package com.w2g.service;

import com.w2g.entity.DelayedElement;

import java.util.concurrent.DelayQueue;

/**
 * Created by W2G on 2018/11/28.
 */
public interface DelayService {


    DelayQueue<DelayedElement> dealwith(int time, String updateNote, int sId);
}

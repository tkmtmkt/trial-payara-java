package com.github.tkmtmkt;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class AppleBean implements Apple {
    @EJB(name="ejb/Orange")
    Orange orange;

    @Override
    public void execute() {
        log.info(">>>>> execute() start");

        orange.execute();
    }
}

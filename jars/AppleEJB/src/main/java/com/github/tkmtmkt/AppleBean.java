package com.github.tkmtmkt;

import javax.ejb.Stateless;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class AppleBean implements Apple {
    public void execute() {
        log.info(">>>>> execute() start");
    }
}

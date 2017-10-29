package com.github.tkmtmkt;

import javax.ejb.Stateless;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class Orange implements OrangeRemote {
    @Override
    public void execute() {
        log.info(">>>>> execute() start");
    }
}

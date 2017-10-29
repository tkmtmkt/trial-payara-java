package com.github.tkmtmkt;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@LocalBean
@Stateless
public class Apple implements AppleRemote {
    @EJB(name="ejb/OrangeRemote")
    OrangeRemote orange;

    @Override
    public void execute() {
        log.info(">>>>> execute() start");

        orange.execute();
    }
}

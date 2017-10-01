package com.github.tkmtmkt;

import javax.ejb.Remote;

@Remote
public interface ParallelSample {
    public void execute();
}

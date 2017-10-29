package com.github.tkmtmkt.test.rules;

import org.junit.rules.ExternalResource;

public class TestFixture extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        super.before();
        System.out.println(">>>>>>> TestFixture.before()");
    }

    @Override
    protected void after() {
        super.after();
        System.out.println(">>>>>>> TestFixture.after()");
    }
}

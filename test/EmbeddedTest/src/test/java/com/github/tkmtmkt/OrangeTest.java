package com.github.tkmtmkt;

import static org.assertj.core.api.Assertions.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.tkmtmkt.test.DeploymentManager;
import com.github.tkmtmkt.test.rules.TestFixture;

@RunWith(Arquillian.class)
public class OrangeTest {
    @Inject
    private Orange sut;

    @Rule
    public TestFixture fixture = new TestFixture();

    @Deployment
    public static Archive<?> createDeployment() {
        System.out.println(">>>>>>> @Deployment");
        final WebArchive war = DeploymentManager.create("OrangeEARTest")
                .addClass(OrangeTest.class);
        System.out.println(war.toString(true));

        return war;
    }

    @Test
    public void testExecute() {
        System.out.println(">>>>>>> @Test");
        assertThat(sut).isNotNull();
        sut.execute();
    }
}

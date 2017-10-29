package com.github.tkmtmkt;

import static org.assertj.core.api.Assertions.*;

import javax.ejb.EJB;

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
public class AppleTest {

    @Rule
    public TestFixture fixture = new TestFixture();

    @EJB(name="ejb/AppleRemote")
    private AppleRemote sut;

    @Deployment
    public static Archive<?> createDeployment() {
        System.out.println(">>>>>>> @Deployment");
        final WebArchive war = DeploymentManager.create("AppleEARTest")
                .addClass(AppleTest.class);
        System.out.println(war.toString(true));

        return war;
    }

    @Test()
    public void testExecute() {
        System.out.println(">>>>>>> @Test");
        assertThat(sut).isNotNull();
        sut.execute();
    }
}

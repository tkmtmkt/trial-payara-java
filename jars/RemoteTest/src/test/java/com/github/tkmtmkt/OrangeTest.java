package com.github.tkmtmkt;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OrangeTest {
    @EJB
    private Orange sut;

    @Deployment
    public static Archive<?> createDeployment() {
        System.out.println(">>>>> @Deployment");
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "OrangeEARTest.war")
                .addAsLibraries(new File("../OrangeEJB/build/libs/OrangeEJB-1.0-SNAPSHOT.jar"))
                .addAsWebInfResource(new File("src/test/resources/web.xml"))
                .addAsWebInfResource(new File("src/test/resources/glassfish-web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));

        System.out.println(war.toString(true));

        return war;
    }

    @Test()
    public void testExecute() {
        System.out.println(">>>>> @Test");
        assertThat(sut).isNotNull();
        sut.execute();
    }
}

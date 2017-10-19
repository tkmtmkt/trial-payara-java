package com.github.tkmtmkt;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ParallelSampleTest {
    //@EJB(mappedName = "java:global/SampleEAR-1.0-SNAPSHOT/SampleEJB-1.0-SNAPSHOT/ParallelSample")
    //@EJB(mappedName = "java:global/SampleEAR-1.0-SNAPSHOT/SampleEJB-1.0-SNAPSHOT/ParallelSample!com.github.tkmtmkt.ParallelSample")
    @Inject
    private Apple sut;

    @Deployment
    public static Archive<?> createDeployment() {
        final EnterpriseArchive ear = ShrinkWrap
                .createFromZipFile(EnterpriseArchive.class, new File("../SampleEAR/build/libs/SampleEAR-1.0-SNAPSHOT.ear"));

        //ArquillianServletRunner
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "arquillian-test.war")
                .addPackages(true, "com.github.tkmtmkt")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        ear.addAsModule(war);

        System.out.println(war.toString(true));
        System.out.println(ear.toString(true));

        return ear;
    }

    @Test()
    //@RunAsClient
    public void testExecute() {
        System.out.println(">>>>> テスト実行");
        assertThat(sut).isNotNull();
        sut.execute();
    }
}

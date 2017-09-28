package com.github.tkmtmkt;

import java.io.File;
import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ParallelSampleTest {
    @EJB
    private ParallelSample sut;

    @Deployment
    public static EnterpriseArchive createDeployment() {
        final EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
            .as(ZipImporter.class)
            .importFrom(new File("../SampleEAR/build/libs/SampleEAR-1.0-SNAPSHOT.ear"))
            .as(EnterpriseArchive.class);
        final JavaArchive testLibrary = ShrinkWrap.create(JavaArchive.class, "testLibrary.jar")
            .addClass(ParallelSampleTest.class);
        ear.addAsLibrary(testLibrary);

        return ear;
    }

    @Test()
    public void testExecute() {
        sut.execute();
    }
}

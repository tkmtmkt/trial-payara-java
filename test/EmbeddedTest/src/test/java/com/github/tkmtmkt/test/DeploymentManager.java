package com.github.tkmtmkt.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class DeploymentManager {

    public static WebArchive create(final String name) {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, name + ".war")
                .addAsLibrary(new File("../../jars/AppleEJB/build/libs/AppleEJB-1.0-SNAPSHOT.jar"))
                .addAsLibrary(new File("../../jars/OrangeEJB/build/libs/OrangeEJB-1.0-SNAPSHOT.jar"))
                .addAsWebInfResource(new File("src/test/resources/web.xml"))
                .addAsWebInfResource(new File("src/test/resources/glassfish-web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));

        return war;
    }
}

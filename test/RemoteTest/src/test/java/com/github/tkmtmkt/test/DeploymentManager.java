package com.github.tkmtmkt.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.github.tkmtmkt.test.rules.TestFixture;

public class DeploymentManager {

    public static WebArchive create(final String name) {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, name + ".war")
                .addAsLibrary(new File("../../jars/RemoteInterface/build/libs/RemoteInterface-1.0-SNAPSHOT.jar"))
                .addAsLibrary(new File("../../libext/assertj-core-3.8.0.jar"))
                .addClass(DeploymentManager.class)
                .addClass(TestFixture.class)
                .addAsWebInfResource(new File("src/test/resources/web.xml"))
                .addAsWebInfResource(new File("src/test/resources/glassfish-web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));

        return war;
    }
}

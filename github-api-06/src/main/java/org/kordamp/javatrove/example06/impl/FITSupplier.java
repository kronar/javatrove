package org.kordamp.javatrove.example06.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import groovy.lang.GroovyClassLoader;
import org.kordamp.javatrove.example06.service.Github;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * Created by nikita on 04.09.17.
 */
public class FITSupplier implements Supplier<Github> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FITSupplier.class);
    private Github original;
    private String groovyFile;
    private final GroovyClassLoader groovyClassLoader;


    @Inject
    public FITSupplier(Github original, @Named("groovy.fit.impl.file") String groovyFile) {
        this.original = original;
        this.groovyFile = groovyFile;
        groovyClassLoader = new GroovyClassLoader(ClassLoader.getSystemClassLoader());

    }

    @Override
    public Github get() {

        File file = new File(groovyFile);
        boolean exists = file.exists();
        LOGGER.debug("FIT file exists : {}", exists);
        if (exists) {
            try {
                Class<?> clazz = groovyClassLoader.parseClass(file);
                if (Github.class.isAssignableFrom(clazz)) {
                    return (Github) clazz.newInstance();
                }
            } catch (IOException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return original;
    }
}

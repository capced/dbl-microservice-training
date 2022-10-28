package fr.caa.common.test.mock;

import java.util.Collection;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class ClasspathMockResolver {

    public static Collection<String> listFiles(String pathPrefix, String pattern) {
        Reflections reflections = new Reflections(pathPrefix, Scanners.Resources);

        return reflections.getResources(Pattern.compile(pattern));
    }
}

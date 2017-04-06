package com.intellecteu.camel.spring.tests;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseTestHelper extends CamelSpringTestSupport {

    private String[] contexts = {
            "classpath:META-INF/spring/simple-spring.xml"
    };

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(contexts);
        return applicationContext;
    }

    public boolean isUseAdviceWith(){
        return true;
    }

    @Override
    public String isMockEndpoints() {
        return "*";
    }

    protected String loadResource(String resourcePath) throws IOException {
        Resource resource = applicationContext.getResource(resourcePath);

        return new String(Files.readAllBytes(Paths.get(resource.getURI())), Charset.defaultCharset());
    }

}

package com.eazybytes.example18.Audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EazySchool_InfoContributor implements InfoContributor {

    @Autowired
    private Environment environment;

    @Override
    public void contribute(Info.Builder builder) {

        Map<String,String> appInfo = new HashMap<>();
        appInfo.put("Application Name","EazySchool Web Application");
        appInfo.put("Version","1.1.0");
        appInfo.put("Framework used","SPRINGBOOT");
        appInfo.put("Tools used","spring security,data jpa,devtool,maven tool,mysql" +
                ",jakarta bean validation,project lombok");
        appInfo.put("server","Embedded Apache Tomcat");
            builder.withDetail("application-info",appInfo);
    }
}

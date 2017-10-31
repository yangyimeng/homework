package com.yangyimeng.homework.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liang on 2017/9/1.
 */
public class RegrexTest {


    public static void main(String [] args) {
        Pattern pattern = Pattern.compile("^(ssh|http)://([\\w-]+)@([\\w-]+\\.[\\w-]+\\.[\\w-]+)(/scm)?/([\\w-]+)/([\\w-]+).git$");
        String gitAddr = "ssh://git@git.github.com/ee/maven-fe.git";
        Matcher  matcher = pattern.matcher(gitAddr);

        System.out.println(matcher.matches());


        System.out.println("Hello , This is Regrex Test");

    }

}

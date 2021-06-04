
/*
 *@author bihai-ui
 *@create 2020-12-18 22:53
 */

import org.junit.Test;

public class Test1 {

    @Test
    public void test1(){
        String pathUrl = "http://onlineducation01.http//oss-cn-shenzhen.aliyuncs.com/2020/12/08/2ef38204-5f74-4da2-9ee2-e117de92b726.png";
        String objectName = pathUrl.substring((pathUrl.indexOf("com") + 4),pathUrl.length());
        System.out.println("---"+objectName+"---");
    }
}
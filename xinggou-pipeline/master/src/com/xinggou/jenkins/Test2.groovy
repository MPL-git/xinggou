#!groovy
package com.xinggou.jenkins


class Test2 {//含有static void main的方法的Test2必须要在第一个定义

    static void main(args) {
        def map = [:]

        /*环境变量，初始确定后一般不需更改*/
        map.put('maven', 'maven3.6.3')
        map.put('jdk', 'jdk8')


        /* 拉取代码 */
        map.put('isPullCode', true);
        map.put('cvs', 'svn');
        map.put('repoUrl', 'svn://121.196.208.202/jufeng/醒购微服系统/trunk/src/xinggou')
        map.put('credId', 'svn-creds')

        /* 编译 */
        map.put('isCompile', true);
        def poms = [
                "mvn clean install -Dmaven.test.skip=true -f xinggou-common/pom.xml",
                "mvn clean install -Dmaven.test.skip=true -f xinggou-bc/xinggou-bc-service-interface/pom.xml",
                "mvn clean install -Dmaven.test.skip=true -f xinggou-rc/xinggou-rc-service-interface/pom.xml"
        ];
        map.put('poms', poms);

        /* 部署 */
        map.put('isDeploy', true);
        def apps = []
        apps.add(new App("aaa", "bbb"))
        apps.add(new App("sssd", "bbdgdgdfb"))
        //生成war包的相对路径
        //apps.put('JAR_PATH', 'target/*.jar')
        map.put('apps', apps);


        /* 通知测试人员 */
        map.put('QA_EMAIL', '307926177@qq.com')

        List<App> abc = (List<App>)map.get("apps");
        abc.each {
            println("name:${it.appName},jarpath:${it.jarPath}");
        }
    }
}


package com.xinggou.jenkins

/**
 * @description ：${description}*
 * @author ：huangdl
 * @date ：Created in 2020/8/21
 *
 */
class App {
    private String appName
    private String jarPath

    App(String appName, String jarPath) {
        this.appName = appName
        this.jarPath = jarPath
    }

    String getAppName() {
        return appName
    }

    void setAppName(String appName) {
        this.appName = appName
    }

    String getJarPath() {
        return jarPath
    }

    void setJarPath(String jarPath) {
        this.jarPath = jarPath
    }
}

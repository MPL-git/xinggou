#!groovy

def call(Map map) {
    pipeline {
        agent any
        //参数化变量,目前只支持[booleanParam, choice, credentials, file, text, password, run, string]这几种参数类型，其他高级参数化类型还需等待社区支持
        parameters {
            //固定设置三类pipeline场景
            choice(name: 'scene', choices: "scene1:完整流水线\nscene2:代码检查\nscene3:测试部署", description: '场景选择，默认运行完整流水线，如果只做开发自测可选择代码检查，如果只做环境部署可选择测试部署')
            //若勾选在pipelie完成后会邮件通知测试人员进行验收
            booleanParam(name: 'isCommitQA', defaultValue: false, description: '是否在pipeline完成后，邮件通知测试人员进行人工验收')
            booleanParam(name: 'isCompile', defaultValue: false, description: '是否编译')
            string(name: 'pomPath', defaultValue: "${map.pomPath}", description: 'pom.xml的相对路径')
        }
        //环境变量，初始确定后一般不需更改
        tools {
            maven "maven3.6.3"
            jdk "${map.jdk}"
        }
        //常量参数，初始确定后一般不需更改
        environment {
            REPO_URL = "${map.REPO_URL}"
            //git服务全系统只读账号，无需修改
            CRED_ID = "${map.CRED_ID}"
            //pom.xml的相对路径
            POM_PATH = "${map.POM_PATH}"
            //生成war包的相对路径
            WAR_PATH = "${map.WAR_PATH}"
            //测试人员邮箱地址
            QA_EMAIL = "${map.QA_EMAIL}"
            //接口测试job名称
            ITEST_JOBNAME = "${map.ITEST_JOBNAME}"
        }

        options {
            disableConcurrentBuilds()
            timeout(time: 1, unit: 'HOURS')
            //保持构建的最大个数
            buildDiscarder(logRotator(numToKeepStr: '10'))
        }
        //pipeline的各个阶段场景
        stages {
            stage('初始化') {
                steps {
                    //一些初始化操作
                    script {
                        //根据param.server分割获取参数
//                        def split = params.server.split(",")
//                        serverIP = split[0]
//                        jettyPort = split[1]
//                        serverName = split[2]
//                        serverPasswd = split[3]
                        //场景选择
                        println params.scene

                        //单元测试运行场景
                        isUT = params.scene.contains('scene1:完整流水线') || params.scene.contains('scene2:代码检查')
                        println "isUT=" + isUT

                        //部署测试环境运行场景
                        isDP = params.scene.contains('scene1:完整流水线') || params.scene.contains('scene3:测试部署')
                        println "isDP=" + isDP

                        //接口测试运行场景
                        isIT = params.scene.contains('scene1:完整流水线')
                        println "isIT=" + isIT

                        try {
                            wrap([$class: 'BuildUser']) {
                                userEmail = "${BUILD_USER_EMAIL},${QA_EMAIL}"
                                user = "${BUILD_USER_ID}"
                            }
                        } catch (exc) {
                            userEmail = "${QA_EMAIL}"
                            user = "system"
                        }

                    }
                }
            }


            stage('拉取代码') {
                steps {
                    echo "starting fetchCode from ${REPO_URL}......"
                    // Get some code from a GitHub repository
                    //git credentialsId: CRED_ID, url: REPO_URL, branch: params.repoBranch

                    checkout([$class: 'SubversionSCM', locations: [[cancelProcessOnExternalsFail: true, credentialsId: CRED_ID, depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: REPO_URL]], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])

                }
            }

            stage('编译打包') {
                when {
                    expression
                            { return params.isCompile }
                }

                steps {
                    echo "starting compile......"
                    sh "cd /home/xgtest/xinggou"
                    sh "ls -lrt"
                    //编译和打包
                    sh "mvn  -f ${params.pomPath} clean package -Dautoconfig.skip=true -Dmaven.test.skip=true"
                }
            }

            stage('单元测试') {
                when {
                    expression
                            { return isUT }
                }

                steps {

                    echo "starting unitTest......"
                }
            }

            stage('部署测试环境') {
                when {
                    expression
                            { return isDP }
                }
                steps {
                    script{
                        wrap([$class: 'BuildUser']) {
                            def remote = [:]
                            remote.name = 'xinggou'
                            remote.host = '116.62.24.252'
                            remote.port = 11022
                            remote.user = 'xgtest'
                            remote.password = 'Jm689!test@xm'
                            remote.allowAnyHosts = true
                            sshCommand remote: remote, command: "ls -lrt"
                            //sshCommand remote: remote, command: "for i in {1..5}; do echo -n \"Loop \$i \"; date ; sleep 1; done"
                            //执行启动脚本
                            sshCommand remote: remote, command: "/home/xgtest/xinggou/script/start.sh xinggou-admin-api2 xinggou-admin-api 9050:8080"

                            serverIP = "116.62.24.252"
                            jettyPort = "11022"
                            serverName = "xgtest"
                            serverPasswd = "Jm689!test@xm"

                            jarPath="xinggou-admin-api/target/*.jar"

                            echo "workspace:${WORKSPACE}"
                            echo "`ls`"
                            echo "starting deploy to ${serverIP}......"


                            //发布war包到指定服务器，虚拟机文件目录通过shell脚本初始化建立，所以目录是固定的
                            //sh "sshpass -p ${serverPasswd} scp ${jarPath} ${serverName}@${serverIP}:htdocs/war"
                            //这里增加了一个小功能，在服务器上记录了基本部署信息，方便多人使用一套环境时问题排查，storge in {WORKSPACE}/deploy.log  & remoteServer:htdocs/war
                            Date date = new Date()
                            def deploylog="${date.toString()},${BUILD_USER} use pipeline  '${JOB_NAME}(${BUILD_NUMBER})' deploy ${jarPath} to server ${serverIP}"
                            println deploylog
                            sh "echo ${deploylog} >>${WORKSPACE}/deploy.log"
                            //sh "sshpass -p ${serverPasswd} scp ${WORKSPACE}/deploy.log ${serverName}@${serverIP}:htdocs/war"
                            //执行启动脚本
                            //sh "sshpass -p ${serverPasswd} ssh ${serverName}@${serverIP} '/home/xgtest/xinggou/script/start.sh xinggou-admin-api2 xinggou-admin-api 9050:8080' "
                        }
                    }


                }
            }

            stage('启动自动测试任务') {
                steps{
                    echo "starting interfaceTest......"
                    //script {

                        //将参数IP和Port传入到接口测试的job，需要确保接口测试的job参数可注入
                        //build job: ITEST_JOBNAME, parameters: [string(name: "dubbourl", value: "${serverIP}:${params.dubboPort}")]
                    //}
                }
            }

            stage('UI自动化测试') {
                steps{
                    echo "starting UITest......"
                    //这个项目不需要UI层测试，UI自动化与接口测试的pipeline脚本类似
                }
            }

            stage('性能自动化测试 ') {
                steps{
                    echo "starting performanceTest......"
                    //视项目需要增加性能的冒烟测试
                }
            }

            stage('通知人工验收'){
                steps{
                    script{

                            if(params.isCommitQA==false){
                                echo "不需要通知测试人员人工验收"
                            }


                    }
                }
            }

        }


        //pipeline运行结果通知给触发者
        post{
            success{
                script {
                    wrap([$class: 'BuildUser']) {
                        mail to: "${BUILD_USER_EMAIL }",
                                subject: "PineLine '${JOB_NAME}' (${BUILD_NUMBER}) result",
                                body: "${BUILD_USER}'s pineline '${JOB_NAME}' (${BUILD_NUMBER}) run success\n请及时前往${env.BUILD_URL}进行查看"
                    }
                }
            }
            failure{
                script {
                    wrap([$class: 'BuildUser']) {
                        mail to: "${BUILD_USER_EMAIL }",
                                subject: "PineLine '${JOB_NAME}' (${BUILD_NUMBER}) result",
                                body: "${BUILD_USER}'s pineline  '${JOB_NAME}' (${BUILD_NUMBER}) run failure\n请及时前往${env.BUILD_URL}进行查看"
                    }
                }

            }
            unstable{
                script {
                    wrap([$class: 'BuildUser']) {
                        mail to: "${BUILD_USER_EMAIL }",
                                subject: "PineLine '${JOB_NAME}' (${BUILD_NUMBER})结果",
                                body: "${BUILD_USER}'s pineline '${JOB_NAME}' (${BUILD_NUMBER}) run unstable\n请及时前往${env.BUILD_URL}进行查看"
                    }
                }
            }
        }

    }
}

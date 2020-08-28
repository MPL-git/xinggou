#!groovy

def call(Map map) {
    pipeline {
        agent any

        tools {
            maven "${map.maven}"
            jdk   "${map.jdk}"
        }
        options {
            disableConcurrentBuilds()
            timeout(time: 1, unit: 'HOURS')
            //保持构建的最大个数
            buildDiscarder(logRotator(numToKeepStr: '10'))
        }
        //pipeline的各个阶段场景
        stages {

            /*stage('初始化') {
                steps {
                    //一些初始化操作
                    script {
                        echo "starting init....."

                    }
                }
            }*/

            stage('拉取代码') {
                when {
                    expression{ return map.isFetchCode }
                }
                steps {
                    script{
                        if (map.cvs == "git") {
                            echo "use git fetchCode from ${map.repoUrl}......"
                            // Get some code from a GitHub repository
                            git credentialsId: map.credId, url: map.repoUrl, branch: map.repoBranch
                        }else if (map.cvs == "svn"){
                            echo "use svn fetchCode from ${map.repoUrl}......"
                            checkout([
                                    $class: 'SubversionSCM',
                                    locations: [
                                            [
                                                    cancelProcessOnExternalsFail: true,
                                                    credentialsId: map.credId,
                                                    depthOption: 'infinity',
                                                    ignoreExternalsOption: true,
                                                    local: map.repoLocal,
                                                    remote: map.repoUrl
                                            ]
                                    ],
                                    quietOperation: true,
                                    workspaceUpdater: [$class: 'UpdateUpdater']
                            ])
                        }
                    }

                }
            }

            stage('编译打包') {
                when {
                    expression{ return map.isCompile }
                }

                steps {
                    //编译和打包
                    script{
                        def poms = map.get("poms")
                        poms.each {
                            println("${it}")
                            sh "${it}"
                        }

                    }

                }
            }

            /*stage('单元测试') {
                when {
                    expression{ return map.isUnitTest }
                }

                steps {
                    echo "starting unitTest......"
                }
            }*/

            stage('部署测试环境') {
                when {
                    expression
                            { return map.isDeploy }
                }
                steps {
                    script{
                        echo "starting deploy......"
                        def apps = map.get("apps")
                        apps.each {
                            println("app:${it}")
                            it.workspace="${it.workspace}/${JOB_NAME}"
                            it.dockerContext="${it.workspace}/${it.dockerContext}"
                            it.dockerfile="${it.workspace}/${it.dockerfile}"
                            it.deployfile="${it.workspace}@libs/${it.deployfile}"

                            def remote = [:]
                            remote.name = it.serverName
                            remote.host = it.serverHost
                            remote.port = it.serverPort
                            remote.user = it.serverUser
                            remote.password = it.serverPassword
                            remote.allowAnyHosts = true
                            //执行启动脚本
                            //sshCommand remote: remote, command: "/home/xgtest/xinggou/script/deploy.sh ${it.home} ${it.appName} ${it.imageName} ${it.port} ${it.dockerfilePath} ${it.DUBBO_IP_TO_REGISTRY} ${it.SPRING_PROFILES_ACTIVE}"
                            sshCommand remote: remote, command: "chmod +x ${it.deployfile}"
                            sshCommand remote: remote, command: "${it.deployfile} ${it.workspace} ${it.appName} ${it.imageName} ${it.exposePort} ${it.hostPort} ${it.dockerContext} ${it.dockerfile} ${it.runParam}"
                        }


                        wrap([$class: 'BuildUser']) {
                            //这里增加了一个小功能，在服务器上记录了基本部署信息，方便多人使用一套环境时问题排查，storge in {WORKSPACE}/deploy.log
                            Date date = new Date()
                            def deploylog="${date.toString()},${BUILD_USER} use pipeline  '${JOB_NAME}(${BUILD_NUMBER})'"
                            println deploylog
                            sh "echo ${deploylog} >>${WORKSPACE}/deploy.log"
                        }
                    }


                }
            }

            /*stage('通知人工验收'){
                when {
                    expression{ return map.isNoticeQa }
                }
                steps{
                    echo "starting 通知人工验收......"


                }
            }*/

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

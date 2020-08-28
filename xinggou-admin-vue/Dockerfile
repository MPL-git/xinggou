FROM harbor.jm.com/library/nginx:18.09.7
MAINTAINER hdl <307926177@qq.com>

RUN rm /etc/nginx/conf.d/default.conf

ENV TZ=Asia/Shanghai

ADD build/nginx-test.conf /etc/nginx/conf.d/default.conf

COPY dist/ /usr/share/nginx/html/

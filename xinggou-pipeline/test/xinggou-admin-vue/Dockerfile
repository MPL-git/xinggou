FROM harbor.jm.com/library/nginx:18.09.7
MAINTAINER hdl <307926177@qq.com>

ENV TZ=Asia/Shanghai

RUN rm /etc/nginx/conf.d/default.conf

ADD build/nginx-test.conf /etc/nginx/conf.d/default.conf

COPY dist/ /usr/share/nginx/html/

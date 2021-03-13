# aframe
aframe
这是一个 java web  java jar 程序的快速开发框架

//nohup java -jar ahead-schedule-starter-jar-1.2.6-RELEASE >/dev/null 2>& 1

firewall-cmd --query-port=8083/tcp

firewall-cmd --zone=public --add-port=8083/tcp --permanent

firewall-cmd --query-port=8083/tcp

firewall-cmd --reload
sed -i 's/\r$//' start.sh
#! /bin/bash
nohup java -jar bydr-schedule-1.0-SNAPSHOT.jar >/dev/null 2>1 &

1，lsof -i:端口号

2，netstat -tunlp|grep 端口号

mkfs.ext4 /dev/sdb

 mount /dev/sdc /mnt/usb/

 du -sh /data
 
 df -hl
 
 gpg: revocation certificate stored as 'C:/Users/niujia/AppData/Roaming/gnupg/openpgp-revocs.d\45353EF140BDF6659BF4F7B67884ADDC2EC8D6D3.rev'
 public and secret key created and signed.
 
 pub   rsa2048 2020-04-14 [SC] [expires: 2022-04-14]
       45353EF140BDF6659BF4F7B67884ADDC2EC8D6D3
 uid                      niupengyu <505436991@qq.com>
 sub   rsa2048 2020-04-14 [E] [expires: 2022-04-14]
 
 $ gpg --list-keys
 # ->
 pub   2048R/XXXXXX 2017-09-14 [expires: 2018-05-14]
 uid       [ultimate] Yan Qian (lambeta) <qianyan.lambeta@gmail.com>
 其中 XXXXXX 就是你的 keyId。接下来必须发布你的公钥：
 
 $ gpg --keyserver hkp://subkeys.pgp.net --send-keys 9A1640F7A2551131612D51B12D83594B7B29D86A
 
 gpg --keyserver hkp://subkeys.pgp.net --send-keys 2D83594B7B29D86A
 
 $ gpg --keyserver hkp://pgp.mit.edu --send-keys XXXXXX

 
 $ gpg --keyserver hkp://pgp.mit.edu --search-keys xxxxx@xxxx.com # user email address
>gpg --keyserver http://keys.openpgp.org  --send-keys 123123123
>


docker run -tid -p 8581:8081 --name niu_nexus -e NEXUS_CONTEXT=nexus -v /usr/local/nexus3/nexus-data:/home/niupengyu/nexus3/  docker.io/sonatype/nexus3
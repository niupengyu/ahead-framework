package com.github.niupengyu.commons.zk;

import java.io.IOException;
import java.util.List;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


public class ZookeeperNodesDelete{


    /**
     * 递归删除zookeeper非空节点
     *
     * @param zk
     * @param nodeStr
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void deleteSubNode(ZooKeeper zk, String nodeStr) throws IOException, KeeperException, InterruptedException {
        String nodePath = nodeStr;
        //打印当前节点路径
        System.out.println("Node Path >>>>>>>>> [" + nodePath + " ]");
        if (zk.getChildren(nodePath, false).size() == 0) {
            //删除节点
            System.out.println("Deleting Node Path >>>>>>>>> [" + nodePath + " ]");
            //if(!nodePath.startsWith("/zookeeper/quota")){
                zk.delete(nodePath,-1);
            //}
        } else {
            //递归查找非空子节点
            List<String> list = zk.getChildren(nodeStr, true);
            for (String str : list) {
                nodePath=(nodePath.endsWith("/")?nodePath:nodePath+"/");
                str=str.startsWith("/")?str.substring(1):str;
                String node=nodePath+str;
                System.out.println(str+" TO "+node);
                deleteSubNode(zk, node);
            }
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String parentNodePath = "/";
        ZooKeeper zk = new ZooKeeper("localhost:" + 2181, 30000,
                new Watcher() {
                    // 监控所有被触发的事件
                    public void process(WatchedEvent event) {
                        System.out.println("已经触发了" + event.getType() + "事件！");
                    }
                });

        //递归删除节点所有子节点
        deleteSubNode(zk, parentNodePath);
        // 关闭zk连接
        zk.close();
    }
//./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic cctv1
}
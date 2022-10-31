package com.hyj.lock;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

//路由表信息
public class RPCRouterTable {

    //Key:接口名
    // Value:路由集合
    private final static ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> R_T = new ConcurrentHashMap<>();

    //根据接口名获取路由表
    public Set<Router> get(String iface) {
        return R_T.get(iface);
    }

    public void remove(Router router){
        CopyOnWriteArraySet<Router> routers = R_T.get(router.getIface());
        if(routers != null){
            routers.remove(router);
        }
    }

    public void add(Router router){
        CopyOnWriteArraySet<Router> routers =
                R_T.computeIfAbsent(router.getIface(), r -> new CopyOnWriteArraySet<Router>());
        routers.add(router);
    }




}

//路由信息
final class Router {
    private final String ip;
    private final Integer port;
    private final String iface;

    public Router(String ip, Integer port, String iface) {
        this.ip = ip;
        this.port = port;
        this.iface = iface;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getIface() {
        return iface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return Objects.equals(ip, router.ip) &&
                Objects.equals(port, router.port) &&
                Objects.equals(iface, router.iface);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, iface);
    }
}
package github.heyxhh.consistenthash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;


public class ConsistentHash<T> {
    // hash函数接口
    private final HashService hashService;

    // 每个机器节点关联的虚拟节点数量
    private final int numOfReplicas;

    // 环形虚拟节点
    private final SortedMap<Long, T> hashCircle = new TreeMap<Long, T>();


    /**
     * 构造函数，初始化数据
     * @param hashService 使用的hash方法
     * @param numOfReplicas 每个真实结点对应的虚拟结点的个数
     * @param nodes 真实结点的集合
     */
    public ConsistentHash(HashService hashService, int numOfReplicas, Collection<T> nodes) {
        this.hashService = hashService;
        this.numOfReplicas = numOfReplicas;
        for (T node : nodes) {
            addVNode(node);
        }

    }

    /**
     * 增加结点
     * @param node
     */
    public void addVNode(T node) {
        for (int i = 0; i < numOfReplicas; ++i) {
            hashCircle.put(hashService.hash(node.toString() + i), node);
        }
    }

    /**
     * 删除结点
     * @param node
     */
    public void removeNode(T node) {
        for (int i = 0; i < numOfReplicas; ++ i) {
            hashCircle.remove(hashService.hash(node.toString() + i));
        }
    }

    /**
     * 给定一个key，找到顺时针与之最近的结点
     * @param key
     * @return
     */
    public T get(String key) {
        if (hashCircle.isEmpty()) {
            return null;
        }

        long hash = hashService.hash(key);

        // 沿环的顺时针找到一个虚拟节点
        if (!hashCircle.containsKey(hash)) {
            SortedMap<Long, T> tailMap =  hashCircle.tailMap(hash);
            hash = tailMap.isEmpty() ? hashCircle.firstKey() : tailMap.firstKey();
        }

        return hashCircle.get(hash);
    }
    
}

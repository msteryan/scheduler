package schedule;

import java.util.List;
import java.util.Random;

public class CustomScheduler {
    private List<Node> nodeList;  //边缘节点列表
    private Pod pod;  //待调度pod

    public CustomScheduler(List<Node> nodeList, Pod pod) {
        this.nodeList = nodeList;
        this.pod = pod;
    }

    //随机调度 随机从node列表里取一个绑定pod
    public int RND() {
        while (true) {
            Random r = new Random();
            int i = r.nextInt(nodeList.size());
            Node node = nodeList.get(i);
            node.addPod(pod);
            return 1;
        }
    }

    //kubernetes默认调度算法
    public int DEAFULT() {
        double max_score = 0;
        int max_index = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            double cpu_score = (nodeList.get(i).getTotalcpu() - pod.getResquestcpu()) * (10 / nodeList.get(i).getTotalcpu());
            double ram_score = (nodeList.get(i).getTotalram() - pod.getResquestcpu()) * (10 / nodeList.get(i).getTotalcpu());
            double score = (cpu_score + ram_score) / 2;
            if (score > max_score) {
                max_score = score;
                max_index = i;
            }
        }
        nodeList.get(max_index).addPod(pod);
        return 1;
    }

    //遗传算法

}

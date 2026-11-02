import java.util.*;

public class Graphs {
    static class Graph {
        Map<String,List<String>> adj=new LinkedHashMap<>();
        void add(String s){adj.putIfAbsent(s,new ArrayList<>());}
        // A -> B means A must be deployed before B.
        void dependency(String a,String b){add(a);add(b);adj.get(a).add(b);}
        void bfs(String start){Set<String> seen=new HashSet<>();Queue<String> q=new ArrayDeque<>();q.add(start);seen.add(start);while(!q.isEmpty()){String x=q.remove();System.out.print(x+" ");for(String y:adj.getOrDefault(x,Collections.emptyList()))if(seen.add(y))q.add(y);}System.out.println();}
        void dfs(String x,Set<String> seen){if(!seen.add(x))return;System.out.print(x+" ");for(String y:adj.getOrDefault(x,Collections.emptyList()))dfs(y,seen);}
        List<String> topologicalOrder(){Map<String,Integer> in=new LinkedHashMap<>();for(String x:adj.keySet())in.put(x,0);for(List<String> ns:adj.values())for(String y:ns)in.put(y,in.get(y)+1);Queue<String> q=new ArrayDeque<>();for(String x:in.keySet())if(in.get(x)==0)q.add(x);List<String> order=new ArrayList<>();while(!q.isEmpty()){String x=q.remove();order.add(x);for(String y:adj.get(x)){in.put(y,in.get(y)-1);if(in.get(y)==0)q.add(y);}}return order.size()==adj.size()?order:Collections.emptyList();}
        boolean hasCycle(){return !adj.isEmpty()&&topologicalOrder().isEmpty();}
    }
    public static void main(String[] args){
        Graph g=new Graph();g.dependency("cart","pricing");g.dependency("pricing","inventory");g.dependency("inventory","payment");g.dependency("payment","notification");g.add("catalogue");
        System.out.print("BFS from cart: ");g.bfs("cart");System.out.print("DFS from cart: ");g.dfs("cart",new HashSet<>());System.out.println();
        System.out.println("Deployment order: "+g.topologicalOrder());System.out.println("Cycle detected: "+g.hasCycle());
        g.dependency("notification","cart");
        System.out.println("After adding notification -> cart, cycle detected: "+g.hasCycle());
        System.out.println("Deployment order: "+(g.hasCycle()?"Not possible - circular dependency":""+g.topologicalOrder()));
    }
}

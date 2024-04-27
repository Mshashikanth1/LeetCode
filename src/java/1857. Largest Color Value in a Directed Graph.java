//1857. Largest Color Value in a Directed Graph.java
//problem : https://leetcode.com/problems/largest-color-value-in-a-directed-graph/description/
class Solution{
    public int largestPathValue(String colors, int[][] edges){
        int n=colors.length();
        Map<Integer, List<Integer>> adj = new HashMap<>();
        int[] indegree= new int[n];

        for(int[] edge : edges){
            adj.computeIfAbsent(edge[0],k->new ArrayList<Integer>()).add(edge[1]);
            indegree[edge[1]]++;
        }

        int[][] count= new int[n][26];
        Queue<Integer> q= new LinkedList<>();

        //push all the nodes with indegree zero in the queue
        for(int i=0;i<n;i++){
            if(indegree[i]==0){
                q.offer(i);  //insert an element From the Queue
            }
        }


        int answer=1, nodesSeen=0;
        while(!q.isEmpty()){
            int node=q.poll();  //remove an element From the Queue
            answer=Math.max(answer, ++count[node][colors.charAt(node)-'a']);
            nodesSeen++;

            if(!adj.containsKey(node)){
                continue;
            }

            for(int neighbor : adj.get(node)){
                for(int i=0;i<26;i++){
                    //Try to Update the frequency of Colors for the neighbor to include path that use node->neighbor edge.
                    count[neighbor][i]=Math.max(count[neighbor][i],count[node][i]);
                }
                indegree[neighbor]--;
                if(indegree[neighbor]==0){
                    q.offer(neighbor);
                }
            }

        }
        return nodesSeen < n ? -1 : answer;
    }


}
























class Solution2{
    private int dfs(int node, String colors, Map<Integer,List<Integer>> adj, int[][] count, boolean[] visit, boolean[] inStack){
            //if the node is already in the stack , we have a cycle.
            if(inStack[node]){
                return Integer.MAX_VALUE;
            }

            if(visit[node]){
                return count[node][colors.charAt(node)-'a'];
            }

            //mark the current node as visited and part of the current recurrsion stack
            visit[node]=true;
            inStack[node]=true;

            if(adj.containsKey(node)){
                for (int neighbor : adj.get(node)){
                    if(dfs(neighbor,colors,adj,count,visit,inStack)==Integer.MAX_VALUE){
                        return Integer.MAX_VALUE;
                    }

                        for(int i=0;i<26;i++){
                            count[node][i]=Math.max(count[node][i],count[neighbor][i]);
                        }
                    }
                }
            
            //After all the incomming edges to the node are  processed, we count the color on the node itself.
            count[node][colors.charAt(node)-'a']++;

            //Remove the node from the stack.
            inStack[node]=false;
            return count[node][colors.charAt(node)-'a'];

    }

    public int largestPathValue(String colors, int[][] edges){
        int n=colors.length();
        Map<Integer,List<Integer>> adj= new HashMap<>();
        int[] indegree=new int[n];

        for(int[] edge : edges){
            adj.computeIfAbsent(edge[0],k->new ArrayList<Integer>()).add(edge[1]);
            indegree[edge[1]]++;
        }

        int[][] count =new int[n][26];
        boolean[] visit=new boolean[n];
        boolean[] inStack=new boolean[n];
        int answer=0;

        for(int j=0;j<n;j++){
            answer=Math.max(answer,dfs(j,colors,adj,count,visit,inStack));
        }

        return answer==Integer.MAX_VALUE ? -1 : answer;
    }
}

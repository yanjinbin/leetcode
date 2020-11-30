图论 
存图方式： https://oi-wiki.org/graph/save/#_12   http://bit.ly/2KfuHPN 
反向建图

前向星/链式前向星 
// 构建前向星代码
用len[i]来记录所有以i为起点的边在数组中的存储长度.
用head[i]记录以i为边集在数组中的第一个存储位置.

```java
        int cnt =0;
        int N = "顶点个数"
        int[][] data = new int[][]{
                {1, 2}, {2, 3}, {3, 4}, {1, 3}, {4, 1}, {1, 5}, {4, 5},
        };
        int cnt = 0;
        int[] len = new int[data.length + 1];
        int[] head = new int[data.length + 1];
        Arrays.sort(data, (o1, o2) -> o1[0] - o2[0]); // NlgN 排序
        for (int i = 0; i < data.length; i++) {
            int u = data[i][0], v = data[i][1];
            cnt++;
            len[u] = ++len[u];
            if (head[u] == 0) {
                head[u] = cnt;
            }
        }
        System.out.println(Arrays.toString(len));
        System.out.println(Arrays.toString(head));
```
// 链式前向星  避免排序

```java
    // 构建 链式前向星
    int[] head  = Arrays.fill(head,-1);
    void add(int u,int v,int w){   
        edge[cnt].to=v;
        edge[cnt].w=w;
        edge[cnt].next = head[u] // head[i] 表示顶点`i`的第一条边的数组下标，-1表示顶点`i`没有边
        head[u]=cnt++;
    }
    // 遍历
    for(int i =head[u];i!=-1;i=head[u].next){
    
    
    }   
```


重边： 在无向图中 (u,v) 和 (v,u) 算一组重边，而在有向图中，u->v  和 v->u 不为重边。




```





/*
#include<bits/stdc++.h>
using namespace std;
//设dp[i][j]为长度为i中最高位是j的windy数的个数
//方程 dp[i][j]=sum(dp[i-1][k]) 其中 abs(j-k)>=2 
int p,q,dp[15][15],a[15];
void init()
{
    for(int i=0;i<=9;i++)   dp[1][i]=1; //0,1,2,3,4...9都属于windy数 
    for(int i=2;i<=10;i++)
    {
        for(int j=0;j<=9;j++)`1`
        {
            for(int k=0;k<=9;k++)
            {
                if(abs(j-k)>=2) dp[i][j]+=dp[i-1][k]; 
            }
        }
    }//从第二位开始 每次枚举最高位j 并找到k 使得j-k>=2 
}
int work(int x) //计算<=x的windy数 
{
    memset(a,0,sizeof(a));
    int len=0,ans=0;
    while(x)
    {
        a[++len]=x%10;
        x/=10;
    }
    //分为几个板块 先求len-1位的windy数 必定包含在区间里的 34287 --> 9999-0
    for(int i=1;i<=len-1;i++)
    {
        for(int j=1;j<=9;j++)
        {
            ans+=dp[i][j];
        } 
    }
    //然后是len位 但最高位<a[len]的windy数 也包含在区间里 
    // 34287  ---->  29999-9999
    for(int i=1;i<a[len];i++)
    {
        ans+=dp[len][i];
    }
    // 34287
    // 3 4 2 8 7
    //  4 j从[0，4)
    // a[i+1]= 3 [0-8];

    //接着占有len位 最高位与原数相同的 最难搞的一部分
    // 39287 
    // 3 9 2 8 7
    //  a[i]       a[i+1]
    //   9 [0,0]  3  ok 30000-30999
    //   9 [0，1] 3  ok 31000-31999
    //   9 [0,2]  3 
    //   9  [0，3] 3 
    //   .........
    //    9 [0,5] 3  35000 -35999
    //   9 [0,8]  3  38000 -38999

     //  2  9 大于等于2 
     //  2  [0,0] 9, 39000-39099


     // 3 4 2 8 7
     // 4 3 
        0 3
        1 3
        2 3 
        3 3 
    


    for(int i=len-1;i>=1;i--)  
    {
        //i从最高位后开始枚举 
        for(int j=0;j<=a[i]-1;j++)
           {
            //j是i位上的数 
                if(abs(j-a[i+1])>=2)    ans+=dp[i][j]; //判断和上一位(i+1)相差2以上
                   //如果是 ans就累加 
           } 
        if(abs(a[i+1]-a[i])<2)       break;
      //  if(i==1)   ans+=1;
    }
    return ans;
}




int main()
{
    init();
    cin>>p>>q;
    cout<<work(q+1)-work(p)<<endl;
    return 0;
}

//DAG DP uva 437 

int babylon_sub(int c,int rot,int n){
    if(d[c][rot]!=-1){
        return d[c][rot];
    }
    d[c][rot] = 0;
    int base1, base2;
    if(rot==0){
        base1 = x[c];
        base2 = y[c];
    }
    if(rot==1){
        base1 = y[c];
        base2 = z[c];
    }
    if(rot==2){
        base1 = x[c];
        base2 = z[c];
    }
    for (int  i = 0; i < count; i++){
        if((x[i]<base1&&y[i]<base2)||(x[i]<base2&&y[i]<base1)){
            d[c][rot] = Max(d[c][rot], babylon_sub(i, 0, n) + z[i]);
        }
        if((y[i]<base1&&z[i]<base2)||(y[i]<base2&&z[i]<base1)){
            d[c][rot] = Max(d[c][rot], babylon_sub(i, 1, n)+x[i]);
        }
        if((x[i]<base1&&z[i]<base2)||(x[i]<base2&&z[i]<base1)){
            d[c][rot] = Max(d[c][rot], babylon_sub(i, 2, n) + y[i]);
        }
    }
    return d[c][rot];
}

int babylon(int n){
    for (int i = 0; i < n; i++){
        d[i][0] = -1;
        d[i][1] = -1;
        d[i][2] = -1;
    }
    int ans = 0;
    for (int i = 0; i < n; i++){
        ans = max(ans, babylon_sub(i, 0, n) + z[i]);
        ans = max(ans, babylon_sub(i, 1, n) + x[i]);
        ans = max(ans, babylon_sub(i, 2, n) + y[i]);
    }
}

// using namespace std;
int f[5][6000];
int n,a,b,root;
int ne[12000],po[12000],ru[12000];
void dp(int x)
{
    for(int i = po[x];i;i = ne[i])
    {
        dp(i);
        f[1][x] = max(max(f[1][x],f[0][i]+f[1][x]),f[0][i]);//这一个点选的时候转移：可以不选子节点，可以是子节点不选时最大值+自己的值，可以是只是子节点不选时的最大值
        f[0][x] = max(max(f[0][x],f[1][i]+f[0][x]),max(f[1][i],f[0][i]));//这一个点不选的时候转移：可以是自己，可以是子节点也不选，可以是子节点选时+自己，可以是仅仅子节点选时最大
    }
}
int main(){
    scanf("%d",&n);
    for(int i = 1;i <= n;i ++)
     scanf("%d",&f[1][i]);
    for(int i = 1;i <= n;i ++)
     {
         scanf("%d%d",&b,&a);
         // 解释下这里的数组含义。ne[] 数组相当于招到某个父节点下所有的子节点数组， po[x]=i,相当于父X找到第一个自己点i.
         ru[b] ++;
         ne[b] = po[a]; //原理与邻接表类似  只是里面存储的都是点  可手动模拟一下
         po[a] = b;
     }
    for(int i = 1;i <= n;i ++)
     if(ru[i] == 0)
       {
            root = i;//找树的根
            break;
       }
    dp(root);
    printf("%d",max(f[1][root],f[0][root]));
    return 0;
}


int main(){
    int t = 0;
    while(true){
        int n;
        std::cin >> n;
        if(n==0)
            break;
        t++;
        for (int i = 0; i < n;i++){
            std::cin >> x[i] >> y[i] >> z[i];
        }
        std::cout << "Case " << t << ":"
                  << "maximum height = " << babylon(n);
                  
        std::cout<<std::endl
    }
}


#include<bits/stdc++.h>
using namespace std;
const int maxm=1000005;
int t,n,m,cnt[2],p[2][maxm];
int dis[2][maxm];
set<pair<int,int> > q; 
struct node{
    int v,next,value;
}e[2][maxm];
void insertt(int id,int u,int v,int value){
    cnt[id]++;
    e[id][cnt[id]].v=v;
    e[id][cnt[id]].next=p[id][u];
    e[id][cnt[id]].value=value;
    p[id][u]=cnt[id];
}
void dij(int id){
    q.clear();
    dis[id][1]=0;
    q.insert(make_pair(0,1));
    while(!q.empty()){
        int u=q.begin()->second;
        q.erase(q.begin());
        for(int i=p[id][u];i!=-1;i=e[id][i].next){
            int v=e[id][i].v,value=e[id][i].value;
            if(dis[id][v]>dis[id][u]+value){
                q.erase(make_pair(dis[id][v],v));
                dis[id][v]=dis[id][u]+value;
                q.insert(make_pair(dis[id][v],v));
            }
        }
    }
}


int main(){
    cin>>t;
    while(t--){
        memset(p,-1,sizeof(p));
        memset(dis,0x3f,sizeof(dis));
        memset(e,0,sizeof(e));
        memset(cnt,0,sizeof(cnt));
        scanf("%d%d",&n,&m);
        for(int i=1;i<=m;i++){
            int u,v,value;
            scanf("%d%d%d",&u,&v,&value);
            insertt(0,u,v,value);
            insertt(1,v,u,value);
        }
        long long ans=0;
        dij(0);
        dij(1);
        for(int id=0;id<=1;id++){
            for(int i=1;i<=n;i++){
                ans+=dis[id][i];
            }
        }
        printf("%lld\n",ans);
    }
    return 0;
}*/

#include<iostream>
#include<cstdio>
#include<cstring>
#include<algorithm>
#include<vector>
#include<queue>
using namespace std;

#define INF 0x7f7f7f7f
const int MAXN=3005;
int n,m,ans;
struct node
{
    int to,cost,limit;
    bool operator<(const node& a)const
    {
        return cost>a.cost;//方向别反了
    }
};
vector<node> edge[MAXN];
int dis[MAXN],limit[MAXN];
bool visit[MAXN];

void input(void)
{
    cin>>n>>m;
    for(int i=1;i<=m;i++)
    {
        int a,b,x;
        cin>>a>>b>>x>>limit[i];
        edge[a].push_back(node{b,x,limit[i]});//无向图
        edge[b].push_back(node{a,x,limit[i]});
    }
}

int dijkstra(const int limit)
{
    priority_queue<node> q;//记得清空
    memset(visit,false,sizeof(visit));
    memset(dis,INF,sizeof(dis));
    dis[1]=0;
    q.push(node{1,0,limit});
    while(!q.empty())//模板
    {
        const int u=q.top().to;
        q.pop();
        if(visit[u])
         continue;
        visit[u]=true;
        for(auto v:edge[u])
         if(v.limit>=limit && dis[v.to]>dis[u]+v.cost)
         {//注意限制
            dis[v.to]=dis[u]+v.cost;
            q.push(node{v.to,dis[v.to],limit});
         }
    }
    return dis[n];
}

int main()
{
    input();
    for(int i=1;i<=m;i++)//枚举 m 次而非 n 次

     ans=max(ans,limit[i]*int(1e6)/dijkstra(limit[i]));//更新答案
    cout<<ans<<endl;
    return 0;
}


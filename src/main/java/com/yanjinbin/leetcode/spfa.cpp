#include<bits/stdc++.h>
#define inf 2000000000
using namespace std;
struct edge{
    int to,next,w;
}e[100001],e2[100001];
int n,m,s;
int head[1001],head2[1001];
int in[1001];
int d1[1001];
int d2[1001];

void spfa(){
    queue<int> q;
    q.push(s);
    in[s]=1;
    d1[s]=0;
    while(!q.empty()){
        int t=q.front();
        q.pop();
        in[t]=0;
        for(int i=head[t];i!=0;i=e[i].next){
            if(d1[t]+e[i].w<d1[e[i].to]){
                d1[e[i].to]=d1[t]+e[i].w;
                if(!in[e[i].to]){
                    in[e[i].to]=1;
                    q.push(e[i].to);
                }
            }
        }
    }
}
void spfa2(){
    queue<int> q;
    q.push(s);
    in[s]=1;
    d2[s]=0;
    while(!q.empty()){
        int t=q.front();
        q.pop();
        in[t]=0;
        for(int i=head2[t];i!=0;i=e2[i].next){
            if(d2[t]+e2[i].w<d2[e2[i].to]){
                d2[e2[i].to]=d2[t]+e2[i].w;
                if(!in[e2[i].to]){
                    in[e2[i].to]=1;
                    q.push(e2[i].to);
                }
            }
        }
    }
}
int main(){
    cin>>n>>m>>s;
    for(int i=1;i<=n;i++){
        d1[i]=inf;
        d2[i]=inf;
    }
    for(int i=1;i<=m;i++){
        int a,b,l;
        scanf("%d %d %d",&a,&b,&l);
        e[i].to=b;
        e[i].next=head[a];
        head[a]=i;
        e[i].w=l;
        e2[i].to=a;
        e2[i].next=head2[b];
        e2[i].w=l;
        head2[b]=i;
    }
    spfa();
    memset(in,0,sizeof(in));
    spfa2();
    int mx=0;
    for(int i=1;i<=n;i++){
        mx=max(mx,d1[i]+d2[i]);
    }
    cout<<mx;
    return 0;
}
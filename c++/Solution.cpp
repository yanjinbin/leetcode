#include <bits/stdc++.h>
using namespace std;

#define debug puts("pigtoria bling bling ⚡️⚡️")
#define For(i, j, k) for (int i = (int)(j); i <= (int)(k); i++)
#define FOR(i, a, b) for (int i = a; i < (b); i++)
#define FORd(i, a, b) for (int i = (b)-1; i >= a; i--)
#define ll long long
#define pii pair<int, int>
#define pll pair<ll, ll>
#define IT iterator
#define pii pair<int, int>
typedef long double ld;
typedef complex<ld> cd;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef pair<ld, ld> pd;
typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef vector<pi> vpi;
typedef vector<pl> vpl;
typedef vector<cd> vcd;

#define sqr(x) ((x) * (x))
#define pi acosl(-1)
#define MEM(x) memset(x, 0, sizeof(x))
#define MEMS(x) memset(x, -1, sizeof(x))
#define CLR(a, v) memset(a, v, sizeof(a));
#define CPY(a, b) memcpy(a, b, sizeof(a));
#define fi first
#define se second
#define mp make_pair
#define pb push_back
const ll INF = 1LL << 60;
const int INF = 0x3F3F3F3F, MOD = 10e9 + 7;
const int d4i[4] = {-1, 0, 1, 0}, d4j[4] = {0, 1, 0, -1};
const int d8i[8] = {-1, -1, 0, 1, 1, 1, 0, -1},
          d8j[8] = {0, 1, 1, 1, 0, -1, -1, -1};


class Solution {
typedef vector<int>::iterator vit;
public:
	struct Node // 队列中的必胜局面的数据结构.
	{
		int i, j, turn; // 老鼠位于i, 猫位于j, turn=0表示当前轮到老鼠走, turn=1表示当前轮到猫走
		Node(int i, int j, int turn):i(i),j(j), turn(turn){}
	};

	int catMouseGame(vector<vector<int>>& graph)
	{
		g = graph;
		n = graph.size();
		while(!q.empty())
		{
			q.pop();
		}
		memset(ans, 0, sizeof(ans));
		memset(indeg, 0, sizeof(indeg));
		bfs(); // 第一遍bfs初始化indeg以及ans, 注意, 这一次bfs 队列中并不是仅仅有必胜节点的
		bbfs(); // 第二遍bfs, 这次bfs仅仅允许必胜节点入队
		return ans[1][2][0]; // (1,2,0)是最初的局面
	}

	int n, ans[205][205][2], indeg[205][205][2]; // ans维护答案, indeg维护入度, ans取值为0表示僵局, 1表示老鼠必胜, 2表示猫必胜, n是顶点的个数
	queue<Node> q; // 局面队列
	vector<vector<int>> g;

	void bfs()
	{
		for (int i = 0;i<n; i++)
		{
			if (i)
			{
				q.push(Node(i,i,0));
				q.push(Node(i,i,1));
				q.push(Node(0,i,0));
				q.push(Node(0,i,1));
				ans[i][i][0] = ans[i][i][1] = 2; // 猫必胜局面
				ans[0][i][0] = ans[0][i][1] = 1; // 老鼠必胜局面
				indeg[i][i][0] = indeg[i][i][1] = 1; // 只是为了防止再次进队
			} // 推入必胜局面, 它们是bfs的出发点
		}
		while(!q.empty())
		{
			Node front = q.front(); // 从front开始拓展节点
			q.pop();
			for (vit i = g[front.turn?front.i:front.j].begin(); i!=g[front.turn?front.i:front.j].end(); i++) // front局面是猫走的话(即front.turn=1), 则上一次移动的就是老鼠
			{
				if (front.turn) // front是猫走, 所以上一步, 即子局面是老鼠走, 所以上一步老鼠处于*i, 即子局面是 (*i, front.j, 0)
				{
					if (!(indeg[*i][front.j][0]++)) // 如果此节点尚未被访问过
					{
						q.push(Node(*i,front.j,0));
					}
				}
				else // front是老鼠走, 所以上一步, 即子局面是猫走, 所以上一步猫处于*i, 即子局面是 (front.i, *i, 1)
				{
					if (!*i) // 猫不能来自于洞
					{
						continue;
					}
					if (!(indeg[front.i][*i][1]++))
					{
						q.push(Node(front.i,*i,1));
					}
				}
			}
		}
	}

	void bbfs()
	{
		for (int i = 0;i<n; i++)
		{
			if (i)
			{
				q.push(Node(i,i,0));
				q.push(Node(i,i,1));
				q.push(Node(0,i,0));
				q.push(Node(0,i,1));
			} // 推入必胜局面, 它们是bfs的出发点
		}
		while(!q.empty())
		{
			Node front = q.front();
			q.pop();
			for (vit i = g[front.turn?front.i:front.j].begin(); i!=g[front.turn?front.i:front.j].end(); i++) // front是老鼠走(即front.turn=0), 则子节点就是猫走, front是猫走(即front.turn=1),则子节点就是老鼠走
			{
				if (ans[front.i][front.j][front.turn] == 1) // 如果父局面是老鼠的必胜局面
				{
					if (front.turn) // 子节点是老鼠走, 子节点是 (*i, front.j, 0) 也是老鼠的必胜局面
					{
						if (!ans[*i][front.j][0]) // 注意, 已经有ans的不能再改变了,下同
						{
							ans[*i][front.j][0] = 1;
							q.push(Node(*i, front.j, 0)); // 进入必胜局面队列q
						}
					}
					else // 子节点是猫走, 子节点是 (front.i, *i, 1)
					{
						if (!*i) // 猫不能进洞
						{
							continue;
						}
						if (!ans[front.i][*i][1] && !--indeg[front.i][*i][1]) // 入度自减, 如果减少到0, 表明（front.i, *i, 1）这个子局面的所有父节点都是老鼠必胜的局面,则即便该子局面是猫走, 也是老鼠必胜的局面
						{
							ans[front.i][*i][1] = 1;
							q.push(Node(front.i, *i, 1)); // 进入必胜局面队列q
						}
					}
				}
				else // 如果父局面是猫的必胜局面, 注意, 在第二遍bfs过程中, 队列中仅仅保存必胜局面, 所以ans[front.i][front.j][front.turn]不是1就是2, 不可能是0
				{
					if (front.turn) // 子节点是老鼠走, 子节点是 (*i, front.j, 0)
					{
						if (!ans[*i][front.j][0] && !--indeg[*i][front.j][0]) // 入度自减, 如果减少到0, 表明（*i, front.j, 0）这个子局面的所有父节点都是猫必胜的局面,则即便该子局面是老鼠走, 也是猫必胜的局面
						{
							ans[*i][front.j][0] = 2;
							q.push(Node(*i, front.j, 0)); // 进入必胜局面队列q
						}
					}
					else // 子节点是猫走, 子节点是 (front.i, *i, 1)
					{
						if (!*i) // 猫不能进洞
						{
							continue;
						}
						if (!ans[front.i][*i][1])
						{
							ans[front.i][*i][1] = 2;
							q.push(Node(front.i, *i, 1)); // 进入必胜局面队列q
						}
					}
				}
			}
		}
	}
};

using VI = vector<int>;
using VVI = vector<VI>;
using VVVI = vector<VVI>;
class Solution1 {
public:
    int n;
    int helper(VVI& graph, int t, int x, int y, VVVI& dp){
        if (t == 2 * n) return 0; // \U0001f3c6比赛结束的几大条件按，参考A部分.不要return dp[t][x][y] = 2,想想为啥
        if (x == y) return dp[t][x][y] = 2;
        if (x == 0) return dp[t][x][y] = 1;

        if (dp[t][x][y] != -1) return dp[t][x][y]; // “不要搜了，爷\U0001f474已经搜好了”，老爷爷对小伙子说
        if (t % 2 == 0){ // 老鼠走\U0001f400
            bool catWin = true;
            for (int i = 0; i < graph[x].size(); ++ i){
                int nx = graph[x][i];
                int next = helper(graph, t + 1, nx, y, dp);
                if (next == 1) return dp[t][x][y] = 1; // 直接回家
                else if (next != 2) catWin = false; // 假如出现平地且没有回家，就说明下一步\U0001f431不可能赢
            }
            if (catWin) return dp[t][x][y] = 2;
            else return dp[t][x][y] = 0;
        }else{ // 猫猫走，和上面差不多啦
            bool mouseWin = true;
            for (int i = 0; i < graph[y].size(); ++ i){
                int ny = graph[y][i];
                if (ny == 0) continue;
                int next = helper(graph, t + 1, x, ny, dp);
                if (next == 2) return dp[t][x][y] = 2;
                else if (next != 1) mouseWin = false;
            }
            if (mouseWin) return dp[t][x][y] = 1;
            else return dp[t][x][y] = 0;
        }
    }


    int catMouseGame(vector<vector<int>>& graph) {
        n  = graph.size();
        VVVI dp(2 * n, VVI(n, VI(n, -1)));
        return helper(graph, 0, 1, 2, dp);
    }
};
class Solution {
public:
    struct node{
        int step, x, y, who, win;
        node(int a, int b, int c, int d, int e):step(a), x(b), y(c), who(d), win(e){}
    };

    int catMouseGame(vector<vector<int>>& graph) {
        int n = graph.size();
        int cat_lost[n][n], mice_lost[n][n];
        bool visit[n][n][2],next_to_zero[n];

        memset(cat_lost,0,sizeof(cat_lost));
        memset(mice_lost,0,sizeof(mice_lost));
        memset(visit,false,sizeof(visit));
        memset(next_to_zero,false,sizeof(next_to_zero));

        queue<node> q;
        for (auto i:graph[0]) next_to_zero[i] = true;

        for (int i = 1; i < n; ++i) {
            q.push(node(1,i,i,0,2));
            q.push(node(1,i,i,1,2));
            q.push(node(1,0,i,1,0));
            visit[i][i][0] = true;
            visit[i][i][1] = true;
            visit[0][i][0] = true;
            visit[0][i][1] = true;
        }

        while (!q.empty()) {
            auto temp = q.front();
            if (temp.step > 2*n) break;
            q.pop();
            if (temp.who == 0) {
                for (auto i:graph[temp.y]) {
                    if (!visit[temp.x][i][1] && i != 0) {
                        if (temp.win == 2) {
                            q.push(node(temp.step + 1, temp.x, i, 1, 2));
                            visit[temp.x][i][1] = true;
                        } else {
                            ++cat_lost[temp.x][i];
                            if (cat_lost[temp.x][i] == graph[i].size() - next_to_zero[i]) {
                                q.push(node(temp.step + 1, temp.x, i, 1, 0));
                                visit[temp.x][i][1] = true;
                            }
                        }
                    }
                }
            } else {
                for (auto i:graph[temp.x]) {
                    if (!visit[i][temp.y][0]) {
                        if (temp.win == 0) {
                            q.push(node(temp.step + 1, i, temp.y, 0, 0));
                            visit[i][temp.y][0] = true;
                            if (i == 1 && temp.y == 2) return 1;
                        } else {
                            ++mice_lost[i][temp.y];
                            if (mice_lost[i][temp.y] == graph[i].size()) {
                                q.push(node(temp.step + 1, i, temp.y, 0, 2));
                                visit[i][temp.y][0] = true;
                                if (i == 1 && temp.y == 2) return 2;
                            }
                        }
                    }
                }
            }
        }
        return 0;

    }
};
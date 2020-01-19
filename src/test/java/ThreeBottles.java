import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
// BFS 遍历问题看看
public class ThreeBottles {
 
    private int[] capacity;
 
    public ThreeBottles() {
        capacity = new int[] { 8, 5, 3 };
    }
 
    public boolean canMeasure(int n) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        int initialState = getState(new int[] { 8, 0, 0 });
        queue.add(initialState);
        visited.add(initialState);
        while (!queue.isEmpty()) {
            int state = queue.poll();
            if (match(state, n)) return true;
            int[] water = getWater(state);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int next = transfer(i, j, water);
                    if (next != -1 && !visited.contains(next)) {
                        queue.add(next);
                        visited.add(next);
                    }
                }
            }
        }
        return false;
    }
 
    private int getState(int[] water) {
        return water[0] << 8 | water[1] << 4 | water[2];
    }
 
    private int[] getWater(int state) {
        return new int[] { state >>> 8 & 15, state >>> 4 & 15, state & 15 };
    }
 
    private boolean match(int state, int n) {
        return (state >>> 8 & 15) == n || (state >>> 4 & 15) == n || (state & 15) == n;
    }
 
    private int transfer(int i, int j, int[] water) {
        int[] next = new int[] { water[0], water[1], water[2] };
        if (i != j && water[i] > 0 && water[j] < capacity[j]) {
            int transmission = Math.min(water[i], capacity[j] - water[j]);
            next[i] -= transmission;
            next[j] += transmission;
            return getState(next);
        }
        return -1;
    }
}

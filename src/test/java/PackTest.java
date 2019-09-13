import com.yanjinbin.leetcode.Pack;
import com.yanjinbin.leetcode.Solution;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PackTest {
    public static Pack INSTANCE = new Pack();
    public static Solution SOLUTION = new Solution();

    @Before
    public void init() {
        System.out.println("====背包问题等系列专攻====");
        System.out.println(" https://drive.google.com/open?id=1gMiJgnOQLcntvPrQXHJehiIuuEuTPBGx ");
        System.out.println("======资料链接如上=======");
    }

    @Test(timeout = 3000)
    public void maxConsecutiveSum() {
        // 经典问题分析
        System.out.println("====解法1====");
        int[] arr = new int[]{3, -1, -4, 9, 8, 7};
        Solution.shuffle(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
        assert INSTANCE.maxConsecutiveSum1(arr) == SOLUTION.maxSubArray(arr);
        System.out.println("====解法2====");
        assert INSTANCE.maxConsecutiveSum2(arr) == SOLUTION.maxSubArray(arr);
        System.out.println("====解法3====");
        assert INSTANCE.maxConsecutiveSum3(arr) == SOLUTION.maxSubArray(arr);
    }

}

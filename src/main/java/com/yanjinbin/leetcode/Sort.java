package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sort {

    // shuffle array lower bound  inclusive   upper bound exclusive
    public void shuffle(int[] nums, int lower, int upper) {
        Random rand = new Random();
        for (int i = lower; i < upper; i++) {
            int j = lower + rand.nextInt(i - lower + 1);
            swap(nums, i, j);
        }
    }

    // 交换数组值
    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 判断大小
    public boolean less(Comparable v, Comparable w) {
        if (v == w) return false;
        return v.compareTo(w) < 0;
    }
/*
    public void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (less(nums[j], nums[j + 1])) swap(nums, j, j + 1)
            }
        }
    }*/


    // 冒泡排序
    // i ∈ [0，n) , j ∈ [0，n-1-i) swap  neighbour elements  nums[j] nums[j+1]
    public void bubbleSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // i ∈ [0,n-1]
            //  j ∈ [0,0] ---> [0，n-1-i]
            for (int j = 0; j < n - 1 - i; j++) {
                if (!less(nums[j], nums[j + 1])) swap(nums, j, j + 1);
            }
        }
    }
    /*

    public void selectSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(nums[j], nums[min])) min = j;
            }
            swap(nums, min, i);
        }
    }*/

    // 选择排序 找最小值/最大值  然后交换对应坐标
    // i ∈[0,n-1), supposed i = min,  find minIdx from [i+1,n)
    public void selectSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(nums[j], nums[min])) min = j;
            }
            swap(nums, i, min);
        }
    }
/*
    public void insertSort(int[] nums) {
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (less(nums[j], nums[j - 1])) swap(nums, j, j - 1)
            }
        }
    }*/

    // 插入排序  sorted[0,i-1] , insert unordered  [i,n-1]
    public void insertSort(int[] nums) {
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(nums[j], nums[j - 1]); j--) {
                swap(nums, j, j - 1);
            }
        }
    }

    // 鸡尾酒🍸排序  左右两个方向进行交换   对 [2,3,4,5,1]  只需要 遍历2次即可, 数字随机排列情况下,都比较差
    public void cocktailSort(int[] nums) {
        // 注意 r的取值
        int l = 0, r = nums.length - 1;
        boolean swap = true;
        while (swap) {
            swap = false;
            for (int i = l; i < r; i++) {
                if (less(nums[i + 1], nums[i])) {
                    swap(nums, i, i + 1);
                    swap = true;
                }
            }
            for (int i = r; i > 0; i--) {
                if (less(nums[i], nums[i - 1])) {
                    swap(nums, i, i - 1);
                    swap = true;
                }
            }
        }
    }

    // 计数排序  不是比较排序哦!!!
    // 空间k = max-min+1; 时间 len(arr)+ k
    public void countSort(int[] arr) {
        int max = arr[0], min = arr[0];
        for (int val : arr) {
            if (max < val) max = val;
            if (min > val) min = val;
        }
        int len = max - min + 1;
        int[] bucket = new int[len];
        for (int val : arr) {
            bucket[val - min]++;
        }
        int sortedIndex = 0;
        for (int j = 0; j < len; j++) {
            while (bucket[j] > 0) {
                arr[sortedIndex++] = j + min;
                bucket[j]--;
            }
        }
    }

    // 理解二叉堆的表示法 https://www.cnblogs.com/skywang12345/p/3610187.html
    // 二叉堆从 索引0开始  child = 2i+1,2i+2, parent = (child-1)/2;
    public void heapSort(int[] nums) {
        int n = nums.length;
        // 堆的构造
        for (int k = n / 2 - 1; k >= 0; k--) {
            sink_recursive(nums, k, n);
        }
        // 下沉排序
        // while 或者  for 循环均可以
       /*
       int i = n - 1;
        while (i > 0) {
            swap(nums, 0, i--);
            n = n - 1;
            sink_recursive(nums, 0, n);
        }*/
        for (int i = n - 1; i > 0; i--) {
            swap(nums, 0, i);
            sink_recursive(nums, 0, --n);
        }
    }

    // 上浮 迭代版本 n  数组pq长度，k起始位置上浮
    public void swim(int[] pq, int k, int n) {
        int p = (k - 1) / 2;
        int tmp = pq[k];
        while (k > 0) {
            if (pq[p] > tmp) break;// 上浮到头了
            pq[k] = pq[p];
            k = p;
            p = (p - 1) / 2;
        }
        // 更新k的结果 和上面的pq[k] =pq[p] 连起来看 相当于swap了
        pq[k] = tmp;
    }

    // 上浮递归版本
    public void swim_recur(int[] pq, int k, int n) {
        if (k < 1) return;
        int p = (k - 1) / 2;
        int tmp = pq[k];
        if (pq[p] > tmp) return;
        // swap value and update current index
        swap(pq, k, p);
        k = p;
        swim_recur(pq, k, n);
    }

    public void sink(int[] pq, int k, int n) {
        // 注意这里的while 条件不等式 2k+1 < n 和 2k+1<=n  2k+2 <n的区别
        // if n=6, k 能不能取2的问题  k应该能取2, 因为 2只有一个叶子节点就是5
        // 记不住可以换用递归写法
        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j + 1 < n && less(pq[j], pq[j + 1])) j++;
            if (!less(pq[k], pq[j])) break;
            swap(pq, k, j);
            k = j;
        }
    }

    //下沉  递归写法
    public void sink_recursive(int[] pq, int k, int n) {
        int largest = k;
        int l = 2 * k + 1;
        int r = 2 * k + 2;
        if (l < n && pq[l] > pq[largest]) largest = l;
        if (r < n && pq[r] > pq[largest]) largest = r;
        if (largest != k) {
            swap(pq, largest, k);
            sink_recursive(pq, largest, n);
        }
    }


    public void heapSort2(int[] nums) {
        int n = nums.length;
        // 1 堆的构造
        for (int k = (n - 1) - 1 / 2; k >= 0; k--) {
            sink2(nums, k, n);
        }
        // 2  下沉排序
        while (n > 1) {
            swapHeap(nums, 1, n--);
            sink2(nums, 1, n);
        }
    }

    public void sink2(int[] pq, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(pq[j - 1], pq[j])) j++;
            if (!less(pq[k - 1], pq[j - 1])) break;
            swapHeap(pq, k, j);
            k = j;
        }
    }

    // 堆排序专用
    public void swapHeap(int[] nums, int i, int j) {
        int tmp = nums[i - 1];
        nums[i - 1] = nums[j - 1];
        nums[j - 1] = tmp;
    }


    public void heapSort1(int[] arr) {
        int n = arr.length;
        // build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapifyDown(arr, n, i);

        // one by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // move current root to end
            swap(arr, 0, i);
            // call max heapify on the reduced heap
            heapifyDown(arr, i, 0);
        }
    }

    // 下沉 递归版本
    public void heapifyDown(int arr[], int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // if left child exist and  larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // if right child exist and larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // if largest is not root
        if (largest != i) {
            swap(arr, i, largest);
            // recursively heapify  the affected sub-tree
            heapifyDown(arr, n, largest);
        }
    }

    public int[] heapSort3(int[] arr) {
        // 对 arr 进行拷贝，不改变参数内容
        int len = arr.length;
        buildMaxHeap(arr, len);

        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);

            len--;
            heapify(arr, 0, len);
        }
        return arr;
    }

    private void buildMaxHeap(int[] arr, int len) {
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    // sink 递归版本来自于https://www.runoob.com/w3cnote/heap-sort.html
    private void heapify(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, len);
        }
    }

    // 快排 top down方法
    public void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    public void quickSort(int[] nums, int l, int r) {
        int n = nums.length;
        if (n <= 1) return;
        if (l < r) {
            int j = partition0(nums, l, r);
            quickSort(nums, l, j - 1);
            quickSort(nums, j + 1, r);
        }
    }

    public int partition1(int[] nums, int l, int r) {
        //  要将pivotIdx 移到最左边或者最右边  否则的话 会出现死循环的情况出现
        int pivotIdx = l;
        l = l + 1;
        while (l <= r) {
            if (nums[l] > nums[pivotIdx] && nums[pivotIdx] > nums[r]) {
                swap(nums, l++, r--);
            }
            if (nums[l] < nums[pivotIdx]) l++;
            if (nums[r] > nums[pivotIdx]) r--;
        }
        // System.out.println("l:" + l + "r:" + r); // l -r = 1; 很有趣的发现
        swap(nums, pivotIdx, r);
        return r;
    }

    public int partition0(int[] nums, int l, int r) {
        int pivotIdx = l;
        l = l + 1;
        // 左右两边扫描
        while (l <= r) {
            if (nums[l] > nums[pivotIdx] && nums[pivotIdx] > nums[r]) {
                swap(nums, l++, r--);
            }
            if (nums[l] < nums[pivotIdx]) l++;
            if (nums[r] > nums[pivotIdx]) r--;
        }
        swap(nums, pivotIdx, r);
        return r;
    }

    public int partition2(int[] nums, int l, int r) {
        int pivotIdx = l;
        int index = l + 1;
        // 从左至右遍历
        for (int i = index; i <= r; i++) {
            if (nums[i] < nums[pivotIdx]) {
                swap(nums, i, index);
                index++;
            }
        }
        swap(nums, pivotIdx, index - 1);
        return index - 1;
    }


    // 归并排序 自顶向下和自顶向上
    public void mergeSort(int[] nums) {
        // 需要辅助数组的原因是因为不像更新的时候被覆盖
        int[] aux = new int[nums.length];
        mergeSort(nums, aux, 0, nums.length - 1);
    }

    // 递归版本
    public void mergeSort(int[] nums, int[] aux, int l, int r) {
        if (l >= r) return;
        int mid = l + (r - l) / 2;
        mergeSort(nums, aux, l, mid);
        mergeSort(nums, aux, mid + 1, r);
        merge(nums, aux, l, mid, r);
    }


    public void merge(int[] nums, int[] aux, int l, int mid, int r) {
        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {
            aux[k] = nums[k];
        }
        for (int k = l; k <= r; k++) {
            if (i > mid) nums[k] = aux[j++];
            else if (j > r) nums[k] = aux[i++];
            else if (less(aux[i], aux[j])) nums[k] = aux[i++];
            else nums[k] = nums[j++];
        }
    }

    // bottom up merge sort  比较难以抽象出来
    public void mergeSortBU(int[] nums) {
        int n = nums.length;
        int[] aux = new int[n];
        for (int len = 1; len < n; len = len * 2) {
            for (int l = 0; l < n - len; l = l + 2 * len) {
                int mid = l + len - 1;
                int hi = Math.min(l + len + len - 1, n - 1);
                merge(nums, aux, l, mid, hi);
            }
        }
    }


    // 希尔排序
    public void shellSort(int[] nums) {
        // 这个是希尔排序比较好理解的一种了
        int N = nums.length;
        int h = 1;
        while (h < N / 2) h = 2 * h + 1;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(nums[j], nums[j - h]); j = j - h) {
                    swap(nums, j, j - h);
                }
            }
            h = h / 2;
        }
    }
    // 二叉树排序


    // 基数排序 （非比较排序）
    // 基数排序  http://bit.ly/32mD3LV  O(N)  http://bit.ly/2HHKSUF
    public void radixSort(int[] nums) {
        radixSort(nums, 10);
    }

    public void radixSort(int[] arr, int radix) {
        int min = arr[0], max = arr[0];
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }
        int exp = 1;
        while ((max - min) / exp >= 1) {
            bitSort(arr, radix, min, exp);
            exp = exp * radix;
        }
    }

    //按位数排列
    public void bitSort(int[] arr, int radix, int min, int exp) {
        int N = arr.length;
        int[] buckets = new int[radix];
        int[] output = new int[N];

        // count frequency
        for (int i = 0; i < N; i++) {
            // make negative effective
            int bucketIndex = ((arr[i] - min) / exp) % radix;
            buckets[bucketIndex]++;
        }
        // cumulative sum
        for (int i = 1; i < radix; i++) {
            buckets[i] = buckets[i] + buckets[i - 1];
        }
        // sort by exponent  by reverse
        // for (int i = 0; i <= N-1; i++) {
        //  会反过来 如果 该数的该位为0  比如 2，3，1，11，  2，3，1的 顺序在1位的时候是1，2，3 在10位的时候会变成 3，2，1
        // exp =10 ，1-->bucketIndex = 0, bucket[bucketIndex]= 3, bucket --所以 反过来
        for (int i = N - 1; i >= 0; i--) {
            int bucketIndex = ((arr[i] - min) / exp) % radix;
            output[--buckets[bucketIndex]] = arr[i];
        }
        // update arr
        for (int i = 0; i < N; i++) arr[i] = output[i];

    }
    // 二叉树排序
    public void treeSort(int[] nums) {
        TreeNode bst = new TreeNode(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            insert(bst, nums[i]);
        }
        List<Integer> res = new ArrayList();
        inorder(bst, res);
        for (int i = 0; i < res.size(); i++) {
            nums[i] = res.get(i);
        }
    }

    // 中序
    private void inorder(TreeNode bst, List<Integer> res) {
        if (bst == null) return;
        inorder(bst.left, res);
        res.add(bst.val);
        inorder(bst.right, res);
    }

    private TreeNode insert(TreeNode bst, int val) {
        if (bst == null) return new TreeNode(val);
        if (val >= bst.val) {
            bst.right = insert(bst.right, val);
        }
        if (val < bst.val) {
            bst.left = insert(bst.left, val);
        }
        return bst;
    }


    public Random random = new Random();

    // shuffle linkedList
    public void shuffle(ListNode head) {
        ListNode cur = head;

        for (int i = 1; cur != null; i++) {
            int step = random.nextInt(i);
            // swap
            ListNode node = head;
            for (int j = 0; j < step; j++) {
                node = node.next;
            }
            // cur node swap
            if (node != cur) {
                int tmp = cur.val;
                cur.val = node.val;
                node.val = tmp;
            }
            cur = cur.next;
        }
    }

    // 判断 链表是否是升序
    public boolean isSorted(ListNode head) {
        if (head == null) return false;
        if (head.next == null) return true;
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (less(cur.next.val, cur.val)) return false;
            cur = cur.next;
        }
        return true;
    }

    private void swap(ListNode n1, ListNode n2) {
        if (n1 != n2) {
            int tmp = n1.val;
            n1.val = n2.val;
            n2.val = tmp;
        }
    }

    // 链表排序
    // 冒泡排序  这是最容易理解的版本本了
    public ListNode bubbleSort(ListNode head) {
        boolean isSwapped = true;
        for (ListNode current = head, tail = null; isSwapped && head != null; // 更新tail , current reset to head
             tail = current, current = head) {
            for (isSwapped = false; current.next != tail; current = current.next) {
                if (less(current.next.val, current.val)) {
                    swap(current, current.next);
                    isSwapped = true;
                }
            }
        }
        return head;
    }
}

反直觉诱又常用到的算法记录

// 返回x 二进制 右边第一个1代表的值。
```java
  // 1110 -->  0001 ---> 0010
  public int lowBit(int x) {
    return x & (~x + 1);
  }
```

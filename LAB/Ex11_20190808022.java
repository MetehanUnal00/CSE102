import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Ex11_20190808022 {
    public static void main(String args[]){

    }
public static int numOfTriplets(int[] arr, int sum) {
    Arrays.sort(arr);
    int n = arr.length;
    int count = 0;

    for (int i = 0; i < n - 2; i++) {
        int j = i + 1;
        int k = n - 1;
        while (j < k) {
            if (arr[i] + arr[j] + arr[k] >= sum) {
                k--;
            } else {
                count += k - j;
                j++;
            }
        }
    }
    return count;
}
public static int kthSmallest(int[] arr, int k) {
    PriorityQueue<Integer> queue = new PriorityQueue<>();

    for(int num: arr){
        queue.add(num);
    }

    while(k-- > 1){
        queue.poll();
    }

    return queue.peek();
}

public static String subSequence(String str) {
    int n = str.length();
    int[] dp = new int[n];
    String result = "";

    for (int i = 0; i < n; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
            if (str.charAt(i) > str.charAt(j)) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }

    int maxIndex = 0;
    for (int i = 1; i < n; i++) {
        if (dp[i] > dp[maxIndex]) {
            maxIndex = i;
        }
    }

    char currentChar = str.charAt(maxIndex);
    result = currentChar + result;

    for (int i = maxIndex; i >= 0; i--) {
        if (currentChar > str.charAt(i) && dp[maxIndex] - 1 == dp[i]) {
            result = str.charAt(i) + result;
            currentChar = str.charAt(i);
        }
    }
    return "n^2\n" + result;
}
public static int isSubString(String str1, String str2) {
    int m = str1.length(), n = str2.length();
    for (int i = 0; i <= m - n; i++) {
        int j;
        for (j = 0; j < n; j++) {
            if (str1.charAt(i + j) != str2.charAt(j)) {
                break;
            }
        }
        if (j == n) {
            return i;
        }
    }
    return -1;
}
public static void findRepeats(int[] arr, int n) {
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int num : arr) {
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
    }

    for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
        if (entry.getValue() > n) {
            System.out.println("Number: " + entry.getKey() + ", count: " + entry.getValue());
        }
    }
}


}

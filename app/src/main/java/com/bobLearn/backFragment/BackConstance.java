package com.bobLearn.backFragment;

import com.bobLearn.R;

/**
 * created by cly on 2019-05-21
 */
public class BackConstance {

    public static int count;
    public static int[] colors = {R.color.black_1E, R.color.gray_38, R.color.blue_64, R.color.gold_E2};

    public static int getCount() {
        count++;
        return count;
    }

    public static int getColor() {
        return colors[count % colors.length];
    }
}

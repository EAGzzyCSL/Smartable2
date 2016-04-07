package my;


public class MyUtil {
    //一个工具类，提供一些静态方法供调用
    private static String[] weekEtoCString = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};

    public static String weekEtoC(int e) {
        return "周" + weekEtoCString[e];
    }

    public static int dpToPxInCode(float density, int dp) {
        return (int) (density * dp + 0.5);
    }

    public static int pxToDpInCode(float density, int px) {
        return (int) (px / density + 0.5);
    }
}

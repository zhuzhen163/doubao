package com.doubao.shop.tools;


public class ConfigUtils {

    public static int DETAIL_TO_OTHER = 0;//1:商品详情跳转购物车,2:跳转主页

    public static final String TOKEN="token";
    public static void saveToken(String iscancle){
        SpUtils.put(TOKEN,iscancle);
    }
    public static String getToken(){
        return (String)SpUtils.get(TOKEN,"");
    }

    public static final String USERID="USERID";
    public static void saveUserId(String id){
        SpUtils.put(USERID,id);
    }
    public static String getUserId(){
        return (String)SpUtils.get(USERID,"");
    }

    /**
     * 保存用户名
     */
    public static final String USER_NAME = "user_name";

    public static void saveUserName(String username) {
        if (StringUtils.isNotBlank(username)){
            SpUtils.put(USER_NAME, username);
        }
    }

    public static String getUserName() {
        return (String) SpUtils.get(USER_NAME, "");
    }

    /**
     * 保存用户手机号
     */
    public static final String PHONE = "phone";

    public static void savePhone(String phone) {
        if (StringUtils.isNotBlank(phone)){
            SpUtils.put(PHONE, phone);
        }
    }

    public static String getPhone() {
        return (String) SpUtils.get(PHONE, "");
    }

    /**
     * 保存选中用户地址
     */
    public static final String ADDRESS_ID = "addressId";

    public static void saveAddressId(String id) {
        if (StringUtils.isNotBlank(id)){
            SpUtils.put(ADDRESS_ID, id);
        }
    }

    public static String getAddressId() {
        return (String) SpUtils.get(ADDRESS_ID, "0");
    }

    /**
     * 账户刷新
     */
    public static final String ACCOUNT_REFRESH = "account_refresh";

    public static void setAccountRefresh(boolean refresh) {
            SpUtils.put(ACCOUNT_REFRESH, refresh);
    }

    public static boolean getAccountRefresh() {
        return (boolean) SpUtils.get(ACCOUNT_REFRESH, false);
    }

    /**
     * 购物车刷新
     */
    public static final String CART_REFRESH = "cart_refresh";

    public static void setCartRefresh(boolean refresh) {
        SpUtils.put(CART_REFRESH, refresh);
    }

    public static boolean getCartRefresh() {
        return (boolean) SpUtils.get(CART_REFRESH, false);
    }

    /**
     * 首次安装的时候显示引导页
     */
    public static final String show_guide = "show_guide";

    public static void saveGuide(boolean isguide) {
        SpUtils.put(show_guide, isguide);
    }

    public static boolean getSaveShow() {
        return (boolean) SpUtils.get(show_guide, false);
    }

    /**
     * 清空数据
     */
    public static void cleatSP() {
        SpUtils.clear();
    }


}

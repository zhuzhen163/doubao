package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

public class LoginActivityModel {

    public void toLogin(String mobile,String code, final LoginInterFace loginInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).toLogin(mobile,code).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        loginInterFace.toLoginSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        loginInterFace.toLoginFail(e.message);
                    }
                });

    }

    public interface LoginInterFace{
        void toLoginSuccess(String s);
        void toLoginFail(String s);
    }

    //获取验证码
    public void getSmsCode(String userMobile, final MessageCodeInterFace codeInterFace){
//        String rsa = "";
//        try {
//            rsa = RSAUtils.encryptByPublicKey((userMobile+""+sendType).getBytes());
//            rsa = rsa.replace("\n","");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Http.getHttpService(UrlHelper.BASE_URL).getSMSCode(userMobile)
                .compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String message) {
                        codeInterFace.getSuccessSms(message);
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        codeInterFace.getFailSms(e.message);
                    }

                });

    }
    public interface MessageCodeInterFace{
        void getSuccessSms(String s);
        void getFailSms(String s);
    }
}

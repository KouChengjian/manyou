package org.acg12.net;



import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.bean.Video;
import org.acg12.listener.HttpRequestListener;

import java.util.List;

import rx.Subscription;

public interface HttpRequest {

    Subscription albumList(String pinId ,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription paletteList(String pinId ,HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription bangumiList(String page ,HttpRequestListener<List<Video>> httpRequestListener);

    Subscription videoList(String page , int type , HttpRequestListener<List<Video>> httpRequestListener);

    Subscription palettePreview(String boardId ,String pinId ,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription bangumiPreview(String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription playBangumi(String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription playVideo(String av ,HttpRequestListener<Video> httpRequestListener);

//    void updateToken(User user, HttpRequestListener<User> httpRequestListener);
//
//    Subscription login(User user, HttpRequestListener<User> httpRequestListener);
//
//    void register(User user, HttpRequestListener<User> httpRequestListener);
//
//    void verify(User user, HttpRequestListener<User> httpRequestListener);
//
//    void resetPwd(User user, HttpRequestListener<User> httpRequestListener);
//
//    void userinfo(User user, HttpRequestListener<User> httpRequestListener);
}
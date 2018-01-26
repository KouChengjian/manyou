package org.acg12.net;

import android.content.Context;

import com.acg12.lib.entity.User;
import com.acg12.lib.listener.HttpRequestListener;

import org.acg12.entity.Album;
import org.acg12.entity.Home;
import org.acg12.entity.Palette;
import org.acg12.entity.Search;
import org.acg12.entity.Tags;
import org.acg12.entity.Video;
import org.acg12.net.base.HttpRequest;
import org.acg12.utlis.URLEncoderUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by DELL on 2016/11/16.
 */
public class HttpRequestImpl implements HttpRequest {

    private Context mContext ;
    private static HttpRequestImpl instance;

    public HttpRequestImpl(Context mContext){
        this.mContext = mContext;
        instance = this;
    }

    public static HttpRequestImpl getInstance() {
        return instance;
    }

    @Override
    public Subscription index(User user, final HttpRequestListener<Home> httpRequestListener) {
        return RetrofitClient.with(user).index()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONObject index = RetrofitClient.getJSONObject(data , "list");
                            Home home = new Home();
                            home.setCover(RetrofitClient.getString(index , "cover"));

                            List<Tags> list = new ArrayList<>();
                            JSONArray array = RetrofitClient.getJSONArray(index , "list");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Tags tags = new Tags();
                                tags.setTitle(RetrofitClient.getString(item , "title"));
                                tags.setCover(RetrofitClient.getString(item , "cover"));
                                list.add(tags);
                            }
                            home.setTagsList(list);

                            httpRequestListener.onSuccess(home);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription albumList(User user , String pinId, final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).albumList("album",pinId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Album> list = new ArrayList<>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "album");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Album album = new Album();
                                album.setPinId(RetrofitClient.getString(item ,"pinId"));
                                album.setContent(RetrofitClient.getString(item ,"content"));
                                album.setResWidth(RetrofitClient.getInt(item ,"resWidth"));
                                album.setResHight(RetrofitClient.getInt(item ,"resHight"));
                                album.setLove(RetrofitClient.getInt(item ,"love"));
                                album.setFavorites(RetrofitClient.getInt(item ,"favorites"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlList");
                                if(urls != null){
                                    album.setImageUrl(RetrofitClient.getString(urls , 0));
                                }
                                list.add(album);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription paletteList(User user ,String pinId, final HttpRequestListener<List<Palette>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).paletteList("palette",pinId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Palette> list = new ArrayList<Palette>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "palette");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Palette palette = new Palette();
                                palette.setBoardId(RetrofitClient.getString(item ,"boardId"));
                                palette.setName(RetrofitClient.getString(item ,"name"));
                                palette.setNum(RetrofitClient.getInt(item ,"num"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlAlbum");
                                ArrayList<String> urlAlbum = new ArrayList<String>();
                                for(int j = 0 , n = urls.length() ; j < n ; j ++) {
                                    urlAlbum.add(RetrofitClient.getString(urls , j));
                                }
                                palette.setUrlAlbum(urlAlbum);
                                list.add(palette);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription bangumiList(User user ,String page,final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).bangumiList(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPic(RetrofitClient.getString(item ,"pic"));
                                video.setUpdateContent(RetrofitClient.getString(item ,"updateContent"));
                                //video.setUrlInfo(RetrofitClient.getString(item ,"urlInfo"));
                                //video.setBmId(RetrofitClient.getString(item,"urlInfo").split("/anime/")[1]);
                                video.setBmId(RetrofitClient.getString(item,"bmId"));
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription videoList(User user ,String page , int type ,final HttpRequestListener<List<Video>> httpRequestListener) {
        String requestType = "";
        if(type == 0){
            requestType = "default-24";
        }else if(type == 1){
            requestType = "default-25";
        }else if(type == 2){
            requestType = "default-27";
        }
        Subscription subscription = RetrofitClient.with(user).videoList("video",requestType,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setAid(RetrofitClient.getString(item ,"aid"));
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPlay(RetrofitClient.getString(item ,"play"));
                                video.setVideoReview(RetrofitClient.getString(item ,"videoReview"));
                                video.setFavorites(RetrofitClient.getString(item ,"favorites"));
                                video.setAuthor(RetrofitClient.getString(item ,"author"));
                                video.setDescription(RetrofitClient.getString(item ,"description"));
                                video.setCreate(RetrofitClient.getString(item ,"create"));
                                video.setPic(RetrofitClient.getString(item ,"pic").replace("_320x200.jpg" , ""));
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription palettePreview(User user ,String boardId ,String pinId , final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).palettePreview("palettealbum",pinId ,boardId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Album> list = new ArrayList<Album>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "album");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Album album = new Album();
                                album.setPinId(RetrofitClient.getString(item ,"pinId"));
                                album.setContent(RetrofitClient.getString(item ,"content"));
                                album.setResWidth(RetrofitClient.getInt(item ,"resWidth"));
                                album.setResHight(RetrofitClient.getInt(item ,"resHight"));
                                album.setLove(RetrofitClient.getInt(item ,"love"));
                                album.setFavorites(RetrofitClient.getInt(item ,"favorites"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlList");
                                if(urls != null){
                                    album.setImageUrl(RetrofitClient.getString(urls , 0));
                                }
                                list.add(album);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription bangumiPreview(User user ,String bangumiId , final HttpRequestListener<Video> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).bangumiPreview(bangumiId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            Video video = new Video();
                            JSONObject json = RetrofitClient.getJSONObject(data , "video");
                            video.setTitle(RetrofitClient.getString(json,"title"));
                            video.setSbutitle(RetrofitClient.getString(json,"sbutitle"));
                            video.setDescription(RetrofitClient.getString(json,"description"));
                            video.setPic(RetrofitClient.getString(json,"pic").replace("_225x300.jpg",""));

                            List<Video> episodeList = new ArrayList<>();
                            JSONArray allEpisode = RetrofitClient.getJSONArray(json ,"bangumiVideoList");
                            for (int i = 0 , num = allEpisode.length() ; i < num ; i++){
                                Video item = new Video();
                                JSONObject object = RetrofitClient.getJSONObject(allEpisode , i);
                                item.setTitle(RetrofitClient.getString(object,"title"));
                                item.setAid(RetrofitClient.getString(object,"aid"));
                                episodeList.add(item);
                            }
                            video.setEpisodeList(episodeList);

                            List<Video> seasonList = new ArrayList<>();
                            JSONArray allSeason = RetrofitClient.getJSONArray(json ,"quarterVideoList");
                            for (int i = 0 , num = allSeason.length() ; i < num ; i++){
                                Video item = new Video();
                                JSONObject object = RetrofitClient.getJSONObject(allSeason , i);
                                item.setTitle(RetrofitClient.getString(object,"title"));
                                item.setPic(RetrofitClient.getString(object,"pic"));
                                item.setBmId(RetrofitClient.getString(object ,"bmId"));
                                seasonList.add(item);
                            }
                            video.setSeasonList(seasonList);
                            httpRequestListener.onSuccess(video);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription playBangumi(User user ,String av,final HttpRequestListener<Video> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).playUrl("bangumi" , av)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            Video video = new Video();
                            JSONObject json = RetrofitClient.getJSONObject(data , "info");
                            video.setPlayUrl(RetrofitClient.getString(json,"url"));
                            video.setCid(RetrofitClient.getString(json,"cid"));
                            video.setPic(RetrofitClient.getString(json,"img"));
                            httpRequestListener.onSuccess(video);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription playVideo(User user ,String av, HttpRequestListener<Video> httpRequestListener) {
        return null;
    }

    @Override
    public Subscription searchAlbum(User user ,String key , String page,final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).searchAlbum(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Album> list = new ArrayList<Album>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "album");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Album album = new Album();
                                album.setPinId(RetrofitClient.getString(item ,"pinId"));
                                album.setContent(RetrofitClient.getString(item ,"content"));
                                album.setResWidth(RetrofitClient.getInt(item ,"resWidth"));
                                album.setResHight(RetrofitClient.getInt(item ,"resHight"));
                                album.setLove(RetrofitClient.getInt(item ,"love"));
                                album.setFavorites(RetrofitClient.getInt(item ,"favorites"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlList");
                                if(urls != null){
                                    album.setImageUrl(RetrofitClient.getString(urls , 0));
                                }
                                list.add(album);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchPalette(User user ,String key , String page,final HttpRequestListener<List<Palette>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).searchPalette(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Palette> list = new ArrayList<Palette>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "palette");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Palette palette = new Palette();
                                palette.setBoardId(RetrofitClient.getString(item ,"boardId"));
                                palette.setName(RetrofitClient.getString(item ,"name"));
                                palette.setNum(RetrofitClient.getInt(item ,"num"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlAlbum");
                                ArrayList<String> urlAlbum = new ArrayList<String>();
                                for(int j = 0 , n = urls.length() ; j < n ; j ++) {
                                    urlAlbum.add(RetrofitClient.getString(urls , j));
                                }
                                palette.setUrlAlbum(urlAlbum);
                                list.add(palette);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchBangumi(User user ,String key , String page, final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).searchBangumi(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPic(RetrofitClient.getString(item ,"pic"));
                                video.setUpdateContent(RetrofitClient.getString(item ,"updateContent"));
                                //video.setUrlInfo(RetrofitClient.getString(item ,"urlInfo"));
                                video.setBmId(RetrofitClient.getString(item,"urlInfo").split("/anime/")[1]);
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchVideo(User user ,String key , String page,final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).searchVideo(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setAid(RetrofitClient.getString(item ,"aid"));
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPlay(RetrofitClient.getString(item ,"play"));
                                video.setVideoReview(RetrofitClient.getString(item ,"videoReview"));
                                video.setFavorites(RetrofitClient.getString(item ,"favorites"));
                                video.setAuthor(RetrofitClient.getString(item ,"author"));
                                video.setDescription(RetrofitClient.getString(item ,"description"));
                                video.setCreate(RetrofitClient.getString(item ,"create"));
                                video.setPic(RetrofitClient.getString(item ,"pic").replace("_320x200.jpg" , ""));
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchKeyList(User user, String key, final HttpRequestListener<List<Search>> httpRequestListener) {
        return RetrofitClient.with(user).searchKeyList(URLEncoderUtil.encode(key))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Search> list = new ArrayList<>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.getJSONArray(data , "list");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Search search = new Search();
                                search.setSearchId(RetrofitClient.getInt(item ,"pageid"));
                                search.setTitle(RetrofitClient.getString(item ,"title"));
                                search.setSource(RetrofitClient.getString(item ,"source"));
                                list.add(search);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
    }

//    @Override
//    public void updateToken(final User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public Subscription login(final User user, final HttpRequestListener<User> httpRequestListener) {
//        Subscription subscription = RetrofitClient.with(user).login(user.getUsername() , user.getPassword())
////                .map(new Func1<User, User>() {
////                    @Override
////                    public User call(User user) {
////                        return user;
////                    }
////                })
//                .flatMap(new Func1<User, Observable<User>>() {
//                    @Override
//                    public Observable<User> call(User u) {
//                        user.setC(u.getC());
//                        user.setUid(u.getUid());
//                        user.setTokenKey(u.getTokenKey());
//                        user.setExpired(u.getExpired());
//                        user.setUpdateTime(u.getUpdateTime());
//                        return RetrofitClient.with(user).userinfo(u.getUid()+"");
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .doOnNext(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.e("doOnNext2","调用");
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.e("User","成功");
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e("Throwable",throwable.toString()+"");
//                    }
//                });
//        return subscription;
//    }
//
//    @Override
//    public void register(User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public void verify(User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public void resetPwd(User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public void userinfo(final User user, final HttpRequestListener<User> httpRequestListener) {
////        final ApiService apiService = RetrofitClient.with(user);
////        Observable<ResponseBody> observable1 =  apiService.updataToken(user.getTokenKey());
////        Observable<ResponseBody> observable2 =  apiService.userinfo(user.getUid() + "");
////        Observable.merge(observable1, observable2)
////                .subscribeOn(Schedulers.newThread())
////                //.subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new Subscriber<ResponseBody>() {
////                    @Override
////                    public void onCompleted() {
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
////                    }
////
////                    @Override
////                    public void onNext(ResponseBody obj) {
////                        try {
////                            Log.e("obj",obj.string()+"===");
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                });
//    }
}
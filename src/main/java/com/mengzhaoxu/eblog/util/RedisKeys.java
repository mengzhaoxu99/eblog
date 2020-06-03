/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mengzhaoxu.eblog.util;

/**
 * Redis所有Keys
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2017-07-18
 */
public class RedisKeys {


    public static String getPost(String key) {
        return "day:rank:" + key;
    }
    public static String getRankPost(String key) {
        return "rank:post:" + key;
    }
    public static String getWeekRank(String key) {
        return "week:rank:" + key;
    }


//    public static String getGoodsList(String key) {
//        return "getGoodsList:" + key;
//    }
//
//
//
//    public static String isGoodsOver(String key) {
//        return "goodsover" + key;
//    }
//    public static String getSpikeKey(String key){
//        return "spike" + key;
//    }
//
//    public static String getGoodsVo(String key){
//        return "GoodsVo:" + key;
//    }
//    public static String getGoods(String key){
//        return "Goods:" + key;
//    }
//
//
//    public static String getSysConfigKey(String key){
//        return "sys:config:" + key;
//    }
//
//    public static String getUserfigKey(String key){
//        return "user:" + key;
//    }
//    public static String getSessionKey(String key){
//        return "sessionid:" + key;
//    }
}

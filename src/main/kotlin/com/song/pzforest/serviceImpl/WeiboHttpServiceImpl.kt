package com.song.pzforest.serviceImpl

import cn.hutool.core.date.DateTime
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import com.song.pzforest.service.PicService
import com.song.pzforest.service.WeiboHttpServcie
import com.song.pzforest.service.WeiboService
import io.ktor.client.utils.EmptyContent.status
import mu.KotlinLogging
import okhttp3.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils
import java.io.File
import java.io.IOException
import kotlin.math.log

/**
 * 调用微博接口的所有方法
 */
@Service
open class WeiboHttpServiceImpl :WeiboHttpServcie {
    val client: OkHttpClient = OkHttpClient()

    var count =0

    val access_token: List<String> = listOf(
        "2.00Cr7AhCFlne1D50224d9dadjIQdBE",
        "2.00Cr7AhCxWGpzC70aeafc18fUlNDnB",
        "2.00Cr7AhCbIFBmC2d5ff123e3QHloED",
        "2.00Cr7AhC48cw9D47acd367abcSMdDC",
        "2.00Cr7AhC9pAvNE3f037476cagSG15E")

    fun getAccess_token():String{
        val temp = access_token.get(count)
        count ++
        if(count==access_token.size-1)
        {
            count=0
        }
        return temp
    }
    @Autowired
    lateinit var weiboService: WeiboService
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var picService: PicService
    /*
   发送微博（文字+图片）
    */
    override fun send(status:String,picContent: File) {
        val url = "https://api.weibo.com/2/statuses/share.json"
        //post
        val image = RequestBody.create(MediaType.parse("image/*"),picContent)
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("access_token", getAccess_token())
            .addFormDataPart("pic",picContent.path,image)
            .addFormDataPart("status",status+" http://pzforest.com")
            .addFormDataPart("rip","106.55.180.208")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val call: Call =client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logger.debug { e }
            }
            override fun onResponse(call: Call, response: Response) {


                val check=response.body()?.string()
                logger.info { check }

                if (check != null) {
                    if(check.contains("<center><h1>502 Bad Gateway</h1></center>")){
                        logger.debug { "调用接口失败，返回Bad Gateway,尝试重新发送" }
                        Thread.sleep(5000)
                        send(status,picContent)
                    }
                }
                try {
                    val str =JSONObject.parseObject(check)
                    if(str.getString("error_code")==null)
                    {
                        logger.info { "post成功，贴文Id:${str.getString("idstr")},发送时间:${str.getString("created_at")},内容:${str.getString("text")},图片:${str.getString("original_pic")}" }

                    }
                    else
                        logger.info { "post失败,错误代码${str.getString("error_code")},错误信息:${str.getString("error")}" }

                }catch (e:JSONException)
                {
                    logger.debug { e.message }
                    logger.debug { response.body()?.string() }
                }
                catch (e:Exception)
                {
                    logger.debug { e.message }
                    logger.debug { response.body()?.string() }
                }
                finally {

                }

            }
        })
    }
        /*
        发送微博
         */
    override fun send(status: String)
    {
        val url = "https://api.weibo.com/2/statuses/share.json"
        logger.info { "字符长度${status.length}" }
        val requestBody = FormBody.Builder()
            .add("status", status+" http://pzforest.com")
            .add("access_token",  getAccess_token())
            .add("rip","106.55.180.208")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val call: Call =client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logger.debug { e }
            }
            override fun onResponse(call: Call, response: Response) {
                val check=response.body()?.string()
                logger.info { check }
                if (check != null) {
                    if(check.contains("<center><h1>502 Bad Gateway</h1></center>")){
                            logger.info { "调用接口失败，返回Bad Gateway,尝试重新发送" }
                            Thread.sleep(5000)
                            send(status)
                    }
                    else
                    {
                        try {
                            val str =JSONObject.parseObject(response.body()?.string())

                            if(str.getString("error_code")==null)
                            { logger.info { "post成功，贴文Id:${str.getString("idstr")},发送时间:${str.getString("created_at")},内容:${str.getString("text")},图片:${str.getString("original_pic")}" } }
                            else
                                logger.info { "post失败,错误代码${str.getString("error_code")},错误信息:${str.getString("error")}" }
                        }catch (e:JSONException){
                            logger.debug { e.message }
                            logger.debug { response.body()?.string() }
                        }catch (e:Exception)
                        {
                            logger.debug { e.message }
                            logger.debug { response.body()?.string() }
                        }
                    }
                }




            }
        })
    }


        /**
         * 获取at我的人
         */
    override fun getAt() {
            logger.info { "当前时间:${DateTime.now()},开始检测at我的人" }
            val accesstoken =getAccess_token()
       val url = "https://api.weibo.com/2/statuses/mentions.json?access_token=$accesstoken"


        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val call: Call =client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logger.debug { e }
            }
            override fun onResponse(call: Call, response: Response) {
                val res:JSONObject = JSONObject.parseObject(response.body()?.string())
                val statuses =res.getJSONArray("statuses")
                val size: Int = statuses.size
                var status=ArrayList<JSONObject>()

                for (i in 0..size-1)
                {
                    var file: File? =null
                    var temp =statuses.getJSONObject(i)
                    var text = temp.getString("text")
                    text =text.replace("培正森林","").replace("http://pzforest.com","").replace("@","")
                   var weiboid =temp.get("idstr").toString()
                    var user = temp.getJSONObject("user")
                    var userid = user.getString("id")

                    //如果有这条微博的数据那就跳过
                    if(ObjectUtils.isEmpty(weiboService.selectByWeiboId(weiboid)))
                    {
                        val pic =temp.getString("original_pic")
                        //如果有图片的话获取图片
                        if(!ObjectUtils.isEmpty(pic))
                        {
                            logger.info { "has Image"+temp.getString("original_pic")}
                            file= picService.downPic(temp.getString("original_pic"))
                        }

                        Thread.sleep(5000);
                        weiboService.addWeibo(text,file,1,userid,weiboid)
                        logger.info { "有新的At消息，内容是:$text ,用户id:$userid,该条微博id:$weiboid" }
                        status.add(temp)
                    }




                }


            }
        })
    }

    override fun getAccessToken(clientId: String,client_secret :String,code:String,redirect_uri:String) {
        val url = "https://api.weibo.com/oauth2/access_token"

        val requestBody = FormBody.Builder()
            .add("client_id", clientId)
            .add("client_secret", client_secret)
            .add("grant_type","authorization_code")
            .add("code",code)
            .add("redirect_uri",redirect_uri)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val call: Call =client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logger.debug { e }
            }
            override fun onResponse(call: Call, response: Response) {
                val str = response.body()?.string()
                logger.info { str }
            }
        })
    }

}
package com.song.pzforest.serviceImpl

import com.alibaba.fastjson.JSONObject
import com.song.pzforest.service.PicService
import com.song.pzforest.service.WeiboHttpServcie
import com.song.pzforest.service.WeiboService
import mu.KotlinLogging
import okhttp3.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils
import java.io.File
import java.io.IOException

@Service
open class WeiboHttpServiceImpl :WeiboHttpServcie {
    val client: OkHttpClient = OkHttpClient()
    val access_token="2.00Cr7AhCFlne1D50224d9dadjIQdBE"
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
            .addFormDataPart("access_token", access_token)
            .addFormDataPart("pic",picContent.path,image)
            .addFormDataPart("status",status+"http://pzforest.com")
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
        /*
        发送微博
         */
    override fun send(status: String)
    {
        val url = "https://api.weibo.com/2/statuses/share.json"

        val requestBody = FormBody.Builder()
            .add("status", status+"http://pzforest.com")
            .add("access_token", access_token)
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


        /**
         * 获取at我的人
         */
    override fun getAt() {
       val url = "https://api.weibo.com/2/statuses/mentions.json?access_token=$access_token"


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
                    logger.info { text }
                    //如果有这条微博的数据那就跳过
                    if(!ObjectUtils.isEmpty(weiboService.selectByWeiboId(weiboid)))
                    {
                        continue
                    }

                  val pic =temp.getString("original_pic")
                    //如果有图片的话获取图片
                    if(!ObjectUtils.isEmpty(pic))
                    {
                        logger.info { "has Image"+temp.getString("original_pic")}
                        file= picService.downPic(temp.getString("original_pic"))
                    }

                    Thread.sleep(2000);
//                    weiboService.addWeibo(text,file,1,userid,weiboid)

                    status.add(temp)
                }


            }
        })
    }

}
package _RedGold__.main.function

import okhttp3.*
import java.io.IOException

object HttpConn {

    /**
    *
    * @param url: 7201/path/path1?data_name=test&data_2=test2@post(or get)
    *
    * */
    fun OkHttpClient.send(url: String, onResponse: (Response) -> Unit, onFailure: (IOException) -> Unit = {}) {
        val urlPathParts = url.split("/")

        val urld = HttpUrl.Builder().scheme("http").host("localhost").port(urlPathParts[0].toInt())
        val body = FormBody.Builder().add("secret", "MTQzMTg2ODgyNDEwMzTMyNjg1MAG64EbcxDwcyNjg1MAMAG6cYi64EbTMyNjg1YTUtYThkNC03ZjKlMAG64EbcxDwc")
        var isPost = false

        for (path in urlPathParts.drop(1)) {
            if (path.contains("?")) {
                urld.addPathSegment(path.substringBefore("?"))

                val querySection = path.substringAfter("?") //table=donations@post
                val pureData = querySection.substringBefore("@") //table=donations
                val directive = querySection.substringAfter("@", "").lowercase() //post

                val datas = pureData.split("&")
                when (directive) {
                    "get" -> for (data in datas) urld.addEncodedQueryParameter(data.substringBefore("="), data.substringAfter("=", ""))
                    "post" -> {
                        isPost = true
                        for (data in datas) body.addEncoded(data.substringBefore("="), data.substringAfter("=", ""))
                    }
                }
            }
            else urld.addPathSegment(path)
        }

        val request = Request.Builder().url(urld.build())
        if (isPost) request.post(body.build())
        else request.get()


        this.newCall(request.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    onResponse(it)
                }
            }
        })
    }
}
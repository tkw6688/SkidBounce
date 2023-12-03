//package net.ccbluex.liquidbounce.features.module.modules.skid
//
//import javazoom.jl.decoder.JavaLayerException
//import javazoom.jl.player.advanced.PlaybackEvent
//import javazoom.jl.player.advanced.PlaybackListener
//import javazoom.jl.player.advanced.jlap
//import net.ccbluex.liquidbounce.LiquidBounce
//import net.ccbluex.liquidbounce.features.module.Module
//import net.ccbluex.liquidbounce.features.module.ModuleCategory
//import net.ccbluex.liquidbounce.file.FileManager.dir
//import net.ccbluex.liquidbounce.utils.ClientUtils
//import net.ccbluex.liquidbounce.value.IntegerValue
//import java.io.*
//import java.net.MalformedURLException
//import java.net.URL
//import java.net.URLConnection
//
//
//object MusicPlayer : Module("MusicPlayer", ModuleCategory.SKID) {
//
//    private val id by IntegerValue("MusicID", 0, 0..2147483647)
//
//    private var jsonstring = ""
//    private var preurl : Int = 0
//    private var lasturl : Int = 0
//    private var finalurl=""
//
//    fun getinfo() {
//        jsonstring=""
//        val url = URL("https://autumnfish.cn/song/url?id=$id")
//        val connection = url.openConnection()
//        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
//            var line: String?
//            while (inp.readLine().also { line = it } != null) {
//                jsonstring= line.toString()
//            }
//        }
//    }
//
//    //idk why i write this and it's running so i will not remove
//    fun geturl(string: String) {
//        preurl=0
//        lasturl=0
//        finalurl=""
//        preurl=jsonstring.indexOf("http://",0,false)
//        lasturl=jsonstring.indexOf("\",\"br\"",0,false)
//        try {
//            finalurl = jsonstring.substring(preurl..lasturl - 1)
//        }catch (e: Exception){
//            ClientUtils.displayChatMessage("§8[§9§l${LiquidBounce.CLIENT_NAME}§8] §3Wrong ID!")
//            //Can't Fix this
//            state=false
//        }
//    }
//
//    @Throws(MalformedURLException::class)
//    fun download(url1:String) {
//        // 下载网络文件
//        var bytesum = 0
//        var byteread = 0
//        val url = URL(url1)
//        try {
//            val conn: URLConnection = url.openConnection()
//            val inStream: InputStream = conn.getInputStream()
//            val fs = FileOutputStream(File(dir,"cache.mp3"))
//            val buffer = ByteArray(1204)
//            var length: Int
//            while (inStream.read(buffer).also { byteread = it } != -1) {
//                bytesum += byteread
//                //println(bytesum)
//                fs.write(buffer, 0, byteread)
//            }
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun play(){
//        try {
//            jlap.playMp3(File(dir,"cache.mp3"),  // 创建播放器，播放音频输入流
//                object : PlaybackListener() {
//                    // 触发的监听
//                    override fun playbackStarted(arg0: PlaybackEvent) { // 播放开始前触发
//                        ClientUtils.displayChatMessage("§8[§9§l${LiquidBounce.CLIENT_NAME}§8] §3Song Playing")
//                    }
//
//                    override fun playbackFinished(arg0: PlaybackEvent) { // 播放结束后触发
//                        ClientUtils.displayChatMessage("§8[§9§l${LiquidBounce.CLIENT_NAME}§8] §3Song Ended")
//                    }
//                })
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: JavaLayerException) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onEnable(){
//        getinfo()
//        geturl(jsonstring)
//        download(finalurl)
//        play()
//        //state=false
//    }
//
//}
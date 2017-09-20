package cn.mycommons.mymockserver.service

import cn.mycommons.mymockserver.bean.Mock
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.*
import org.apache.log4j.Logger

import java.util.function.Consumer

/**
 * HttpResponseUtil <br/>
 * Created by Leon on 2017-08-29.
 */
class MockResponseUtil {

    private static final Logger LOGGER = Logger.getLogger(MockResponseUtil.class)

    static HttpResponse gen(Mock mock) {
        long begin = System.currentTimeMillis()

        def version = HttpVersion.HTTP_1_1
        def ok = HttpResponseStatus.OK
        ByteBuf buffer = Unpooled.buffer()

        def mockResp = mock.response
        if (mockResp.version) {
            version = HttpVersion.valueOf(mockResp.version)
        }
        if (mockResp.code) {
            ok = HttpResponseStatus.valueOf(mockResp.code)
        }

        while (true) {
            if (mockResp.body) {
                if (mockResp.body.text) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.text.bytes)
                    break
                }
                if (mockResp.body.textFile) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.textFile.text.bytes)
                    break
                }

                if (mockResp.body.json) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.json.bytes)
                    break
                }
                if (mockResp.body.jsonFile) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.jsonFile.text.bytes)
                    break
                }

                if (mockResp.body.xml) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.xml.bytes)
                    break
                }
                if (mockResp.body.xmlFile) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.xmlFile.text.bytes)
                    break
                }

                if (mockResp.body.html) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.html.bytes)
                    break
                }
                if (mockResp.body.htmlFile) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.htmlFile.text.bytes)
                    break
                }

                if (mockResp.body.bytes) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.bytes)
                    break
                }
                if (mockResp.body.bytesFile) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.bytesFile.text.bytes)
                    break
                }
            }
            break
        }

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(version, ok, buffer)
        HttpUtil.setContentLength(response, buffer.readableBytes())
        if (mockResp.headers) {
            mockResp.headers.header.entrySet().forEach(new Consumer<Map.Entry<String, Object>>() {
                @Override
                void accept(Map.Entry<String, Object> entry) {
                    response.headers().add(entry.getKey(), entry.getValue())
                }
            })
        }
        // "Content-type":"text/html;charset=UTF-8"
        response.headers().add("Content-type", "text/plain;charset=UTF-8")

        long end = System.currentTimeMillis()

        if (mock.control && mock.control.delay > 0) {
            long time = mock.control.delay * 1000 - (end - begin)
            LOGGER.debug("delay time = " + time)

            if (time > 0) {
                sleep(time)
            }
        }

        // LOGGER.info(response)

        return response
    }
}
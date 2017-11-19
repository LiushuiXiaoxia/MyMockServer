package cn.mycommons.mymockserver.service

import cn.mycommons.mymockserver.bean.Mock
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.*
import org.apache.log4j.Logger

import java.util.function.Consumer

/**
 * MockResponseGenerator <br/>
 * Created by Leon on 2017-08-29.
 */
class MockResponseGenerator {

    private static final Logger LOGGER = Logger.getLogger(MockResponseGenerator.class)

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

        String contentType = null
        while (true) {
            if (mockResp.body) {
                if (mockResp.body.text) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.text.bytes)
                    contentType = "text/plain"
                    break
                }
                if (mockResp.body.textFile) {
                    contentType = "text/plain"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.textFile.bytes)
                    break
                }

                if (mockResp.body.json) {
                    contentType = "application/json"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.json.bytes)
                    break
                }
                if (mockResp.body.jsonFile) {
                    contentType = "application/json"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.jsonFile.bytes)
                    break
                }

                if (mockResp.body.xml) {
                    contentType = "text/xml"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.xml.bytes)
                    break
                }
                if (mockResp.body.xmlFile) {
                    contentType = "text/xml"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.xmlFile.bytes)
                    break
                }

                if (mockResp.body.html) {
                    contentType = "text/html"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.html.bytes)
                    break
                }
                if (mockResp.body.htmlFile) {
                    contentType = "text/html"
                    buffer = Unpooled.wrappedBuffer(mockResp.body.htmlFile.bytes)
                    break
                }

                if (mockResp.body.bytes) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.bytes)
                    break
                }
                if (mockResp.body.bytesFile) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.bytesFile.bytes)
                    break
                }

                if (mockResp.body.file) {
                    buffer = Unpooled.wrappedBuffer(mockResp.body.file.bytes)
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
        if (contentType != null && contentType.length() != 0) {
            response.headers().add("Content-type", contentType + ";charset=UTF-8")
        } else {
            response.headers().add("Content-type", "text/plain;charset=UTF-8")
        }

        response.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)

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
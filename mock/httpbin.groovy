mock {
    desc "mock login"
    request {
        url "httpbin.org"
        host "httpbin.org"
        headers {
            // header "aaa", "bbb"
        }
        params {
        }
    }
    response {
        code 200
        headers {
            header "header_key_1", "header_value_1"
            header "header_key_2", "header_value_2"
            header "header_time", new Date().format("yyyy-MM-dd HH:mm:ss")
        }
        body {
            // text "text"
            // textFile "file.txt"

            json """{"key":"value"}"""
            jsonFile "file.txt"

            xml "<xml/>"
            xmlFile "file.txt"

            html "<html/>"
            htmlFile "file.txt"

            file "file.txt"
        }
    }
    control {
        // delay 2
    }
}
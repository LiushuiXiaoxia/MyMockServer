mock {
    desc "a.groovy"
    request {
        scheme "http"
        host "ele.me"
        port 80
        path "/api/login/"
        method "GET"
        params {
            param "param_key_1", "param_value_1"
            param "param_key_2", "param_value_2"
        }
        headers {
            header "header_key_1", "header_value_1"
            header "header_key_2", "header_value_2"
            header "time", new Date().format("yyyy-MM-dd HH:mm:ss")
        }
        body {

        }
    }
    response {
        version 'HTTP/1.1'
        code 200
        headers {
            header "header_key_1", "header_value_1"
            header "header_key_2", "header_value_2"
            header "time", new Date().format("yyyy-MM-dd HH:mm:ss")
        }
        body {
            text "text"
            textFile "file.txt"

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
        delay 5
    }
}
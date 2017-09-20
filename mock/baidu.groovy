mock {
    desc "baidu"
    request {
        host "baidu.com"
    }
    response {
        code 200
        headers {
            header "header_key_1", "header_value_1"
            header "header_key_2", "header_value_2"
            header "time", new Date().format("yyyy-MM-dd HH:mm:ss")
        }
        body {
            json """{"key":"value"}"""
        }
    }
    control {
        delay 2
    }
}
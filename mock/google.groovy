mock {
    // enable true
    desc "template"
    request {
        url "https://www.google.com.hk/"
    }
    response {
        // version 'HTTP/1.1'
        // code 200
        headers {
            header "header_key_1", "header_value_1"
            header "time", new Date().format("yyyy-MM-dd HH:mm:ss")
        }
        body {
            file "file.txt"
        }
    }
    control {
        delay 1
    }
}
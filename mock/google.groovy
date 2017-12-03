mock {
    // enable true
    desc "google mock"
    request {
        url "https://www.google.*"
    }
    response {
        // version 'HTTP/1.1'
        // code 200
        headers {
            header "header_key_1", "header_value_1"
            header "header_key_time", new Date().format("yyyy-MM-dd HH:mm:ss SSS")
        }
        body {
            file "file.txt"
        }
    }
}
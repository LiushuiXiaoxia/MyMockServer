package cn.mycommons.mymockserver

import com.google.common.base.Joiner

class MmsMain {

    static void main(String[] args) {
        println "Path: ${new File("./").absolutePath}"
        println "Cmd: mms ${Joiner.on(" ").join(args)}"

        // new CmdMain().execute("-i -c config".split())
        new CmdMain().execute(args)
    }
}
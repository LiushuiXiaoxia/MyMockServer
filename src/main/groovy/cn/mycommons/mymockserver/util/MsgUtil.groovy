package cn.mycommons.mymockserver.util

/**
 * MsgUtil <br/>
 * Created by Leon on 2017-09-08.
 */
class MsgUtil {

    public static final String ANSI_RESET = "\u001B[0m"
    public static final String ANSI_BLACK = "\u001B[30m"
    public static final String ANSI_RED = "\u001B[31m"
    public static final String ANSI_GREEN = "\u001B[32m"
    public static final String ANSI_YELLOW = "\u001B[33m"
    public static final String ANSI_BLUE = "\u001B[34m"
    public static final String ANSI_PURPLE = "\u001B[35m"
    public static final String ANSI_CYAN = "\u001B[36m"
    public static final String ANSI_WHITE = "\u001B[37m"

    private static final String MSG = """
          _____                    _____                    _____          
         /\\    \\                  /\\    \\                  /\\    \\         
        /::\\____\\                /::\\____\\                /::\\    \\        
       /::::|   |               /::::|   |               /::::\\    \\       
      /:::::|   |              /:::::|   |              /::::::\\    \\      
     /::::::|   |             /::::::|   |             /:::/\\:::\\    \\     
    /:::/|::|   |            /:::/|::|   |            /:::/__\\:::\\    \\    
   /:::/ |::|   |           /:::/ |::|   |            \\:::\\   \\:::\\    \\   
  /:::/  |::|___|______    /:::/  |::|___|______    ___\\:::\\   \\:::\\    \\  
 /:::/   |::::::::\\    \\  /:::/   |::::::::\\    \\  /\\   \\:::\\   \\:::\\    \\ 
/:::/    |:::::::::\\____\\/:::/    |:::::::::\\____\\/::\\   \\:::\\   \\:::\\____\\
\\::/    / ~~~~~/:::/    /\\::/    / ~~~~~/:::/    /\\:::\\   \\:::\\   \\::/    /
 \\/____/      /:::/    /  \\/____/      /:::/    /  \\:::\\   \\:::\\   \\/____/ 
             /:::/    /               /:::/    /    \\:::\\   \\:::\\    \\     
            /:::/    /               /:::/    /      \\:::\\   \\:::\\____\\    
           /:::/    /               /:::/    /        \\:::\\  /:::/    /    
          /:::/    /               /:::/    /          \\:::\\/:::/    /     
         /:::/    /               /:::/    /            \\::::::/    /      
        /:::/    /               /:::/    /              \\::::/    /       
        \\::/    /                \\::/    /                \\::/    /        
         \\/____/                  \\/____/                  \\/____/         
"""

    public static void msg() {
        println(ANSI_GREEN + MSG + ANSI_RESET)
    }
}
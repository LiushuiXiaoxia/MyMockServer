package cn.mycommons.mymockserver;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SearchTest <br/>
 * Created by Leon on 2017-11-19.
 */
public class SearchTest {

    boolean match(String text, String key) {
        if (key.contains("*")) {
            String newKey = key.replaceAll("\\*", ".+");
            System.out.println("newKey = " + newKey);
            return text.matches(newKey);
        } else {
            return text.contains(key);
        }
    }

    @Test
    public void testSearch() {
        Assert.assertTrue(match("http://www.baidu.com/", "baidu.com"));
        Assert.assertTrue(match("http://www.baidu.com/", "*www*com*"));
        Assert.assertTrue(match("http://www.baidu.com/", "*www.*.com*"));

        Assert.assertTrue("123-456".matches("\\d{3}.\\d{3}"));
        Assert.assertTrue("http://www.baidu.com/".matches(".+"));
    }
}

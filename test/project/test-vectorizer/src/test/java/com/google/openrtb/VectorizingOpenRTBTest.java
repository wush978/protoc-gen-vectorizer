package com.google.openrtb;

import com.github.wush978.vectorizer.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by wush.wu on 2016/11/18.
 */
@RunWith(JUnit4.class)
public class VectorizingOpenRTBTest {
    @Test
    public void testVectorizingOpenRTB() throws Exception {

        String srcPath = new File("src/test/resources/bidRequest.pb").getAbsolutePath();
        String dstPath = new File("src/test/resources/vector.pb").getAbsolutePath();
        FileInputStream input = new FileInputStream(srcPath);
        FileOutputStream output = new FileOutputStream(dstPath);
        while(true) {
            OpenRtb.BidRequest bidRequest = OpenRtb.BidRequest.parseDelimitedFrom(input);
            if (bidRequest == null) break;
            Vector.SparseVector vector = com.google.openrtb.Vectorizer.apply(bidRequest).build();
            vector.writeDelimitedTo(output);
        }
        output.close();

    }

}

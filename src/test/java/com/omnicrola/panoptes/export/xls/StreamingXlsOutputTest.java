package com.omnicrola.panoptes.export.xls;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.StreamingOutput;
import java.io.OutputStream;

import static com.omnicrola.test.util.TestUtil.assertImplementsInterface;
import static org.mockito.Mockito.verify;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XSSFWorkbook.class)
public class StreamingXlsOutputTest {
    @Test
    public void testExtendsAbstractStreamingClass() throws Exception {
        assertImplementsInterface(StreamingXlsOutput.class, StreamingOutput.class);
    }

    @Test
    public void testWriteWillExportXlsWorkbook() throws Exception {
        XSSFWorkbook mockWorkbook = PowerMockito.mock(XSSFWorkbook.class);
        OutputStream mockOutputStream = PowerMockito.mock(OutputStream.class);

        StreamingXlsOutput streamingXlsOutput = new StreamingXlsOutput(mockWorkbook);

        PowerMockito.verifyZeroInteractions(mockWorkbook);
        streamingXlsOutput.write(mockOutputStream);
        verify(mockWorkbook).write(mockOutputStream);

    }

}
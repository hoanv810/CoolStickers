package github.hoanv810.icon;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static final String PATH = "//public_html//stickers//collections//nervous_duck.zip";

    //    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void listAllFiles_isCorrect() throws Exception {
        ZipFile zipFile = new ZipFile(PATH);
        // Get the list of file headers from the zip file
        List fileHeaderList = zipFile.getFileHeaders();

        // Loop through the file headers
        for (int i = 0; i < fileHeaderList.size(); i++) {
            FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
            // FileHeader contains all the properties of the file
            System.out.println("****File Details for: " + fileHeader.getFileName() + "*****");
            System.out.println("Name: " + fileHeader.getFileName());
            System.out.println("Compressed Size: " + fileHeader.getCompressedSize());
            System.out.println("Uncompressed Size: " + fileHeader.getUncompressedSize());
            System.out.println("CRC: " + fileHeader.getCrc32());
            System.out.println("************************************************************");

            // Various other properties are available in FileHeader. Please have a look at FileHeader
            // class to see all the properties
        }
    }
}
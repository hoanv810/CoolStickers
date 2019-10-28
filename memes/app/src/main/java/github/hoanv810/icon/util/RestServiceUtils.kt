package github.hoanv810.icon.util

import java.io.*

/**
 * @author hoanv
 * @since 8/6/18
 */
object RestServiceUtils {

    @Throws(Exception::class)
    fun getStringFromFile(filePath: String): String {
        val f = File(filePath)
        require(f.exists()) { "File path not found." }

        val stream = FileInputStream(File(filePath))

        val ret = convertStreamToString(stream)
        stream.close()
        return ret
    }

    @Throws(Exception::class)
    private fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()

        reader.lineSequence().forEach {
            sb.append(it).append("\n")
        }

        reader.close()
        return sb.toString()
    }
}
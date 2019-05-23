import View.Application
import java.awt.Dimension
import javax.swing.JFrame

fun main() {

    val frame = JFrame("LL1")
    /*try {
        val img = ImageIcon(URL("https://images-eu.ssl-images-amazon.com/images/I/71uAfFTpYaL.png"))
        frame.iconImage = img.image
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    }
    */

    frame.isResizable = true
    frame.contentPane = Application().panel
    frame.minimumSize = Dimension(1370, 720)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.pack()
    frame.isVisible = true

}
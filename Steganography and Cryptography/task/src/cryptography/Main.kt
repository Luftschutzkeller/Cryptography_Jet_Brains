package cryptography

import java.io.File
import javax.imageio.ImageIO

fun main() {
    while(true) {
        println("Task (hide, show, exit):")
        readLine().let {
            when(it) {
                "exit" -> { println("Bye!"); return }
                "hide" -> { println("Hiding message in image."); hide() }
                "show" -> { println("Input image file:"); show() }
                else -> println("Wrong task: $it")
            }
        }
    }
}

fun show() = mutableListOf<Int>().run {
    ImageIO.read(File(readLine()!!)).run {
        for (y in 0 until height) { for (x in 0 until width) { add(getRGB(x, y) and 1) } }
        val s = java.math.BigInteger(joinToString("").substringBefore("000000000000000000000011"), 2)
        println("Message:\n${s.toByteArray().toString(Charsets.UTF_8)}")
    }
}

fun hide() {
    println("Input image file:")
    val inputFile =  File(readLine()!!)
    println("Output image file:")
    val outputFile = File(readLine()!!)
    println("Message to hide:")
    val msgToHide = readLine()!!.plus("\u0000\u0000\u0003")
    val msg = msgToHide.map { Integer.toBinaryString(it.code).padStart(8, '0') }.joinToString("")
    try {
        ImageIO.read(inputFile).run {
            if (msg.length > width * height) throw java.lang.Exception()
            println("Input Image: ${inputFile.absolutePath}")
            println("Output Image: ${outputFile.absolutePath}")
            msg.forEachIndexed { index, c ->
                setRGB(
                    index % width,
                    index / width,
                    getRGB(index % width, index / width) and 0xFFFFFE or c.toString().toInt()
                )
            }
            ImageIO.write(this, "PNG", outputFile)
        }
        println("Message saved in ${outputFile.absolutePath} image.")
    } catch (e: javax.imageio.IIOException) {
        println("Can't read input file!")
    } catch (e: java.lang.Exception) {
        println("The input image is not large enough to hold this message.")
    }
}
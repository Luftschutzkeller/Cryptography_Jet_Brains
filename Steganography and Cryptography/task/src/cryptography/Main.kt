package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    println("Task (hide, show, exit):")
    when (val action: String = readLine()!!) {
        "hide" -> hideImageDriver()
        "show" -> println("Obtaining message from image.")
        "exit" -> {
            println("Bye!")
            return
        }
        else -> {
            println("Wrong task: $action")
        }
    }
}

fun hideImageDriver() {
    println("Input image file:")

    try {
        val inputImageFileName = readLine()!!
        val bufferedImage = ImageIO.read(File(inputImageFileName))

        // Setting to 1 the least significant bit for each color (Red, Green, and Blue)
        val outputImage = hideImageHandler(bufferedImage)

        println("Output image file:")
        val outputImageFileName = readLine()!!

        println("Input Image: $inputImageFileName")
        println("Output Image: $outputImageFileName")

        ImageIO.write(outputImage, "png", File(outputImageFileName))
        println("Image $outputImageFileName is saved.")

    } catch (e: Exception){
        println("Can't read input file!")
    }
    // Repeat "cycle" until "exit" action
    main()
}

fun hideImageHandler(inputImage: BufferedImage): BufferedImage {

    for (i in 0 until inputImage.width) {
        for (j in 0 until inputImage.height) {
            val color = Color(inputImage.getRGB(i, j))

            val rgb = Color(
                setLeastSignificantBitToOne(color.red),
                setLeastSignificantBitToOne(color.green),
                setLeastSignificantBitToOne(color.blue)
            ).rgb

            inputImage.setRGB(i, j, rgb)
        }
    }
    return inputImage
}

fun setLeastSignificantBitToOne(input: Int): Int {
    return if (input % 2 == 0) input + 1 else input
}

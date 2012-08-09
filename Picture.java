package proj1;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
* A class that represents a picture. This class inherits from SimplePicture and
* allows the student to add functionality and picture effects.
*
* @author Barb Ericson (ericson@cc.gatech.edu) (Copyright Georgia Institute of
*         Technology 2004)
* @author Modified by Colleen Lewis (colleenl@berkeley.edu), Jonathan Kotker
*         (jo_ko_berkeley@berkeley.edu), Kaushik Iyer (kiyer@berkeley.edu),
*         George Wang (georgewang@berkeley.edu), and David Zeng
*         (davidzeng@berkeley.edu), for use in CS61BL, the data structures
*         course at University of California, Berkeley.
*/
public class Picture extends SimplePicture {

    // ///////////////////////// Static Variables //////////////////////////////

    // Different axes available to flip a picture.
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int FORWARD_DIAGONAL = 3;
    public static final int BACKWARD_DIAGONAL = 4;

    // Different Picture objects for the bitmaps used in ASCII art conversion.
    private static Picture BMP_AMPERSAND;
    private static Picture BMP_APOSTROPHE;
    private static Picture BMP_AT;
    private static Picture BMP_BAR;
    private static Picture BMP_COLON;
    private static Picture BMP_DOLLAR;
    private static Picture BMP_DOT;
    private static Picture BMP_EXCLAMATION;
    private static Picture BMP_GRAVE;
    private static Picture BMP_HASH;
    private static Picture BMP_PERCENT;
    private static Picture BMP_SEMICOLON;
    private static Picture BMP_SPACE;

    // ////////////////////////// Constructors /////////////////////////////////

    /**
     * A constructor that takes no arguments.
     */
    public Picture() {
        super();
    }

    /**
     * Creates a Picture from the file name provided.
     *
     * @param fileName
     *            The name of the file to create the picture from.
     */
    public Picture(String fileName) {
        // Let the parent class handle this fileName.
        super(fileName);
    }

    /**
     * Creates a Picture from the width and height provided.
     *
     * @param width
     *            the width of the desired picture.
     * @param height
     *            the height of the desired picture.
     */
    public Picture(int width, int height) {
        // Let the parent class handle this width and height.
        super(width, height);
    }

    /**
     * Creates a copy of the Picture provided.
     *
     * @param pictureToCopy
     *            Picture to be copied.
     */
    public Picture(Picture pictureToCopy) {
        // Let the parent class do the copying.
        super(pictureToCopy);
    }

    /**
     * Creates a copy of the SimplePicture provided.
     *
     * @param pictureToCopy
     *            SimplePicture to be copied.
     */
    public Picture(SimplePicture pictureToCopy) {
        // Let the parent class do the copying.
        super(pictureToCopy);
    }

    // ///////////////////////////// Methods ///////////////////////////////////

    /**
     * @return A string with information about the picture, such as filename,
     *         height, and width.
     */
    public String toString() {
        String output = "Picture, filename = " + this.getFileName() + ","
                + " height = " + this.getHeight() + ", width = "
                + this.getWidth();
        return output;
    }

    // ///////////////////// PROJECT 1 BEGINS HERE /////////////////////////////

    /*
     * Each of the methods below is constructive: in other words, each of the
     * methods below generates a new Picture, without permanently modifying the
     * original Picture.
     */

    // ////////////////////////////// Level 1 //////////////////////////////////

    /**
     * Converts the Picture into grayscale. Since any variation of gray is
     * obtained by setting the red, green, and blue components to the same
     * value, a Picture can be converted into its grayscale component by setting
     * the red, green, and blue components of each pixel in the new picture to
     * the same value: the average of the red, green, and blue components of the
     * same pixel in the original.
     *
     * @return A new Picture that is the grayscale version of this Picture.
     */
    public Picture grayscale() {
       
        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                newPicture.setPixelToGray(x, y);
            }
        }
        return newPicture;
    }

    /**
     * Helper method for grayscale() to set a pixel at (x, y) to be gray.
     *
     * @param x
     *            The x-coordinate of the pixel to be set to gray.
     * @param y
     *            The y-coordinate of the pixel to be set to gray.
     */
    private void setPixelToGray(int x, int y) {
        Pixel currentPixel = this.getPixel(x, y);
       
        int average = currentPixel.getAverage();
       
        currentPixel.setRed(average);
        currentPixel.setGreen(average);
        currentPixel.setBlue(average);
    }

    /**
     * Test method for setPixelToGray. This method is called by the JUnit file
     * through the public method Picture.helpersWork().
     */
    private static boolean setPixelToGrayWorks() {
       
        Picture bg = Picture.loadPicture("Creek.bmp");
        Pixel focalPixel = bg.getPixel(10, 10);
        bg.setPixelToGray(10, 10);
        int goalColor = (int) focalPixel.getAverage();
        int originalAlpha = focalPixel.getColor().getAlpha();
       
        boolean redCorrect = focalPixel.getRed() == goalColor;
        boolean greenCorrect = focalPixel.getGreen() == goalColor;
        boolean blueCorrect = focalPixel.getBlue() == goalColor;
        boolean alphaCorrect = focalPixel.getAlpha() == originalAlpha;
       
        return redCorrect && greenCorrect && blueCorrect && alphaCorrect;
    }

    /**
     * This method provide JUnit access to the testing methods written within
     * Picture.java
     */
    public static boolean helpersWork() {
            if (!Picture.setPixelToGrayWorks()) {
                return false;
            }
     
            if (!Picture.rotateNinetyworks()) {
                return false;
            }
     
            if (!Picture.chunktoAsciiWorks()) {
                return false;
            }
     
            if (!Picture.toAsciiWorks()) {
                return false;
            }
     
            if (!Picture.paintBucketsurroundingWorks()) {
                return false;
            }
            
            if (!Picture.blurHelperWorks() ) {
                return false;
            }
     
            return true;
     
        }

    /**
     * Converts the Picture into its photonegative version. The photonegative
     * version of an image is obtained by setting each of the red, green, and
     * blue components of every pixel to a value that is 255 minus their current
     * values.
     *
     * @return A new Picture that is the photonegative version of this Picture.
     */
    public Picture negate() {
       
        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                // Set Red, Blue, Green to 255 - color component
                // Invariants: new color components of each pixel should be 255-
                // color component
                Pixel currentPixel = newPicture.getPixel(x, y);
                currentPixel.setRed(255 - currentPixel.getRed());
                currentPixel.setGreen(255 - currentPixel.getGreen());
                currentPixel.setBlue(255 - currentPixel.getBlue());
            }
        }
        return newPicture;
    }

    /**
     * Creates an image that is lighter than the original image. The range of
     * each color component should be between 0 and 255 in the new image. The
     * alpha value should not be changed.
     *
     * @return A new Picture that has every color value of the Picture increased
     *         by the lightenAmount.
     */
    public Picture lighten(int lightenAmount) {

        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                Pixel currentPixel = newPicture.getPixel(x, y);
                currentPixel.setRed(lightenAmount + currentPixel.getRed());
                currentPixel.setGreen(lightenAmount + currentPixel.getGreen());
                currentPixel.setBlue(lightenAmount + currentPixel.getBlue());
            }
        }
        return newPicture;
    }

    /**
     * Creates an image that is darker than the original image.The range of each
     * color component should be between 0 and 255 in the new image. The alpha
     * value should not be changed.
     *
     * @return A new Picture that has every color value of the Picture decreased
     *         by the darkenenAmount.
     */
    public Picture darken(int darkenenAmount) {
       
        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                Pixel currentPixel = newPicture.getPixel(x, y);
                currentPixel.setRed(currentPixel.getRed() - darkenenAmount);
                currentPixel.setGreen(currentPixel.getGreen() - darkenenAmount);
                currentPixel.setBlue(currentPixel.getBlue() - darkenenAmount);
            }
        }
        return newPicture;
    }

    /**
     * Creates an image where the blue value has been increased by amount.The
     * range of each color component should be between 0 and 255 in the new
     * image. The alpha value should not be changed.
     *
     * @return A new Picture that has every blue value of the Picture increased
     *         by amount.
     */
    public Picture addBlue(int amount) {

        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                Pixel currentPixel = newPicture.getPixel(x, y);
                currentPixel.setBlue(currentPixel.getBlue() + amount);
            }
        }
        return newPicture;
    }

    /**
     * Creates an image where the red value has been increased by amount. The
     * range of each color component should be between 0 and 255 in the new
     * image. The alpha value should not be changed.
     *
     * @return A new Picture that has every red value of the Picture increased
     *         by amount.
     */
    public Picture addRed(int amount) {

        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                Pixel currentPixel = newPicture.getPixel(x, y);
                currentPixel.setRed(currentPixel.getRed() + amount);
            }
        }
        return newPicture;
    }

    /**
     * Creates an image where the green value has been increased by amount. The
     * range of each color component should be between 0 and 255 in the new
     * image. The alpha value should not be changed.
     *
     * @return A new Picture that has every green value of the Picture increased
     *         by amount.
     */
    public Picture addGreen(int amount) {
       
        Picture newPicture = new Picture(this);

        int pictureHeight = this.getHeight();
        int pictureWidth = this.getWidth();

        for (int x = 0; x < pictureWidth; x++) {
           
            for (int y = 0; y < pictureHeight; y++) {
                Pixel currentPixel = newPicture.getPixel(x, y);
                currentPixel.setGreen(currentPixel.getGreen() + amount);
            }
        }
        return newPicture;

    }

    /**
     * @param x
     *            x-coordinate of the pixel currently selected.
     * @param y
     *            y-coordinate of the pixel currently selected.
     * @param background
     *            Picture to use as the background.
     * @param threshold
     *            Threshold within which to replace pixels.
     *
     * @return A new Picture where all the pixels in the original Picture, which
     *         differ from the currently selected pixel within the provided
     *         threshold (in terms of color distance), are replaced with the
     *         corresponding pixels in the background picture provided.
     *
     *         If the two Pictures are of different dimensions, the new Picture
     *         will have length equal to the smallest of the two Pictures being
     *         combined, and height equal to the smallest of the two Pictures
     *         being combined. In this case, the Pictures are combined as if
     *         they were aligned at the top left corner (0, 0).
     */
    public Picture chromaKey(int xRef, int yRef, Picture background,
            int threshold) {

        int newHeight = Math.min(this.getHeight(), background.getHeight());
        int newWidth = Math.min(this.getWidth(), background.getWidth());
       
        Picture newPicture = new Picture(newWidth, newHeight);
        Pixel compareTo = this.getPixel(xRef, yRef);
       
        for (int y = 0; y < newHeight; y++) {
           
            for (int x = 0; x < newWidth; x++) {
               
                Pixel toChange = newPicture.getPixel(x, y);
                Pixel reference = this.getPixel(x, y);
               
                if (reference.colorDistance(compareTo.getColor()) < threshold) {
                    toChange.setColor(background.getPixel(x, y).getColor());
                   
                } else {
                    toChange.setColor(reference.getColor());
                }
            }
        }
        return newPicture;
    }

    // ////////////////////////////// Level 2 //////////////////////////////////

    /**
     * Rotates this Picture by the integer multiple of 90 degrees provided. If
     * the number of rotations provided is positive, then the picture is rotated
     * clockwise; else, the picture is rotated counterclockwise. Multiples of
     * four rotations (including zero) correspond to no rotation at all.
     *
     * @param rotations
     *            The number of 90-degree rotations to rotate this image by.
     *
     * @return A new Picture that is the rotated version of this Picture.
     */

    public Picture rotate(int rotations) {
       
        while (rotations < 0) {
            rotations += 4;
        }
       
        // Return copy of original picture if rotations is a multiple of four.
        if (rotations % 4 == 0) {
            return new Picture(this);
           
        } else {
            // Reduce rotations by multiples of four until rotations is between
            // 0-3.
            rotations = rotations % 4;
        }
       
        Picture newPicture = new Picture(this);

        for (; rotations > 0; rotations--) {
            // Rotate by 90 degrees clockwise for every rotation.
            newPicture = rotateNinety(newPicture);
        }
        return newPicture;
    }

    /**
     * Helper method for rotate() to rotate a picture 90 degrees.
     *
     * @param pic
     *            The picture to be rotated 90 degrees.
     *
     * @return A new Picture that is the parameter Picture rotated by 90-degree
     *         clockwise.
     */
    private Picture rotateNinety(Picture pic) {
       
        int oldWidth = pic.getWidth();
        int oldHeight = pic.getHeight();
        int newWidth = pic.getHeight();
        int newHeight = pic.getWidth();
       
        Picture newPicture = new Picture(newWidth, newHeight);
       
        for (int x = 0; x < oldWidth; x++) {
           
            for (int y = 0; y < oldHeight; y++) {
               
                Pixel reference = pic.getPixel(x, y);
                Pixel toChange = newPicture.getPixel(oldHeight - 1 - y, x);
               
                toChange.setColor(reference.getColor());
            }
        }
        return newPicture;
    }

    public static boolean rotateNinetyworks() {
        Picture pic = Picture.loadPicture("CalOriginal.bmp");
        Picture picCopy = new Picture(pic);
        Picture picCorrect = Picture.loadPicture("CalRotate1.bmp");
        Picture picTest = picCopy.rotateNinety(picCopy);
        return pic.equals(picCopy) && picCorrect.equals(picTest);
    }

    /**
     * Flips this Picture about the given axis. The axis can be one of four
     * static integer constants:
     *
     * (a) Picture.HORIZONTAL: The picture should be flipped about a horizontal
     * axis passing through the center of the picture. (b) Picture.VERTICAL: The
     * picture should be flipped about a vertical axis passing through the
     * center of the picture. (c) Picture.FORWARD_DIAGONAL: The picture should
     * be flipped about an axis that passes through the north-east and
     * south-west corners of the picture. (d) Picture.BACKWARD_DIAGONAL: The
     * picture should be flipped about an axis that passes through the
     * north-west and south-east corners of the picture.
     *
     * @param axis
     *            Axis about which to flip the Picture provided.
     *
     * @return A new Picture flipped about the axis provided.
     */
    public Picture flip(int axis) {
        /*
         * Flipping forward on the diagonal axis can be broken down to
         * rotating Picture 90 degrees clockwise then flipping it about the horizontal axis.
         * Flipping backwards on the diagonal axis can be broken down to
         * rotating Picture 90 degrees clockwise then flipping it about the vertical axis.
         */
        Picture newPicture;
       
        if (axis == 3) {
            newPicture = this.rotate(1);
            return newPicture.flip(1);
           
        } else if (axis == 4) {
            newPicture = this.rotate(1);
            return newPicture.flip(2);
        } else {
            newPicture = new Picture(this.getWidth(), this.getHeight());
        }
       
        for (int y = 0; y < this.getHeight(); y++) {
           
            for (int x = 0; x < this.getWidth(); x++) {
               
                if (axis == 1) {
                    // Flip along horizontal axis
                    Pixel toFlip = this.getPixel(x, y);
                    Pixel flippedPixel = newPicture.getPixel(x,
                            this.getHeight() - 1 - y);
                    flippedPixel.setColor(toFlip.getColor());
                   
                } else {
                    // Flip along vertical axis here
                    Pixel toFlip = this.getPixel(x, y);
                    Pixel flippedPixel = newPicture.getPixel(this.getWidth()
                            - 1 - x, y);
                    flippedPixel.setColor(toFlip.getColor());
                }
            }
        }
        return newPicture;
    }

    /**
     * @param threshold
     *            Threshold to use to determine the presence of edges.
     *
     * @return A new Picture that contains only the edges of this Picture. For
     *         each pixel, we separately consider the color distance between
     *         that pixel and the one pixel to its left, and also the color
     *         distance between that pixel and the one pixel to the north, where
     *         applicable. As an example, we would compare the pixel at (3, 4)
     *         with the pixels at (3, 3) and the pixels at (2, 4). Also, since
     *         the pixel at (0, 4) only has a pixel to its north, we would only
     *         compare it to that pixel. If either of the color distances is
     *         larger than the provided color threshold, it is set to black
     *         (with an alpha of 255); otherwise, the pixel is set to white
     *         (with an alpha of 255). The pixel at (0, 0) will always be set to
     *         white.
     */
    public Picture showEdges(int threshold) {
       
        Picture newPicture = new Picture(this);
       
        int width = newPicture.getWidth();
        int height = newPicture.getHeight();
       
        double leftDist;
        double topDist;
       
        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {
                Pixel reference = this.getPixel(x, y);
                Pixel toChange = newPicture.getPixel(x, y);

                // Determining color distance between reference Pixel and its
                // left-neighboring Pixel
                if (x == 0) {
                    leftDist = 0;
                } else {
                    leftDist = reference.colorDistance(this.getPixel(x - 1, y)
                            .getColor());
                }

                // Determining color distance between reference Pixel and its
                // top-neighboring Pixel
                if (y == 0) {
                    topDist = 0;
                } else {
                    topDist = reference.colorDistance(this.getPixel(x, y - 1)
                            .getColor());
                }

                if ((int) leftDist > threshold || (int) topDist > threshold) {
                    toChange.setColor(Color.BLACK);

                } else {
                    toChange.setColor(Color.WHITE);
                }
                toChange.setAlpha(255);
            }
        }
        // Set Color of Pixel at origin to white
        newPicture.getPixel(0, 0).setColor(Color.WHITE);

        return newPicture;
    }

    // ////////////////////////////// Level 3 //////////////////////////////////

    /**
     * @return A new Picture that is the ASCII art version of this Picture. To
     *         implement this, the Picture is first converted into its grayscale
     *         equivalent. Then, starting from the top left, the average color
     *         of every chunk of 10 pixels wide by 20 pixels tall is computed.
     *         Based on the average value obtained, this chunk will be replaced
     *         by the corresponding ASCII character specified by the table
     *         below.
     *
     *         The ASCII characters to be used are available as Picture objects,
     *         also of size 10 pixels by 20 pixels. The following characters
     *         should be used, depending on the average value obtained:
     *
     *         0 to 18: # (Picture.BMP_POUND) 19 to 37: @ (Picture.BMP_AT) 38 to
     *         56: & (Picture.BMP_AMPERSAND) 57 to 75: $ (Picture.BMP_DOLLAR) 76
     *         to 94: % (Picture.BMP_PERCENT) 95 to 113: | (Picture.BMP_BAR) 114
     *         to 132: ! (Picture.BMP_EXCLAMATION) 133 to 151: ;
     *         (Picture.BMP_SEMICOLON) 152 to 170: : (Picture.BMP_COLON) 171 to
     *         189: ' (Picture.BMP_APOSTROPHE) 190 to 208: ` (Picture.BMP_GRAVE)
     *         209 to 227: . (Picture.BMP_DOT) 228 to 255: (Picture.BMP_SPACE)
     *
     *         We provide a getAsciiPic method to obtain the Picture object
     *         corresponding to a character, given any of the static Strings
     *         mentioned above.
     *
     *         Note that the resultant Picture should be the exact same size as
     *         the original Picture; this might involve characters being
     *         partially copied to the final Picture.
     */
    public Picture convertToAscii() {
       
        Picture newPicture = new Picture(this.grayscale());
       
        int width = newPicture.getWidth();
        int height = newPicture.getHeight();
       
        Picture ascii;
        for (int x = 0; x < width; x += 10) {
            for (int y = 0; y < height; y += 20) {

                ascii = chunktoAscii(newPicture, x, y);
                toAscii(ascii, newPicture, x, y);
            }
        }
        return newPicture;
    }

    /**
     * Helper method for convertToAscii() to match 10 by 20 pixel chunk to ASCII
     * character.
     *
     * @param newPicture
     *            The picture to which the 10 by 20 pixel chunk belongs.
     * @param xRef
     *            The x-position at which the chunk begins.
     * @param yRef
     *            The y-position at which the chunk begins.
     *
     * @return A new Picture representing an ASCII character which corresponds
     *         to the chunk.
     */

    private Picture chunktoAscii(Picture newPicture, int xRef, int yRef) {
        int average = 0;
        int Count = 0;
        int xPos = xRef;
        int yPos = yRef;

        for (int x = 0; xPos < newPicture.getWidth() && x < 10; x++, xPos++) {
            yPos = yRef;
           
            for (int y = 0; yPos < newPicture.getHeight() && y < 20; y++, Count++, yPos++) {
                average += newPicture.getPixel(xPos, yPos).getAverage();

            }
        }
        average = average / (Count);
        return getAsciiPic(average);
    }

    public static boolean chunktoAsciiWorks() {
       
        Picture pic = Picture.loadPicture("at.bmp");
        Picture picTest = pic.chunktoAscii(pic, 0, 0);
       
        if (pic.convertToAscii().equals(picTest)) {
            return true;
        }
        return false;
    }

    /**
     * Helper method for convertToAscii() to copy an ASCII character to a chunk
     * in newPicture.
     *
     * @param ascii
     *            A 10 by 20 Picture representing an ASCII character.
     * @param newPicture
     *            The picture to which the 10 by 20 pixel chunk belongs.
     * @param xRef
     *            The x-position at which the chunk begins.
     * @param yRef
     *            The y-position at which the chunk begins.
     *
     * @return A new Picture representing an ASCII character which corresponds
     *         to the chunk.
     */
    private void toAscii(Picture ascii, Picture newPicture, int xRef, int yRef) {
        int xPos = xRef;
        int yPos = yRef;
       
        for (int x = 0; xPos < newPicture.getWidth() && x < 10; x++, xPos++) {
            yPos = yRef;
           
            for (int y = 0; yPos < newPicture.getHeight() && y < 20; y++, yPos++) {
                Pixel reference = ascii.getPixel(x, y);

                Pixel toChange = newPicture.getPixel(xPos, yPos);
                toChange.setColor(reference.getColor());
            }
        }

    }

    public static boolean toAsciiWorks() {
        Picture pic = Picture.loadPicture("at.bmp");
        Picture referencePic = Picture.loadPicture("ampersand.bmp");

        pic.toAscii(referencePic, pic, 0, 0);
        if (pic.equals(referencePic)) {
            return true;
        }
        return false;
    }

    /**
     * Blurs this Picture. To achieve this, the algorithm takes a pixel, and
     * sets it to the average value of all the pixels in a square of side (2 *
     * blurThreshold) + 1, centered at that pixel. For example, if blurThreshold
     * is 2, and the current pixel is at location (8, 10), then we will consider
     * the pixels in a 5 by 5 square that has corners at pixels (6, 8), (10, 8),
     * (6, 12), and (10, 12). If there are not enough pixels available -- if the
     * pixel is at the edge, for example, or if the threshold is larger than the
     * image -- then the missing pixels are ignored, and the average is taken
     * only of the pixels available.
     *
     * The red, blue, green and alpha values should each be averaged separately.
     *
     * @param blurThreshold
     *            Size of the blurring square around the pixel.
     *
     * @return A new Picture that is the blurred version of this Picture, using
     *         a blurring square of size (2 * threshold) + 1.
     */
   public Picture blur(int blurThreshold) {
	   
       Picture newPic = new Picture(this);
       
       for (int y = 0; y < newPic.getHeight(); y++) {
           for (int x = 0; x < newPic.getWidth(); x++) {
               newPic = blurHelper(x, y, blurThreshold, newPic, this);
           }
       }
       
       return newPic;
    }

    /**
    * Helper method for blur. Blurs one square of dimensions defined by the
    * blurThreshold in blur().
    *
    * @param x
    *         x-coordinate of center Pixel to be blurred
    * @param y
    *         y-coordinate of center Pixel to be blurred
    * @param bounds
    *         length of a side square to collect RBG and Alpha from
    * @param pic
    *         Picture being blurred by calling the helper function
    * @param original
    *         The original un-blurred picture used to get average RGB values
    *
    * @return
    *         The picture with one blurred pixel
    */
    private Picture blurHelper (int x, int y, int bounds, Picture pic, Picture original) {
    	
       int xBoundLow = x - bounds;
       int xBoundHigh = x + bounds + 1;
       int yBoundLow = y - bounds;
       int yBoundHigh = y + bounds + 1;
       int avgRed = 0;
       int avgGreen = 0;
       int avgBlue = 0;
       int avgAlpha = 0;
       int counter = 0;
       
       // If bounds are larger than Picture dimensions, reset bounds to Picture boundaries
       if (xBoundLow < 0) {    
           xBoundLow = 0;
       }
       if (xBoundHigh > pic.getWidth()) {
           xBoundHigh = pic.getWidth();
       }
       if (yBoundLow < 0) {
           yBoundLow = 0;
       }
       if (yBoundHigh > pic.getHeight()) {
           yBoundHigh = pic.getHeight();
       }
       
       // Collect RGB and alpha data from each Pixel within bounds
       for (int i = xBoundLow; i < xBoundHigh; i++) {
           for (int j = yBoundLow; j < yBoundHigh; j++) {
               Pixel pixelToUse = original.getPixel(i, j);
               counter++;
               avgRed += pixelToUse.getRed();
               avgGreen += pixelToUse.getGreen();
               avgBlue += pixelToUse.getBlue();
               avgAlpha += pixelToUse.getAlpha();
           }
       }
       // Update average color and alpha values by dividing by counter

       avgRed = (avgRed / counter);
       avgBlue = (avgBlue / counter);
       avgGreen = (avgGreen / counter);
       avgAlpha = (avgAlpha / counter);
       
       // Blur the selected pixel
       Pixel changePixel = pic.getPixel(x, y);    
       changePixel.setRed(avgRed);
       changePixel.setGreen(avgGreen);
       changePixel.setBlue(avgBlue);
       changePixel.setAlpha(avgAlpha);
       
       
       return pic;
    }

    public static boolean blurHelperWorks() {
    	
       // Picture with size of 639 x 479 pixels
       Picture picTest = Picture.loadPicture("Creek.bmp");
       Picture alreadyBlurred = Picture.loadPicture("Creek_blur.bmp");
       
       
       // Testing edge case at (0, 0), making sure correct pixels are selected
       Picture test1 = new Picture(picTest);
       test1.blurHelper(0, 0, 3, test1, picTest);
       
       if (!(test1.getPixel(0, 0).equals(alreadyBlurred.getPixel(0, 0)))) {
    	   return false;
       }
       

       // Testing edge case at (639, 0), making sure correct pixels are selected
       Picture test2 = new Picture(picTest);
       test2.blurHelper(639, 0, 3, test2, picTest);
       
       if (!(test2.getPixel(639, 0).equals(alreadyBlurred.getPixel(639, 0)))) {
    	   return false;
       }

       // Testing edge case at (0, 479), making sure correct pixels are selected
       Picture test3 = new Picture(picTest);
       test3.blurHelper(0, 479, 3, test3, picTest);
       
       if (!(test3.getPixel(0, 479).equals(alreadyBlurred.getPixel(0, 479)))) {
    	   return false;
       }

       // Testing edge case at (639, 479), making sure correct pixels are selected
       Picture test4 = new Picture(picTest);
       test4.blurHelper(639, 479, 3, test4, picTest);
       
       if (!(test4.getPixel(639, 479).equals(alreadyBlurred.getPixel(639, 479)))) {
    	   return false;
       }
        

       // Testing middle case at (100, 100), making sure correct pixels are selected
       Picture test5 = new Picture(picTest);
       test5.blurHelper(100, 100, 3, test5, picTest);
       
       if (!(test5.getPixel(100, 100).equals(alreadyBlurred.getPixel(100, 100)))) {
    	   return false;
       }
       
       
       return true;
    }

    /**
     * @param x
     *            x-coordinate of the pixel currently selected.
     * @param y
     *            y-coordinate of the pixel currently selected.
     * @param threshold
     *            Threshold within which to delete pixels.
     * @param newColor
     *            New color to color pixels.
     *
     * @return A new Picture where all the pixels connected to the currently
     *         selected pixel, and which differ from the selected pixel within
     *         the provided threshold (in terms of color distance), are colored
     *         with the new color provided.
     */
    public Picture paintBucket(int x, int y, int threshold, Color newColor) {
        Picture newPicture = new Picture(this);

        Pixel reference = this.getPixel(x, y); // Keep to use as reference
        Pixel refCopy = newPicture.getPixel(x, y);

        int x1, y1; // Used in while loop as references to coordinates of Pixel
                    // being checked.
        newPicture.toBeOperated = new ArrayList<Pixel>(newPicture.getWidth()
                * newPicture.getHeight());
        newPicture.done = new boolean[newPicture.getWidth()][newPicture
                .getHeight()];

        newPicture.toBeOperated.add(refCopy);
        while (!newPicture.toBeOperated.isEmpty()) {
           
            Pixel checked = newPicture.toBeOperated.remove(0);
            checked.setColor(newColor);
           
            x1 = checked.getX();
            y1 = checked.getY();
           
            // Add Pixel checked to 2D list of Pixels checked.
            newPicture.done[x1][y1] = true;
           
            // Locate Pixels touching the Pixel checked.
            ArrayList<Pixel> surrounding = paintBucketsurrounding(newPicture,
                    x1, y1);
            while (!surrounding.isEmpty()) {
                Pixel pixel = surrounding.remove(0);
                // Determine if neighboring Pixel should be operated on.
                if (reference.colorDistance(pixel.getColor()) <= threshold) {
                    newPicture.toBeOperated.add(pixel);
                }
            }
        }
        return newPicture;
    }

    /**
     * ArrayList containing pixels which require color changing. Used by
     * paintBucket() as a queue of pixels to operate on.
     */
    private ArrayList<Pixel> toBeOperated;

    /**
     * boolean[ ][ ], a 2D array keeping track of which pixels have been added
     * to toBeOperated. Used by paintBucket().
     */
    private boolean[][] done;

    /**
     * Helper method for paintBucket() to find all valid x-y combinations
     * representing pixels that directly touch the origin pixel located at
     * parameter x and y.
     *
     * @param x
     *            The x-coordinate of the origin pixel.
     * @param y
     *            The y-coordinate of the origin pixel.
     *
     * @return surrounding, an ArrayList containing pixels that directly touch
     *         the origin pixel.
     */
    private ArrayList<Pixel> paintBucketsurrounding(Picture newPicture, int x,
            int y) {
        int xmin, xmax, ymin, ymax;

        // Determine if there are pixels to the top, bottom, left, and right of
        // (x, y)
        if (x == 0) {
            xmin = 0;
        } else {
            xmin = -1;
        }

        if (x == newPicture.getWidth() - 1) {
            xmax = 0;
        } else {
            xmax = 1;
        }

        if (y == 0) {
            ymin = 0;
        } else {
            ymin = -1;
        }

        if (y == newPicture.getHeight() - 1) {
            ymax = 0;
        } else {
            ymax = 1;
        }

        ArrayList<Pixel> surrounding = new ArrayList<Pixel>(7);
        // Add pixels not already checked to returned ArrayList surrounding.
        for (int x1 = (xmin + x); x1 <= (xmax + x); x1++) {
            for (int y1 = (ymin + y); y1 <= (ymax + y); y1++) {

                if (newPicture.done[x1][y1] != true) {
                    newPicture.done[x1][y1] = true;
                   
                    Pixel pixel = newPicture.getPixel(x1, y1);
                    surrounding.add(pixel);

                }
            }
        }
        return surrounding;
    }

    public static boolean paintBucketsurroundingWorks() {
        Picture pic = Picture.loadPicture("at.bmp");
        pic.done = new boolean[pic.getWidth()][pic.getHeight()];

        ArrayList<Pixel> surrounding = pic.paintBucketsurrounding(pic, 0, 0);

       
        if (surrounding.size() == 4 && pic.done[0][0] == true
                && pic.done[0][1] == true && pic.done[1][0] == true
                && pic.done[1][1] == true) {
            return true;
        }
        return false;
    }

    // /////////////////////// PROJECT 1 ENDS HERE /////////////////////////////

    public boolean equals(Object obj) {
        if (!(obj instanceof Picture)) {
            return false;
        }

        Picture p = (Picture) obj;
        // Check that the two pictures have the same dimensions.
        if ((p.getWidth() != this.getWidth())
                || (p.getHeight() != this.getHeight())) {
            return false;
        }

        // Check each pixel.
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                if (!this.getPixel(x, y).equals(p.getPixel(x, y))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Helper method for loading a picture in the current directory.
     */
    protected static Picture loadPicture(String pictureName) {
        URL url = Picture.class.getResource(pictureName);
        return new Picture(url.getFile().replaceAll("%20", " "));
    }

    /**
     * Helper method for loading the pictures corresponding to each character
     * for the ASCII art conversion.
     */
    private static Picture getAsciiPic(int grayValue) {
        int asciiIndex = (int) grayValue / 19;

        if (BMP_AMPERSAND == null) {
            BMP_AMPERSAND = Picture.loadPicture("ampersand.bmp");
            BMP_APOSTROPHE = Picture.loadPicture("apostrophe.bmp");
            BMP_AT = Picture.loadPicture("at.bmp");
            BMP_BAR = Picture.loadPicture("bar.bmp");
            BMP_COLON = Picture.loadPicture("colon.bmp");
            BMP_DOLLAR = Picture.loadPicture("dollar.bmp");
            BMP_DOT = Picture.loadPicture("dot.bmp");
            BMP_EXCLAMATION = Picture.loadPicture("exclamation.bmp");
            BMP_GRAVE = Picture.loadPicture("grave.bmp");
            BMP_HASH = Picture.loadPicture("hash.bmp");
            BMP_PERCENT = Picture.loadPicture("percent.bmp");
            BMP_SEMICOLON = Picture.loadPicture("semicolon.bmp");
            BMP_SPACE = Picture.loadPicture("space.bmp");
        }

        switch (asciiIndex) {
        case 0:
            return Picture.BMP_HASH;
        case 1:
            return Picture.BMP_AT;
        case 2:
            return Picture.BMP_AMPERSAND;
        case 3:
            return Picture.BMP_DOLLAR;
        case 4:
            return Picture.BMP_PERCENT;
        case 5:
            return Picture.BMP_BAR;
        case 6:
            return Picture.BMP_EXCLAMATION;
        case 7:
            return Picture.BMP_SEMICOLON;
        case 8:
            return Picture.BMP_COLON;
        case 9:
            return Picture.BMP_APOSTROPHE;
        case 10:
            return Picture.BMP_GRAVE;
        case 11:
            return Picture.BMP_DOT;
        default:
            return Picture.BMP_SPACE;
        }
    }

    public static void main(String[] args) {
        Picture initialPicture = new Picture(
                FileChooser.pickAFile(FileChooser.OPEN));
        initialPicture.explore();
    }

} // End of Picture class

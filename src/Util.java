//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.image.BufferedImage;

public class Util {
    public Util() {
    }

    static final double[] affine(double[][] a, double[] b) {
        double[] ret = new double[b.length];
        int index = 0;
        for (int i = 0 ; i < a.length ; i ++){
            ret[index] = 0;
            for (int j = 0 ; j < a[0].length ; j ++){
                ret[index] += a[i][j] * b[j];
            }
            index += 1;
        }
        return (double[])ret;
    }

    static final int bilinear(int leftTop, int rightTop, int leftBottom, int rightBottom, double alpha, double beta) {
        double A = Util.linear(leftTop, rightTop, alpha);
        double B = Util.linear(leftBottom, rightBottom, alpha);
        double C = Util.linear(A, B, beta);
        return (int) C;
    }

    static final double linear(double v1, double v2, double weight) {
        return v1 + weight * (v2 - v1);
    }

    static final int scaleColor(BufferedImage img, double[] target){
        // x , y are Int
        if (Util.isInt(target[0]) && Util.isInt(target[1])){
            return img.getRGB(checkEdge((int) target[0], img.getWidth()), checkEdge((int) target[1], img.getHeight()));
        }
        else if(Util.isInt(target[0]) || Util.isInt(target[1])){
            // if x is int, and y is double
            if (Util.isInt(target[0])){
                int v1 = img.getRGB(checkEdge((int) target[0], img.getWidth()), checkEdge((int) target[1], img.getHeight()));
                int v2 = img.getRGB(checkEdge((int) target[0], img.getWidth()), checkEdge(((int) target[1]) + 1, img.getHeight()));
                double weight = checkEdge(((int) target[1]) + 1, img.getHeight()) - target[1];
                return (int) Util.linear(v1, v2, weight);
            }
            else{
                int v1 = img.getRGB(checkEdge((int) target[0], img.getWidth()), checkEdge((int) target[1], img.getHeight()));
                int v2 = img.getRGB(checkEdge(((int) target[0]) + 1, img.getWidth()), checkEdge((int) target[1], img.getHeight()));
                double weight = (checkEdge(((int) target[0]) + 1, img.getWidth()) - target[0]);
                return (int) Util.linear(v1, v2, weight);
            }
        }
        else{
            int x = checkEdge((int) target[0], img.getWidth());
            int y = checkEdge((int) target[1], img.getHeight());


            int leftTop = img.getRGB(checkEdge(x, img.getWidth()), checkEdge(y, img.getHeight()));
            int rightTop = img.getRGB(checkEdge(x + 1, img.getWidth()), checkEdge(y, img.getHeight()));
            int leftBottom = img.getRGB(checkEdge(x, img.getWidth()), checkEdge(y + 1, img.getHeight()));
            int rightBottom = img.getRGB(checkEdge(x + 1, img.getWidth()), checkEdge(y + 1, img.getHeight()));

            double alpha = (x + 1) - target[0];
            double beta = (y + 1) - target[1];
            return bilinear(leftTop, rightTop, leftBottom, rightBottom, alpha, beta);
        }
    }

    static final boolean isInt(double value){
        if (value == 0)
            return true;
        int intValue = (int) value;

        if(value % intValue == 0)
            return true;
        else
            return false;
    }

    final static double distance(double[] p1, double[] p2){
        return Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }

    final static int checkPixelBounds(int value){
        if (value > 255)
            return 255;
        else if (value < 0)
            return 0;
        else
            return value;
    }

    //get red channel from colorspace (4 bytes)
    final static int getR(int rgb){
        return checkPixelBounds((int) ((rgb & 0x00ff0000) >>> 16));
    }

    //get green channel from colorspace (4 bytes)
    final static int getG(int rgb){
        return checkPixelBounds((int) ((rgb & 0x0000ff00) >>> 8));
    }

    //get blue channel from colorspace (4 bytes)
    final static int getB(int rgb){

        return checkPixelBounds((int) (rgb & 0x000000ff));
    }

    //make ARGB color format from R, G, and B channels
    final static int makeColor(int r, int g, int b){
        return (((int) (r & 0x000000ff) << 16) + ((int) (g & 0x000000ff) << 8) + ((int) (b & 0x000000ff)) + ((int) (0xff000000)));
    }

    final static int checkEdge(int v, int limit){
        if (v >= limit)
            return limit - 1;
        else
            return v;
    }
}

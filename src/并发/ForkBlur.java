package 并发;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.imageio.ImageIO;
/* fork/join框架是ExecutorService接口的一个实现，可以帮助开发人员充分利用多核处理器的优势，编写出并行执行的程序，提高应用程序的性能；
 * 设计的目的是为了处理那些可以被递归拆分的任务。
fork/join框架与其它ExecutorService的实现类相似，会给线程池中的线程分发任务，不同之处在于它使用了工作窃取算法，所谓工作窃取，指的是对那些处理完自身任务的线程，会从其它线程窃取任务执行。
fork/join框架的核心是ForkJoinPool类，该类继承了AbstractExecutorService类。
ForkJoinPool实现了工作窃取算法并且能够执行 ForkJoinTask任务。

*该例子仅仅是阐述fork/join框架的使用，并不推荐使用该方法做图像模糊，图像边缘处理也没做判断
*/
public class ForkBlur extends RecursiveAction {
    private static final long serialVersionUID = -8032915917030559798L;
    private int[] mSource;
    private int mStart;
    private int mLength;
    private int[] mDestination;
    private int mBlurWidth = 15; // Processing window size, should be odd.
 
    public ForkBlur(int[] src, int start, int length, int[] dst) {
        mSource = src;
        mStart = start;
        mLength = length;
        mDestination = dst;
    }
 
    // Average pixels from source, write results into destination.
    protected void computeDirectly() {
        int sidePixels = (mBlurWidth - 1) / 2;
        for (int index = mStart; index < mStart + mLength; index++) {
            // Calculate average.
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), mSource.length - 1);
                int pixel = mSource[mindex];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / mBlurWidth;
                gt += (float) ((pixel & 0x0000ff00) >> 8) / mBlurWidth;
                bt += (float) ((pixel & 0x000000ff) >> 0) / mBlurWidth;
            }
 
            // Re-assemble destination pixel.
            int dpixel = (0xff000000)
                    | (((int) rt) << 16)
                    | (((int) gt) << 8)
                    | (((int) bt) << 0);
            mDestination[index] = dpixel;
        }
    }
    
    protected static int sThreshold = 10000;
    /*   compute()的实现方法，该方法分成两部分：
     * 直接执行模糊操作和任务的划分；
                一个数组长度阈值sThreshold可以帮助我们决定任务是直接执行还是进行划分；*/
    @Override
    protected void compute() {
        if (mLength < sThreshold) {
            computeDirectly();
            return;
        }
        int split = mLength / 2;
        invokeAll(new ForkBlur(mSource, mStart, split, mDestination),new ForkBlur(mSource, mStart + split, mLength - split, mDestination));
    }
    
    
    public static BufferedImage blur(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        //假定我们有一个图像模糊的任务需要完成，原始图像数据可以用一个整型数组表示，每一个整型元素包含了一个像素点的颜色值（RBG，存放在整型元素的不同位中）
        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];
 
        System.out.println("Array size is " + src.length);
        System.out.println("Threshold is " + sThreshold);
 
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(Integer.toString(processors) + " processor"
                + (processors != 1 ? "s are " : " is ")
                + "available");
        //1.创建图像模糊任务
        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
        //2.创建ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();
 
        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();
 
        System.out.println("Image blur took " + (endTime - startTime) + " milliseconds.");
 
        BufferedImage dstImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);
 
        return dstImage;
    }
 
    // Plumbing follows.
    public static void main(String[] args) throws Exception {
        String srcName = "E:\\test.jpg";
        File srcFile = new File(srcName);
        BufferedImage image = ImageIO.read(srcFile);
         
        System.out.println("Source image: " + srcName);
         
        BufferedImage blurredImage = blur(image);
         
        String dstName = "E:\\test_out.jpg";
        File dstFile = new File(dstName);
        ImageIO.write(blurredImage, "jpg", dstFile);
         
        System.out.println("Output image: " + dstName);
         
    }
 
    
}

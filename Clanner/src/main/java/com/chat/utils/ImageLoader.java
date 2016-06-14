package com.chat.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Clanner on 2016/6/4.
 * 图片加载类
 */
public class ImageLoader {

    private static final int DEAFULT_COUNT = 1;
    /**
     * 单例模式的好处
     * <p/>
     * 1：每次从getInstance（）都能返回一个且唯一的对象
     * 2:提高访问性能
     * 3:懒加载（lazy load），在需要的时候才构造
     */

    //单例对象实例
    private static ImageLoader mInstance;
    //图片缓存的核心对象
    private LruCache<String, Bitmap> mLruCache;
    //线程池
    private ExecutorService mThreadPool;
    //队列的调度方式
    private Type mType = Type.LIFO;
    //任务队列
    private LinkedList<Runnable> mTaskQueue;
    //后台轮询线程
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    //UI线程中的Handler
    private Handler mUIHandler;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);

    private Semaphore mSemaphoreThreadPool;

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(DEAFULT_COUNT, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    public static ImageLoader getInstance(int threadCount, Type type) {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }

//    /**
//     * 通过反射获取imageview的某个属性值
//     *
//     * @param object
//     * @param fieldName
//     * @return
//     */
//    private static int getImageViewFieldValue(Object object, String fieldName) {
//        int value = 0;
//
//        try {
//            Field field = ImageView.class.getDeclaredField(fieldName);
//            field.setAccessible(true);
//
//            int fieldValue = field.getInt(object);
//            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
//                value = fieldValue;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return value;
//    }

    /**
     * 根据path为imageView设置图片
     *
     * @param path
     * @param imageView
     */
    public void loadaImage(final String path, final ImageView imageView) {
        imageView.setTag(path);

        if (mUIHandler == null) {
            mUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //获取得到图片，为mageviewi回调设置图片
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageview = holder.imageView;
                    Bitmap bm = holder.bitmap;
                    String path = holder.path;
                    //将path与getTag存储路径进行比较
                    if (imageview.getTag().toString().equals(path)) {
                        imageview.setImageBitmap(bm);
                    }
                }
            };
        }

        //根据path在缓存中获取Bitmap
        Bitmap bm = getBitmapFromLruCache(path);

        if (bm != null) {
            refreashBitmap(path, imageView, bm);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    //加载图片，图片的压缩
                    //1:获得图片需要显示的大小
                    ImageSize imageSize = getImageViewSize(imageView);
                    //2:压缩图片
                    Bitmap bm = decodeSampleBitmapFromPath(path, imageSize.width, imageSize.height);
                    //3：把图片加入到缓存
                    addBitmapTpLruCache(path, bm);
                    refreashBitmap(path, imageView, bm);

                    mSemaphoreThreadPool.release();
                }
            });
        }
    }

    private void refreashBitmap(final String path, final ImageView imageView, Bitmap bm) {
        Message message = Message.obtain();
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 将图片加入缓存
     *
     * @param path
     * @param bm
     */
    protected void addBitmapTpLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null) {
            if (bm != null) {
                mLruCache.put(path, bm);
            }
        }
    }

    /**
     * 根据图片需要显示的宽和高进行压缩
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    protected Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {
        //获取图片的宽和高，并不把图片加载到内存中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateSampleSize(options, width, height);

        //使用获得的InSampleSizer再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int caculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    /**
     * 根据imageVIew获取适当的压缩的宽和高
     *
     * @param imageView
     * @return
     */
    protected ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        int width = imageView.getWidth();//获取imageview的实际宽度
        if (width <= 0) {
            width = lp.width;//获取imageview在layout中声明的宽度
        }
        if (width <= 0) {
            width = imageView.getMaxWidth();//检查最大值
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <= 0) {
            height = lp.height;
        }
        if (height <= 0) {
            height = imageView.getMaxHeight();
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    private synchronized void addTask(Runnable runable) {
        mTaskQueue.add(runable);
        try {
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * 根据path在缓存中获取Bitmap
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 初始化
     *
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {

        //后台轮询线程
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //线程池取出一个任务进行执行
                        mThreadPool.execute(getTask());

                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };

        mPoolThread.start();

        int maxMemery = (int) Runtime.getRuntime().maxMemory();
        int chcheMemery = maxMemery / 8;
        mLruCache = new LruCache<String, Bitmap>(chcheMemery) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        //创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;

        mSemaphoreThreadPool = new Semaphore(threadCount);
    }

    /**
     * 从任务队列取出一个方法
     *
     * @return
     */
    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }

    public enum Type {
        FIFO, LIFO;
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }

    /**
     * synchronized详解
     *
     * Java语言的关键字，当它用来修饰一个方法或者一个代码块时，能够保证同一时刻最多只有一个线程执行该段代码
     *
     * 1:当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，
     * 一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
     *
     * 2:然而，当一个线程访问object的一个synchronized(this)同步代码块时，
     * 另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
     *
     * 3:尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，
     * 其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
     *
     * 4:第三个例子同样适用其它同步代码块。也就是说，当一个线程访问object的一个synchronized(this)同步代码块时，
     * 它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。
     *
     * 5:以上规则对其它对象锁同样适用.
     */

    /**
     * 整体思路
     *
     * 后台轮询线程(Thread)不断访问任务队列（LinkList<Runnable>）,如果任务队列中有加载图片的任务（Runnable），
     * 就通过Handler发消息给线程池（ExecuterService），让线程池拿出一个子线程，
     * 然后根据调度任务的策略（LIFO）从任务队列中取出一个任务去完成图片的获取，
     * 因为图片是异步的在子线程中获取到的，不能直接显示，所以需要通过一个UI相关的Handler把图片对象发送到UI线程中，
     * 最后完成图片的显示。
     */
}

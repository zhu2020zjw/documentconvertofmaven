package com.zjw.convert.util;

import java.util.concurrent.ArrayBlockingQueue;

import application.dcs.Convert;

/**
 * @author admin
 */
public class ConvertorPool {
    
	private ConvertorPool() {}
    
    private static volatile ConvertorPool instance = null;
    private static final int maxSize = 2;
    
    private static ArrayBlockingQueue<Convert> aQueue = new ArrayBlockingQueue<>(maxSize);
    
    static {
    	for(int i = 0; i < maxSize; i++) {
    		try {
				aQueue.put(new Convert());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
	    
    public static ConvertorPool getInstance() {
		if (instance == null) {
			synchronized (ConvertorPool.class) {
				if(instance == null) {
					instance = new ConvertorPool();
				}
			}
		}
		return instance;
    }
    
    
    public Convert getConvert() {
		try {
			return aQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public void returnConvert(Convert convert) {
    	try {
			aQueue.put(convert);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
//	  private ConvertorPool() {}
//    
//    private static volatile ConvertorPool instance = null;
//    
//    private ArrayList<ConvertorObject> pool = new ArrayList<ConvertorObject>();
//    //池内维护了最大为5个实例，可以根据自己的服务器性能调整最大值
//    private static final int maxSize = 5;
//    
//    private int availSize = 0;//对象池内可用对象的个数
//    
//    private int current = 0;//用于标记对象池中的对象
//    
//    public static ConvertorPool getInstance() {
//        if (instance == null) {
//            synchronized (ConvertorPool.class) {
//				if(instance == null) {
//					instance = new ConvertorPool();
//				}
//			}
//        }
//        return instance;
//    }
//    //获取池内一个转换实例
//    public synchronized ConvertorObject getConvertor() {
//        if (availSize > 0) {
//            return getIdleConvertor();
//        } else if (pool.size() < maxSize) {
//            return createNewConvertor();
//        } else {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return getConvertor();
//        }
//    }
//    //使用完成后需要还给池内
//    public synchronized void returnConvertor(ConvertorObject convertor) {
//        for (ConvertorObject co : pool) {
//            if (co == convertor) {
//                co.available = true;
//                availSize++;
//                notify();
//                break;
//            }
//        }
//    }
//    
//    private synchronized ConvertorObject getIdleConvertor() {
//        for (ConvertorObject co : pool) {
//            if (co.available) {
//                co.available = false;
//                availSize--;
//                return co;
//            }
//        }
//        return null;
//    }
//    
//    private synchronized ConvertorObject createNewConvertor() {
//        ConvertorObject co = new ConvertorObject(++current);
//        co.convertor = new Convert();
//        co.available = false;
//        pool.add(co);
//        return co;
//    }
//    
//    //包装convert类，可记录是否在使用中
//    public class ConvertorObject {
//        public ConvertorObject(int id) {
//            this.id = id;
//        }
//        public int id;
//        public Convert convertor;
//        public boolean available;//记录是否被使用
//    }
    
}

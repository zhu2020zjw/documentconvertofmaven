package com.zjw.convert.bean;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.zjw.convert.exception.ComputerException;

public class Computer {
	
	private static String ip;

	static {
		try {
			Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
	
			while (nifs.hasMoreElements()) {
			    NetworkInterface nif = nifs.nextElement();
			
			    // 获得与该网络接口绑定的 IP 地址，一般只有一个
			    Enumeration<InetAddress> addresses = nif.getInetAddresses();
			    while (addresses.hasMoreElements()) {
			        InetAddress addr = addresses.nextElement();
			
			        if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
			            if(nif.getName().equals("wlan0")) {//只要无线局域网的ip地址
			            	ip = addr.getHostAddress();
			            }
			        }
			    }
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static String getIp() {
		if(ip.equals(null)) {
			try {
				throw new ComputerException("当前ip地址不存在");
			} catch (ComputerException e) {
				e.printStackTrace();
			}
		}
		return ip;
	}

}

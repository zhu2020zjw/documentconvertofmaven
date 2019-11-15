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
			
			    // ����������ӿڰ󶨵� IP ��ַ��һ��ֻ��һ��
			    Enumeration<InetAddress> addresses = nif.getInetAddresses();
			    while (addresses.hasMoreElements()) {
			        InetAddress addr = addresses.nextElement();
			
			        if (addr instanceof Inet4Address) { // ֻ���� IPv4 ��ַ
			            if(nif.getName().equals("wlan0")) {//ֻҪ���߾�������ip��ַ
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
				throw new ComputerException("��ǰip��ַ������");
			} catch (ComputerException e) {
				e.printStackTrace();
			}
		}
		return ip;
	}

}

package com.zjw.convert.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * ѹ���ļ��й�����
 *
 */
public class CompressUtils {

	/**
	 * ѹ���ļ��е�ָ��zip�ļ�
	 * 
	 * @param srcDir     Դ�ļ���
	 * @param targetFile Ŀ��֪��zip�ļ�
	 * @throws IOException IO�쳣���׳��������ߴ���
	 */
	public static void zip(String srcDir, String targetFile) throws IOException {

		try (OutputStream outputStream = new FileOutputStream(targetFile);) {
			zip(srcDir, outputStream);
		}
	}

	/**
	 * ѹ���ļ��е�ָ��������У������Ǳ����ļ��������Ҳ������web��Ӧ������
	 * 
	 * @param srcDir       Դ�ļ���
	 * @param outputStream ѹ�����ļ��������
	 * @throws IOException IO�쳣���׳��������ߴ���
	 */
	public static void zip(String srcDir, OutputStream outputStream) throws IOException {
		try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
				ArchiveOutputStream out = new ZipArchiveOutputStream(bufferedOutputStream);) {
			Path start = Paths.get(srcDir);
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					ArchiveEntry entry = new ZipArchiveEntry(dir.toFile(), start.relativize(dir).toString());
					out.putArchiveEntry(entry);
					out.closeArchiveEntry();
					return super.preVisitDirectory(dir, attrs);
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					try (InputStream input = new FileInputStream(file.toFile())) {
						ArchiveEntry entry = new ZipArchiveEntry(file.toFile(), start.relativize(file).toString());
						out.putArchiveEntry(entry);
						IOUtils.copy(input, out);
						out.closeArchiveEntry();
					}
					return super.visitFile(file, attrs);
				}

			});

		}
	}

	/**
	 * ��ѹzip�ļ���ָ���ļ���
	 * 
	 * @param zipFileName Դzip�ļ�·��
	 * @param destDir     ��ѹ�����·��
	 * @throws IOException IO�쳣���׳��������ߴ���
	 */
	public static void unzip(String zipFileName, String destDir) throws IOException {
		try (InputStream inputStream = new FileInputStream(zipFileName);) {
			unzip(inputStream, destDir);
		}

	}

	/**
	 * ���������л�ȡzip�ļ�������ѹ��ָ���ļ���
	 * 
	 * @param inputStream zip�ļ��������������Ǳ����ļ���������Ҳ������web�����ϴ���
	 * @param destDir     ��ѹ�����·��
	 * @throws IOException IO�쳣���׳��������ߴ���
	 */
	public static void unzip(InputStream inputStream, String destDir) throws IOException {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				ArchiveInputStream in = new ZipArchiveInputStream(bufferedInputStream);) {
			ArchiveEntry entry = null;
			while (Objects.nonNull(entry = in.getNextEntry())) {
				if (in.canReadEntryData(entry)) {
					File file = Paths.get(destDir, entry.getName()).toFile();
					if (entry.isDirectory()) {
						if (!file.exists()) {
							file.mkdirs();
						}
					} else {
						try (OutputStream out = new FileOutputStream(file);) {
							IOUtils.copy(in, out);
						}
					}
				} else {
					System.out.println(entry.getName());
				}
			}
		}

	}

}

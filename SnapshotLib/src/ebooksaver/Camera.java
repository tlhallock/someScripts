///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package ebooksaver;
//
//import java.awt.Rectangle;
//import java.awt.Robot;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.zip.CRC32;
//import javax.imageio.ImageIO;
//
///**
// *
// * @author John
// */
//public final class Camera {
//
//	private final Robot robot;
//	private boolean getHash;
//	private String fileType;
//	private boolean overwrite;
//
//	public Camera(Robot robot) {
//		this.robot = robot;
//	}
//	
//	public void setOverWrite(boolean overwrite) {
//		this.overwrite = overwrite;
//	}
//
//	public void setFileType(String filetype) {
//		this.fileType = filetype;
//	}
//	
//	public void setGetHash(boolean check) {
//		this.getHash = check;
//	}
//
//	public long saveImage(Rectangle window, String path) throws IOException {
//		BufferedImage image = robot.createScreenCapture(window);
//		File file = new File(path);
//		if (file.exists()) {
//			System.out.println("A file already exists at " + path);
//			if (overwrite) {
//				System.out.println("deleting...");
//			} else {
//				System.out.println("aborting...");
//				return 0;
//			}
//		}
//		ImageIO.write(image, fileType, file);
//		System.out.println("Saved image to " + path);
//		return getHash ? getHash(image) : 0;
//	}
//
//	private static long getHash(BufferedImage image) {
//		byte[] bytes = new byte[4];
//		CRC32 crc = new CRC32();
//		for (int i = 0; i < image.getWidth(); i++) {
//			for (int j = 0; j < image.getHeight(); j++) {
//				int rgb = image.getRGB(i, j);
//				bytes[0] = (byte) ((rgb >> 0) & 0xff);
//				bytes[1] = (byte) ((rgb >> 8) & 0xff);
//				bytes[2] = (byte) ((rgb >> 16) & 0xff);
//				bytes[3] = (byte) ((rgb >> 24) & 0xff);
//				crc.update(bytes);
//			}
//		}
//		return crc.getValue();
//	}
//}

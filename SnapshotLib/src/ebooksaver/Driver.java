///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package ebooksaver;
//
//import java.awt.AWTEvent;
//import java.awt.Point;
//import java.awt.Robot;
//import java.awt.Toolkit;
//import java.awt.event.AWTEventListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//
//import javax.swing.JOptionPane;
//
//import snap.Lens;
//
///**
// *
// * @author John
// */
//public final class Driver {
//
//	static private final Lens lens = new Lens();
//	static private final Menu menu = new Menu();
//	static private final Robot robot;
//	static private Thread thread;
//
//	static {
//		Robot r;
//		try {
//			r = new Robot();
//		} catch (Exception e) {
//			r = null;
//			handleException("This software is not supported on your system!",
//					e, true);
//		}
//		robot = r;
//	}
//	static private final Camera camera = new Camera(robot);
//	static boolean stop;
//
//	public static void updateWindow() {
//		menu.setWindow(lens.getWindow());
//	}
//
//	public static void showWindow() {
//		lens.focus();
//	}
//
//	public static void savePictures(final BookProps properties) {
//		try {
//			if (thread != null) {
//				throw new Exception("A save process is already started!");
//			}
//			Runnable runnable = new Runnable() {
//
//				@Override
//				public void run() {
//					menu.setTitle("Saving...");
//
//					stop = false;
//					boolean success = true;
//					long[] hashes = null;
//					if (properties.check) {
//						hashes = new long[properties.times];
//					}
//
//					new File(properties.location + File.separator
//							+ properties.bookName + File.separator).mkdirs();
//
//					System.out.println("Save path: " + properties.location);
//					System.out.println("Book name: " + properties.bookName);
//					System.out.println("Number of times: " + properties.times);
//					System.out.println("File extension: " + properties.filetype);
//					System.out.println("Wait interval: " + properties.wait);
//					System.out.println("Overwrite old files: " + properties.overwrite);
//					System.out.println("Check for repeats: " + properties.check);
//
//					camera.setFileType(properties.filetype);
//					camera.setGetHash(properties.check);
//					camera.setOverWrite(properties.overwrite);
//
//					for (int i = 0; i < properties.times; i++) {
//						try {
//							if (stop) {
//								success = false;
//								JOptionPane.showMessageDialog(null,
//										"Saving stopped!", "Stopped",
//										JOptionPane.INFORMATION_MESSAGE);
//								stop = false;
//								break;
//							}
//							String savePath = properties.location + File.separator + properties.bookName
//									+ File.separator + properties.bookName + "_Page"
//									+ (i + 1) + "." + properties.filetype;
//							try {
//								long hash = camera.saveImage(lens.getWindow(),
//										savePath);
//								if (properties.check) {
//									hashes[i] = hash;
//								}
//							} catch (Exception e) {
//								handleException("Error saving to " + savePath, e,
//										false);
//							}
//							menu.setTitle("Saving... (" + (i + 1)
//									+ " complete of " + properties.times + ")");
//                                                        
//                                                        Point center = lens.getCenter();
//                                                        robot.mouseMove(center.x, center.y);
//                                                        robot.delay(200);
//                                                        robot.mouseWheel(10);
//                                                                
//                                                                
//                                                                
//                                                                
//                                                        
//                                                        
////                                                        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
////                                                        robot.delay(200);
////                                                        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
////                                                        robot.mouseWheel(5);
////                                                        
////							robot.mouseMove(properties.turnPage.x, properties.turnPage.y);
////                                                        robot.delay(200);
////							robot.mousePress(MouseEvent.BUTTON1_MASK);
////                                                        robot.delay(200);
////							robot.mouseRelease(MouseEvent.BUTTON1_MASK);
//							if (properties.docMouse) {
//								final Point doc = menu.getDocLocation();
//								robot.mouseMove(doc.x, doc.y);
//							}
//                                                        
//                                                        
//                                                        
//							menu.setVisible(true);
//							Thread.sleep(properties.wait + (long)(Math.random() * properties.wait));
//						} catch (InterruptedException e) {
//							stop = true;
//						}
//					}
//					if (success) {
//						JOptionPane.showMessageDialog(null,
//								"Successfully saved "
//								+ properties.times + " images.", "Saving Complete!",
//								JOptionPane.INFORMATION_MESSAGE);
//						if (properties.check) {
//							checkForRepeats(hashes);
//						}
//					}
//					menu.setTitle("Ready...");
//					thread = null;
//				}
//			};
//
//			thread = new Thread(runnable);
//			thread.start();
//		} catch (Exception e) {
//			handleException("Could not save!", e, false);
//		}
//	}
//
//	public static void handleException(String message, Throwable t,
//			boolean die) {
//		JOptionPane.showMessageDialog(menu,
//				message + (t == null ? "" : "\n" + t.getMessage()),
//				"Uh oh!", JOptionPane.ERROR_MESSAGE);
//		if (die) {
//			System.exit(-1);
//		}
//	}
//
//	private static void checkForRepeats(long[] hashes) {
//		int dupCount = 0;
//		String filename = REPEAT_PREFIX + ".txt";
//		int num = 0;
//		while (new File(filename).exists()) {
//			filename = REPEAT_PREFIX + (++num) + ".txt";
//		}
//		try (PrintWriter out = new PrintWriter(
//						new FileWriter(filename))) {
//			for (int i = 0; i < hashes.length; i++) {
//				if (hashes[i] == 0) {
//					continue;
//				}
//				for (int j = i + 1; j < hashes.length; j++) {
//					if (hashes[j] == hashes[i]) {
//						dupCount++;
//						out.println("photo " + (i + 1)
//								+ " = photo " + (j + 1)
//								+ " crc32"
//								+ " = " + hashes[i]);
//					}
//				}
//			}
//			if (0 < dupCount) {
//				JOptionPane.showMessageDialog(null,
//						"There were "
//						+ "at least " + dupCount
//						+ " repeat pairs.\n Perhaps not "
//						+ "enough time was given for the"
//						+ " reader to turn the page.\n For"
//						+ " more details, please see\n"
//						+ filename + ".",
//						"Found Duplicates",
//						JOptionPane.WARNING_MESSAGE);
//			}
//		} catch (Exception e) {
//			handleException("Unable to write repeats to file!\n"
//					+ "There may have been repeats.", e, false);
//		}
//	}
//
//	public static void main(String[] args) {
////		robot.setAutoWaitForIdle(true);
//		menu.setTitle("Ready...");
//		lens.setVisible(true);
//		menu.setVisible(true);
//		updateWindow();
//		showWindow();
//	}
//	private final static String REPEAT_PREFIX;
//
//	static {
//		Toolkit.getDefaultToolkit().addAWTEventListener(
//				new AWTEventListener() {
//
//					private long lastTyped;
//
//					@Override
//					public void eventDispatched(AWTEvent event) {
//						long ctime = System.currentTimeMillis();
//						if (ctime < lastTyped + 1000) {
//							return;
//						}
//						lastTyped = ctime;
//						if (((KeyEvent) event).getKeyCode()
//								== KeyEvent.VK_ESCAPE) {
//							stop = true;
//							thread.interrupt();
//							System.out.println("escaped!");
//						}
//
//					}
//				}, AWTEvent.KEY_EVENT_MASK);
//
//		String s = "repeats";
//		try {
//			s = new File(s).getCanonicalPath();
//		} catch (Exception e) {
//		}
//		REPEAT_PREFIX = s;
//	}
//}

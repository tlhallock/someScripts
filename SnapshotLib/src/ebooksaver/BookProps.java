///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ebooksaver;
//
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintStream;
//import java.util.Properties;
//import java.util.Scanner;
//
///**
// *
// * @author default
// */
//public class BookProps extends Properties
//{   
//    public Rectangle bounds;
//    public String bookName;
//    public String location;
//    
//    public int currentProgress;
//    public int numPics;
//    
//    public long wait;
//    
//    public Point doc;
//    public Point turnPage;
//    
//    public int times;
//    public boolean overwrite;
//    public boolean docMouse;
//    public boolean check;
//    public String filetype;
//    
//    
//    void save(String filename) throws FileNotFoundException
//    {
//        try (PrintStream ps = new PrintStream(new File(filename));)
//        {
//            ps.println(bounds.x + "\t" + bounds.y + "\t"+ bounds.width + "\t"+ bounds.height);
//            ps.println(bookName);
//            ps.println(location);
//            ps.println(currentProgress);
//            ps.println(numPics);
//            ps.println(wait);
//            ps.println(doc.x + "\t" + doc.y);
//            ps.println(turnPage.x + "\t" + turnPage.y);
//            
//            ps.println(times);
//            ps.println(overwrite);
//            ps.println(docMouse);
//            ps.println(check);
//            ps.println(filetype);
//        }
//    }
//    
//    void read(String filename) throws FileNotFoundException
//    {
//        try (Scanner scanner = new Scanner(new File(filename)))
//        {
//            bounds = new Rectangle(
//                scanner.nextInt(),
//                scanner.nextInt(),
//                scanner.nextInt(),
//                scanner.nextInt());
//            
//            bookName = scanner.nextLine();
//            location = scanner.nextLine();
//            currentProgress = scanner.nextInt();
//            numPics = scanner.nextInt();
//            
//            wait = scanner.nextLong();
//            
//            doc = new Point(scanner.nextInt(), scanner.nextInt());
//            turnPage = new Point(scanner.nextInt(), scanner.nextInt());
//            
//            
//            times = scanner.nextInt();
//            overwrite = scanner.nextBoolean();
//            docMouse = scanner.nextBoolean();
//            check = scanner.nextBoolean();
//            filetype = scanner.nextLine();
//        }
//    }
//}

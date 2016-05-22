package snap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class Lens {

	private final BoundPoint ul = new BoundPoint();
	private final BoundPoint ur = new BoundPoint();
	private final BoundPoint lr = new BoundPoint();
	private final BoundPoint ll = new BoundPoint();

	public Lens() {
		final int startOffset = 1500;
		ul.setLocation(startOffset, startOffset);
		ur.setLocation(startOffset + BOUND_POINT_WIDTH, startOffset);
		ll.setLocation(startOffset, startOffset + BOUND_POINT_WIDTH);
		lr.setLocation(startOffset + BOUND_POINT_WIDTH, startOffset
				+ BOUND_POINT_WIDTH);

		ul.setListener(new PositionListener(ul, ll, ur));
		ur.setListener(new PositionListener(ur, lr, ul));
		lr.setListener(new PositionListener(lr, ur, ll));
		ll.setListener(new PositionListener(ll, ul, lr));

		ul.setBounds(new PositionBound(ur, ll, null, null));
		ur.setBounds(new PositionBound(null, lr, ul, null));
		lr.setBounds(new PositionBound(null, null, ll, ur));
		ll.setBounds(new PositionBound(lr, null, null, ul));

		ul.setImage(ulImage);
		ur.setImage(urImage);
		lr.setImage(lrImage);
		ll.setImage(llImage);
	}
        
        void setBounds(Rectangle bounds)
        {
            ul.setLocation(bounds.x - ul.getWidth(), bounds.y - ul.getHeight());
            ur.setLocation(bounds.x + bounds.width, bounds.y - ur.getHeight());
            ll.setLocation(bounds.x - ll.getWidth(), bounds.y + bounds.height);
            lr.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
        }
//        
//        void write(BookProps props)
//        {
//            props.bounds = getWindow();
//        }

	public void setVisible(boolean visible) {
		ul.setVisible(visible);
		ur.setVisible(visible);
		lr.setVisible(visible);
		ll.setVisible(visible);
	}
	
	public void setOnTop(boolean onTop)
	{
		ul.setAlwaysOnTop(onTop);
		ur.setAlwaysOnTop(onTop);
		lr.setAlwaysOnTop(onTop);
		ll.setAlwaysOnTop(onTop);
	}

	public void focus() {
		setVisible(true);
	}
	public void cover() {
		setVisible(false);
	}

	public Rectangle getWindow() {
		final int x = ul.getX() + ul.getWidth();
		final int y = ul.getY() + ul.getHeight();
		final int w = lr.getX() - x;
		final int h = lr.getY() - y;
		return new Rectangle(x, y, w, h);
	}
        
        public Point getCenter()
        {
            Rectangle window = getWindow();
            return new Point(
                    window.x + window.width  / 2,
                    window.y + window.height / 2);
        }

	private static final class BoundPoint extends JFrame {

		private final JLabel label;
		private PositionListener listener;
		private PositionBound bounds;
		private Point lastClick;

		public BoundPoint() {
			setSize(BOUND_POINT_WIDTH, BOUND_POINT_WIDTH);
			setResizable(false);
			setUndecorated(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLayout(null);
			
			label = new JLabel();
			label.setBounds(0, 0, BOUND_POINT_WIDTH, BOUND_POINT_WIDTH);
			add(label);
			label.setBackground(Color.red);
			label.setVisible(true);

			label.addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if (lastClick == null || listener == null || bounds == null) {
						return;
					}
					final Point n = e.getLocationOnScreen();
					final Point p = new Point(n.x - lastClick.x,
							n.y - lastClick.y);

					listener.setLocation(p, bounds);
				}
			});

			label.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					lastClick = e.getPoint();
				}
			});
		}

		@Override
		public void setBackground(Color bgColor) {
			super.setBackground(bgColor);
			if (label != null) {
				label.setBackground(bgColor);
			}
		}

		public void setImage(Image image) {
			label.setIcon(
					new ImageIcon(image.getScaledInstance(BOUND_POINT_WIDTH,
					BOUND_POINT_WIDTH, Image.SCALE_DEFAULT)));
		}

		public void setListener(PositionListener listener) {
			this.listener = listener;
		}

		public void setBounds(PositionBound bounds) {
			this.bounds = bounds;
		}
	}

	private static final class PositionListener {

		private final BoundPoint main;
		private final BoundPoint w;
		private final BoundPoint h;

		public PositionListener(BoundPoint main, BoundPoint w, BoundPoint h) {
			this.main = main;
			this.w = w;
			this.h = h;
		}

		public void setLocation(Point p, PositionBound bounds) {
			p = new Point(
					Math.max(Math.min(p.x, bounds.getMaxX()), bounds.getMinX()),
					Math.max(Math.min(p.y, bounds.getMaxY()), bounds.getMinY()));
			main.setLocation(p);
			w.setLocation(p.x, w.getY());
			h.setLocation(h.getX(), p.y);
//			Driver.updateWindow();
		}
	}

	private static final class PositionBound {

		private final BoundPoint maxX;
		private final BoundPoint maxY;
		private final BoundPoint minX;
		private final BoundPoint minY;

		public PositionBound(BoundPoint maxX, BoundPoint maxY, BoundPoint minX,
				BoundPoint minY) {
			this.maxX = maxX;
			this.maxY = maxY;
			this.minX = minX;
			this.minY = minY;
		}

		public int getMaxX() {
			if (maxX == null) {
				return Integer.MAX_VALUE;
			}
			return maxX.getX() - BOUND_POINT_WIDTH;
		}

		public int getMaxY() {
			if (maxY == null) {
				return Integer.MAX_VALUE;
			}
			return maxY.getY() - BOUND_POINT_WIDTH;
		}

		public int getMinX() {
			if (minX == null) {
				return 0;
			}
			return minX.getX() + BOUND_POINT_WIDTH;
		}

		public int getMinY() {
			if (minY == null) {
				return 0;
			}
			return minY.getY() + BOUND_POINT_WIDTH;
		}
	}
	private static final Image ulImage;
	private static final Image urImage;
	private static final Image lrImage;
	private static final Image llImage;

	private static BufferedImage getImage(String file, BufferedImage image) {
		try {
			return ImageIO.read(Lens.class.getResourceAsStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			return image;
		}
	}

	static
	{
		BufferedImage defaultImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = defaultImage.createGraphics();
		graphics.setColor(Color.red);
		graphics.fillRect(0, 0, 10, 10);
		ulImage = getImage("/ul.png", defaultImage);
		urImage = getImage("/ur.png", defaultImage);
		lrImage = getImage("/lr.png", defaultImage);
		llImage = getImage("/ll.png", defaultImage);
	}
	private static final int BOUND_POINT_WIDTH = 20;

}
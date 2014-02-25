package Closest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

//РљР»Р°СЃСЃ РіР»Р°РІРЅРѕРіРѕ РѕРєРЅР° РїСЂРёР»РѕР¶РµРЅРёСЏ
public class GeoFrame extends JFrame {
    private static GeoFrame gFrame = new GeoFrame();

    private static JLabel jLabel1;
    private static JTextArea jText1;

    private static JButton jButton1;
    private static JButton jButton2;
    private static JButton jButton3;

    //Р”РѕРїРѕР»РЅРёС‚РµР»СЊРЅС‹Рµ РєРѕРјРїРѕРЅРµРЅС‚С‹
    private static BufferedImage image1;
    private static Graphics g;

    private static final String rules = "РџСЂР°РІРёР»Р°:" +
            "\nР§С‚РѕР±С‹ РґРѕР±Р°РІРёС‚СЊ С‚РѕС‡РєСѓ, С‰РµР»РєРЅРёС‚Рµ РЅР° \nСЃРІРѕР±РѕРґРЅСѓСЋ РѕР±Р»Р°СЃС‚СЊ." +
            "\nРќР°С‡Р°Р»Рѕ РєРѕРѕСЂРґРёРЅР°С‚ СЃР»РµРІР° РІРІРµСЂС…Сѓ." +
            "\nРџСЂРё С‰РµР»С‡РєРµ РЅР° С‚РѕС‡РєСѓ, РѕРЅР° СѓРґР°Р»РёС‚СЃСЏ." +
            "\nРўРѕС‡РєРё РјРѕР¶РЅРѕ РїРµСЂРµРјРµС‰Р°С‚СЊ." +
            "\nР’Рѕ РІСЂРµРјСЏ РІС‹РїРѕР»РЅРµРЅРёСЏ Р°Р»РіРѕСЂРёС‚РјР° Р·Р°РїСЂРµС‰РµРЅС‹" +
            "\nР»СЋР±С‹Рµ РёР·РјРµРЅРµРЅРёСЏ.";

    //РљРѕСЃС‚СЂСѓРєС‚РѕСЂ РѕРєРЅР°
    private GeoFrame() {
        Toolkit t = Toolkit.getDefaultToolkit();
        int DEFAULT_POSITION_X = t.getScreenSize().width / 9;
        int DEFAULT_POSITION_Y = t.getScreenSize().height / 9;
        int DEFAULT_WIDTH = (int)(t.getScreenSize().width / 1.25);
        int DEFAULT_HEIGHT = (int)(t.getScreenSize().height / 1.25);

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setIconImage(Toolkit.getDefaultToolkit().createImage("C:\\Users\\РРіРѕСЂСЊ\\Desktop\\Р”РѕРєСѓРјРµРЅС‚С‹\\X7l5SlpT1Uc.jpg"));
        setTitle("Р“РµРѕРјРµС‚СЂРёСЏ 4.1. Р—Р°РґР°РЅРёРµ 4");

        JPanel gPanel1 = new JPanel(new BorderLayout());
        jLabel1 = new JLabel();
        jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1 = new JTextArea(rules);
        jText1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1.setEditable(false);
        JPanel gPanel2 = new JPanel(new GridLayout(12, 1));
        gPanel1.add(jLabel1, BorderLayout.EAST);
        gPanel1.add(gPanel2, BorderLayout.WEST);
        gPanel1.add(jText1, BorderLayout.CENTER);

        jButton1 = new JButton();
        jButton1.setText("Clear");
        jButton3 = new JButton();
        jButton3.setText("Random");
        jButton2 = new JButton();
        jButton2.setEnabled(false);
        jButton2.setText("Go!");
        gPanel2.add(jButton1);
        gPanel2.add(jButton2);
        gPanel2.add(jButton3);

        setContentPane(gPanel1);
        setVisible(true);

        image1 = new BufferedImage(getWidth() / 2, jLabel1.getHeight(), BufferedImage.TYPE_INT_RGB);
        image1.getGraphics().fillRect(0, 0, 1000, 1000);
        image1.getGraphics().setColor(Color.BLACK);

        ImageIcon iIcon1 = new ImageIcon();
        iIcon1.setImage(image1);
        jLabel1.setIcon(iIcon1);
        g = image1.getGraphics();
    }

    public static GeoFrame getGeoFrame() {
        return gFrame;
    }

    //Р“Р»РѕР±Р°Р»СЊРЅС‹Рµ РїРµСЂРµРјРµРЅРЅС‹Рµ
    static ArrayList<Point> points = new ArrayList<Point>(),
            xpoints = new ArrayList<Point>(),
            ypoints = new ArrayList<Point>();

    //РќР°Р¶Р°С‚Р° Р»Рё РєРЅРѕРїРєР°
    static boolean isClicked = false;
    /*
     * Р•СЃР»Рё isDragged > 0, Р·РЅР°С‡РёС‚, Р±С‹Р»Рѕ РЅР°Р¶Р°С‚РёРµ РЅР° С‚РѕС‡РєСѓ
     * -1 Рё 0 - РѕС‚СЃСѓС‚СЃС‚РІРёРµ РЅР°Р¶Р°С‚РёСЏ
     * -2 - РґРѕ СЃРѕР±С‹С‚РёСЏ Р±С‹Р»Рѕ РЅР°Р¶Р°С‚РёРµ РЅР° С‚РѕС‡РєСѓ
     */
    static int isDragged = -1;
    //РљРѕРѕСЂРґРёРЅР°С‚С‹ С‚РѕС‡РєРё РЅР°Р¶Р°С‚РёСЏ
    static int X0, Y0;

    public static void main(String[] args) {
        final GeoFrame mainFrame = GeoFrame.getGeoFrame();

        jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                isClicked = true;
                //РќР°Р¶Р°С‚РёРµ РїРѕ С‚РѕС‡РєРµ РёР»Рё РЅРµС‚.
                for (Point point : points) {
                    if (point.isContained(new Point(e.getX(), e.getY()))) {
                        isDragged = points.indexOf(point);
                        X0 = e.getX();
                        Y0 = e.getY();
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                boolean isDelete = false;
                if (isClicked) {
                    boolean isContained = false;
                    //РџСЂРѕРІРµСЂРєР°, РѕС‚РїСѓСЃРєР°РµРј Р»Рё РЅР°Рґ РѕР±Р»Р°СЃС‚СЊСЋ С‚РѕС‡РєРё
                    for (Point point : points) {
                        isContained = point.equals(new Point(e.getX(), e.getY()), 40);
                        isDelete = isContained &&
                                point.equals(new Point(e.getX(), e.getY()), 30) && isDragged != -2;
                        if (isContained) break;
                    }
                    if (isDragged != -2) {
                        if (!isContained) {
                            points.add(new Point(e.getX(), e.getY()));
                            g.drawOval(e.getX() - 3, e.getY() - 3, 3, 3);
                        } else if (isDragged > -1 && isDelete) {
                            points.remove(isDragged);
                        }
                    } else {
                        points.add(new Point(X0, Y0));
                        g.drawOval(X0 - 3, Y0 - 3, 3, 3);
                    }

                    isDragged = -1;
                    isClicked = false;
                    clearImage();
                    showPoints();
                    jLabel1.repaint();
                }
                jButton2.setEnabled(points.size() > 2);
            }
        }
        );

        jLabel1.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                boolean isSoClose = false;
                if (isClicked) {
                    if (isDragged > -1) {
                        g.drawOval(points.get(isDragged).getX() - 3,
                                points.get(isDragged).getY() - 3, 3, 3);
                        points.remove(isDragged);
                        isDragged = -2;
                    } else if (isDragged == -2) {
                        for (Point point : points) {
                            isSoClose = point.equals(new Point(e.getX(), e.getY()), 40);
                            if (isSoClose) break;
                        }
                        if (!isSoClose && e.getX() > 3 && e.getX() < jLabel1.getWidth() - 3
                                && e.getY() > 3 && e.getY() < jLabel1.getHeight() - 3) {
                            clearImage();
                            g.drawOval(e.getX() - 3,
                                    e.getY() - 3, 3, 3);
                            X0 = e.getX();
                            Y0 = e.getY();

                        }
                    }
                    showPoints();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });


        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image1.getGraphics().setColor(Color.WHITE);
                image1.getGraphics().fillRect(0, 0, 780, 640);
                jLabel1.repaint();
                points.clear();
                jText1.setText(rules);
                jButton2.setEnabled(false);
            }
        }
        );

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xpoints = new ArrayList<Point>(points);
                ypoints = new ArrayList<Point>(points);
                sortByX(xpoints);
                sortByY(ypoints);

                System.out.println();

                System.out.println();
                ArrayList<Point> aux = new ArrayList<Point>();
                Line l = mainFrame.closestPair(xpoints, ypoints, aux, 0, xpoints.size() - 1, new Line(0, 0, 500, 500));
                g.setColor(Color.GREEN);
                clearImage();
                showPoints();
                g.drawLine(l.getPoint0().getX(),
                        l.getPoint0().getY(),
                        l.getPoint1().getX(),
                        l.getPoint1().getY());
                mainFrame.jLabel1.update(mainFrame.jLabel1.getGraphics());
                g.setColor(Color.RED);
            }
        }
        );

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random r = new Random();
                for (int i = 0; i < 10; i++) {
                    boolean isCons = false;
                    int X = 10 + r.nextInt(jLabel1.getWidth() - 20);
                    int Y = 10 + r.nextInt(jLabel1.getWidth() - 20);
                    for (Point point : points) {
                        isCons = point.isContained(new Point(X, Y));
                        if (isCons) break;
                    }
                    if (!isCons) {
                        points.add(new Point(X, Y));
                        clearImage();
                        showPoints();
                    }
                    jLabel1.repaint();
                }
                jButton2.setEnabled(points.size() > 2);
            }
        }
        );
    }

    public static void clearImage() {
        image1.getGraphics().setColor(Color.WHITE);
        image1.getGraphics().fillRect(0, 0, jLabel1.getWidth(), jLabel1.getHeight());
        image1.getGraphics().setColor(Color.RED);
    }

    public static void showPoints() {
        for (Point point : points) {
            g.setColor(Color.RED);
            g.setPaintMode();
            g.drawOval(point.getX() - 3, point.getY() - 3, 3, 3);
            jLabel1.repaint();
        }
    }

    public static void sortByX(ArrayList<Point> xArray) {
        Collections.sort(xArray, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.getX() > p2.getX()) {
                    return 1;
                } else if (p1.getX() < p2.getX()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public static void sortByY(ArrayList<Point> yArray) {
        Collections.sort(yArray, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.getY() > p2.getY()) {
                    return 1;
                } else if (p1.getY() < p2.getY()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public Line closestPair(ArrayList<Point> xPoints, ArrayList<Point> yPoints, ArrayList<Point> aux,
                            int left, int right, Line s0) {
        if (left >= right) {
            return new Line(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        int middle = ((right + left) >> 1) + ((right + left) % 2);
        Point mPoint = xPoints.get(middle);

        Line lS = closestPair(xPoints, yPoints, aux, left, middle - 1, s0);
        Line rS = closestPair(xPoints, yPoints, aux, middle + 1, right, s0);
        Line s = lS.compare(rS).compare(s0);

        int index = 0;

        for (int i = left; i <= right; i++) {
            if ((Math.abs((new Line(yPoints.get(i), mPoint)).getSize())) < (s.getSize())) {
                aux.add(index++, yPoints.get(i));
            }
        }

        for (int i = 0; i < index; i++) {
            for (int j = i + 1; (j < index) && (aux.get(i).getY() - aux.get(j).getY()) <
                    (s.getSize()); j++) {
                if (((new Line(aux.get(i), aux.get(j))).getSize()) < (s.getSize())) {
                    s = new Line(aux.get(i), aux.get(j));
                }
            }
        }


        this.g.setColor(Color.YELLOW);
        if (lS.getPoint1().getX() < 400) {
            drawB(lS);
        }
        this.g.setColor(Color.BLUE);
        if (rS.getPoint1().getX() < 400) {
            drawB(rS);
        }
        this.g.setColor(Color.BLACK);
        drawB(s);
        this.jLabel1.update(this.jLabel1.getGraphics());
        this.g.setColor(Color.RED);


        return s;
    }

    public static synchronized void drawB(Line s) {
        try {
            Thread.sleep(10);
            g.drawLine(s.getPoint0().getX(),
                    s.getPoint0().getY(),
                    s.getPoint1().getX(),
                    s.getPoint1().getY());
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

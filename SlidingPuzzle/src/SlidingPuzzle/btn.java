package SlidingPuzzle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class btn extends JButton implements MouseListener {

    int movementCounter = 0;

    private JFrame frame = new JFrame("SlidingPuzzle");

    private int row, col;
    private int sizeRow, sizeCol;
    private JButton[][] board;
    private int emptyRow, emptyCol;
    private JButton emptyButton = new JButton(Integer.toString(sizeCol * sizeRow));
    
    private boolean isShuffled = false;
    
    private Clip clip;	

    public static void main(String[] args) {
        btn b = new btn();
    }

    public btn() {
        frame.setSize(720,788);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu bar
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem item = new JMenuItem("Shuffle");
        JMenuItem item2 = new JMenuItem("Set Puzzle Size");
        JMenuItem item3 = new JMenuItem("Choose Picture");
        JMenuItem item4 = new JMenuItem("Choose Type");
        JMenuItem item6 = new JMenuItem("References");
        JMenuItem item5 = new JMenuItem("Music");
        JMenu musicMenu = new JMenu("Music");
        JMenuItem musicOn = new JMenuItem("On");
        JMenuItem musicOff = new JMenuItem("Off");

        menu.add(item);
        mb.add(menu);
        frame.setJMenuBar(mb);
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                shuffle();
                frame.revalidate();
                frame.repaint();
            }
            
        });

        menu.add(item2);
        mb.add(menu);
        frame.setJMenuBar(mb);
        item2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.getContentPane().removeAll();
                setPuzzle();
                isShuffled = false;
                movementCounter = 0;
            }

        });
        menu.add(item3);
        mb.add(menu);
        frame.setJMenuBar(mb);
        item3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.getContentPane().removeAll();
                choosePic();
                isShuffled = false;
                movementCounter = 0;
            }

        });
        
        menu.add(item4);
        mb.add(menu);
        frame.setJMenuBar(mb);
        item4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.getContentPane().removeAll();
                setType();
                isShuffled = false;
                movementCounter = 0;
            }

        });
        
        menu.add(musicMenu);
        mb.add(menu);
        musicMenu.add(musicOn);
        musicMenu.add(musicOff);
        frame.setJMenuBar(mb);
        
        musicOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic();
            }
        });
        
        musicOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();
            }
        });
        
        menu.add(item6);
        mb.add(menu);
        frame.setJMenuBar(mb);
        item6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame ref = new JFrame("References");
            	ref.setLayout(null);
            	JLabel tuna = new JLabel("Tuna Bekiş");
            	JLabel emre = new JLabel("Emre Erdem");
            	JLabel metehan = new JLabel("Metehan Kartop");
            	JLabel ismail = new JLabel("İsmail Ambarkütük");
            	JLabel gpt = new JLabel("Chat GPT version 3.5");
            	ref.setResizable(false);
            	ref.setVisible(true);
            	ref.getContentPane().setBackground(Color.white);
            	ref.setSize(400,300);
                ref.setLocationRelativeTo(null);
                ref.add(tuna);
            	ref.add(emre);
            	ref.add(ismail);
            	ref.add(metehan);
            	ref.add(gpt);
            	tuna.setBounds(20,20,150,40);
            	emre.setBounds(20,60,150,40);
            	ismail.setBounds(20,100,150,40);
            	metehan.setBounds(20,140,150,40);
            	gpt.setBounds(20,180,150,40);
         	
                frame.revalidate();
                frame.repaint();

            }

        });
       
        setPuzzle();
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            findIndex(e);
            
            boolean up = (emptyCol == this.col) && (emptyRow == this.row + 1);
            boolean right = (emptyCol == this.col - 1) && (emptyRow == this.row);
            boolean left = (emptyCol == this.col + 1) && (emptyRow == this.row);
            boolean down = (emptyCol == this.col) && (emptyRow == this.row - 1);

            if (up || right || left || down) {
                soundEffect(1);
            	swapButton();
            }
            else {
                soundEffect(0);
            }
            if (isShuffled) {
                check();
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void findIndex(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeRow; j++) {
                if (board[i][j] == button) {
                    this.row = i;
                    this.col = j;
                }
            }
        }
    }

    public void swapButton() {

        movementCounter++;

        int a = this.row + this.col;
        int b = emptyRow + emptyCol;
        JButton[][] temp = new JButton[sizeRow][sizeCol];
        JButton clicked = board[this.row][this.col];

        frame.getContentPane().removeAll();


        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeCol; j++) {
                if (board[i][j] != emptyButton) {
                    temp[i][j] = board[i][j];
                }
            }
        }

        temp[this.row][this.col] = emptyButton;
        temp[emptyRow][emptyCol] = clicked;
        board = temp;

        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeRow; j++) {
                frame.getContentPane().add(board[i][j]);
            }
        }

        this.emptyRow = this.row;
        this.emptyCol = this.col;



        frame.revalidate();
        frame.repaint();
    }



    public void shuffle() {
        movementCounter =0;
        Random random = new Random();
        int count = 1;
        int[][] template = new int[sizeRow][sizeCol];
        int index = 1;
        int row9 = sizeRow-1; // Başlangıç konumu
        int col9 = sizeCol-1; // Başlangıç konumu

        // Başlangıç durumu
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeCol; j++) {
                template[i][j] = index++;
            }
        }

        // Karıştırma
        while (count <10000+2) {
            int move=random.nextInt(4)+1;


            int tempint;
            if(move==1) {
                if(col9>0) {
                    tempint=template[row9][col9-1];
                    template[row9][col9-1]=sizeRow*sizeCol;
                    template[row9][col9]=tempint;
                    col9=col9-1;
                    count++;

                }

            }
            else if(move==2) {
                if(row9>0) {
                    tempint=template[row9-1][col9];
                    template[row9-1][col9]=sizeRow*sizeCol;
                    template[row9][col9]=tempint;
                    row9=row9-1;
                    count++;

                }

            }
            else if(move==3) {
                if(col9<sizeCol-1) {
                    tempint=template[row9][col9+1];
                    template[row9][col9+1]=sizeRow*sizeCol;
                    template[row9][col9]=tempint;
                    col9=col9+1;
                    count++;

                }

            }
            else if(move==4) {
                if(row9<sizeRow-1) {
                    tempint=template[row9+1][col9];
                    template[row9+1][col9]=sizeRow*sizeCol;
                    template[row9][col9]=tempint;
                    row9=row9+1;
                    count++;

                }

            }

        }


        JButton[][] temp=new JButton[sizeRow][sizeCol];

        for(int i=0;i<sizeRow;i++) {
            for(int j=0;j<sizeCol;j++) {
                for(int k=0;k<sizeRow;k++) {
                    for(int l=0;l<sizeCol;l++) {
                        if(template[i][j]==Integer.parseInt(board[k][l].getText())) {
                            temp[i][j]=board[k][l];
                        }
                    }
                }
            }
        }

        board=temp;

        frame.getContentPane().removeAll();

        for(int i=0;i<sizeRow;i++) {
            for(int j=0;j<sizeCol;j++) {
                frame.getContentPane().add(board[i][j]);
            }
        }

        for(int i=0;i<sizeRow;i++) {
            for(int j=0;j<sizeCol;j++) {
                if(board[i][j].getText().equals(Integer.toString(sizeCol * sizeRow))) {
                    this.emptyRow=i;
                    this.emptyCol=j;
                }
            }
        }
        isShuffled=true;
    }
    public void check() {

        int index = 1;
        boolean m = true;
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeRow; j++) {
                if (!(index++ == Integer.valueOf(board[i][j].getText()))) {
                    m = false;
                    break;
                }
            }

        }
        if (m) {
            JFrame congrats = new JFrame();
            /*ImageIcon ikon = new ImageIcon("\"C:\\Users\\ismai\\OneDrive\\Masa�st�\\images.jpeg\"");
            congrats.setIconImage(ikon.getImage());*/
        	ImageIcon imageIcon1 = new ImageIcon("C:\\Users\\ismai\\OneDrive\\Masa�st�\\SlidingPuzzleImages\\choosePicture1.jpg");
            Image image1 = imageIcon1.getImage();
            
            congrats.setSize(400, 200); // Pencere boyutu
            congrats.getContentPane().setBackground(new Color(140, 10, 110)); // Arka plan rengi
            JLabel label = new JLabel("Congratulations! Movements:" + movementCounter);  // Yazı değiştirildi
            label.setForeground(Color.WHITE); // Yazı rengi
            label.setFont(new Font("Arial", Font.BOLD, 20)); // Yazı tipi ve boyutu
            congrats.add(label);
            label.setBounds(50, 30, 400, 50); // Yazı konumu ve boyutu
            congrats.setLayout(null);
            congrats.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirir
            congrats.setVisible(true);
            frame.setVisible(false);

            isShuffled = false;

            JButton a = new JButton("New Game?");
            a.setBounds(140, 80, 120, 60);
            congrats.add(a);
            a.setFont(new Font("Arial",Font.BOLD,10));
            a.setBackground(new Color(250,250,250));
            a.setVisible(true);

            a.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                	soundEffect(1);
                    movementCounter = 0;
                    isShuffled = false;
                    frame.getContentPane().removeAll();
                    setType();
                    frame.setVisible(false);
                    congrats.setVisible(false);

                }
            });




        }
    }

    public void createPuzzle() {
        ImageIcon ikon = new ImageIcon("\"C:\\Users\\ismai\\OneDrive\\Masa�st�\\SlidingPuzzleImages\\choosePicture2.jpg\"");
        frame.setIconImage(ikon.getImage());



        board = new JButton[this.sizeRow][this.sizeCol];
        frame.getContentPane().setLayout(new GridLayout(sizeRow, sizeCol));
        int index = 1;
        int index2 = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (index2 < sizeRow * sizeCol) {
                    JButton b = new JButton(String.valueOf(index++));

                    b.setBackground(new Color(110, 10, 140)); // Light Orange
                    b.setForeground(Color.WHITE);
                    b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    b.setFocusPainted(false);
                    
                    if(isPicture) {
                    	b.setFont(b.getFont().deriveFont(0f));
                    }else {b.setFont(b.getFont().deriveFont(20.0f));}
                    b.addMouseListener(this);
                    board[i][j] = b;
                    index2++;
                } else if (index2 == sizeRow * sizeCol) {

                    emptyButton.setVisible(false);
                    emptyButton.setText(Integer.toString(sizeCol * sizeRow));
                    board[sizeRow - 1][sizeCol - 1] = emptyButton;

                }


            }
        }
        for(int i=0;i<sizeRow;i++) {
            for(int j=0;j<sizeCol;j++) {
                frame.getContentPane().add(board[i][j]);
            }
        }
        this.emptyRow = sizeRow - 1;
        this.emptyCol = sizeCol - 1;
        frame.setVisible(true);
    }

    public void setPuzzle(){
        JFrame puzzleSize=new JFrame("Set Size and Picture");
        ImageIcon ikon = new ImageIcon("src/SlidingPuzzle/image.png");
        puzzleSize.setIconImage(ikon.getImage());
        puzzleSize.getContentPane().setBackground(new Color(150,150,250));

        JButton three = new JButton("3x3");
        JButton four = new JButton("4x4");
        JButton five = new JButton("5x5");
        JButton six = new JButton("6x6");
        JButton seven = new JButton("7x7");
        JButton eight = new JButton("8x8");
        JLabel label = new JLabel("Choose Puzzle's Size");
        label.setForeground(new Color(150,30,250));
        label.setFont(new Font("Arial", Font.BOLD, 16));


        puzzleSize.add(label);
        label.setBounds(160,40,200,20);


        puzzleSize.setSize(500,340);
        puzzleSize.setLayout(null);
        puzzleSize.setVisible(true);
        puzzleSize.setResizable(false);
        puzzleSize.setLocationRelativeTo(null);

        three.setBounds(40,80,120,90);
        four.setBounds(180,80,120,90);
        five.setBounds(320,80,120,90);
        six.setBounds(40,190,120,90);
        seven.setBounds(180,190,120,90);
        eight.setBounds(320,190,120,90);

        three.setBackground(new Color(225,160,250));
        four.setBackground(new Color(225,160,250));
        five.setBackground(new Color(225,160,250));
        six.setBackground(new Color(225,160,250));
        seven.setBackground(new Color(225,160,250));
        eight.setBackground(new Color(225,160,250));

        three.setForeground(new Color(150,30,250));
        four.setForeground(new Color(150,30,250));
        five.setForeground(new Color(150,30,250));
        six.setForeground(new Color(150,30,250));
        seven.setForeground(new Color(150,30,250));
        eight.setForeground(new Color(150,30,250));


        three.setFont(new Font("Arial", Font.BOLD, 16));
        four.setFont(new Font("Arial", Font.BOLD, 16));
        five.setFont(new Font("Arial", Font.BOLD, 16));
        six.setFont(new Font("Arial", Font.BOLD, 16));
        seven.setFont(new Font("Arial", Font.BOLD, 16));
        eight.setFont(new Font("Arial", Font.BOLD, 16));

        puzzleSize.add(three);
        puzzleSize.add(four);
        puzzleSize.add(five);
        puzzleSize.add(six);
        puzzleSize.add(seven);
        puzzleSize.add(eight);

        three.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                sizeCol = 3;
                sizeRow=3;
                setType();
                puzzleSize.setVisible(false);
            }
        });
        four.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                sizeCol = 4;
                sizeRow=4;
                setType();
                puzzleSize.setVisible(false);
            }
        });
        five.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                sizeCol = 5;
                sizeRow=5;
                setType();
                puzzleSize.setVisible(false);
            }
        });
        six.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                sizeCol = 6;
                sizeRow=6;
                setType();
                puzzleSize.setVisible(false);
            }
        });
        seven.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                sizeCol = 7;
                sizeRow=7;
                setType();
                puzzleSize.setVisible(false);
            }
        });
        eight.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                sizeCol = 8;
                sizeRow=8;
                setType();
                puzzleSize.setVisible(false);
            }
        });
    }
    public void addPicture(ImageIcon img) {
    	ImageIcon imageIcon = img;
    	Image image = imageIcon.getImage();
    	
    	// Par�a say�s�n� belirle
        int size = this.sizeCol;

        // Resmin boyutlar�na g�re par�alar� hesapla
        int parcaGenislik = imageIcon.getIconWidth() / size;
        int parcaYukseklik = imageIcon.getIconHeight() / size;

        // Her bir par�a i�in ImageIcon dizisi olu�tur
        ImageIcon[][] imageIconParca = new ImageIcon[size][size];

        // Par�alar� olu�tur
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Her bir par�ay� BufferedImage olarak olu�tur
                BufferedImage imageBuf = new BufferedImage(parcaGenislik, parcaYukseklik, BufferedImage.TYPE_INT_ARGB);

                // Her bir par�ay� ana resimden alarak kopyala
                Graphics2D g2d = imageBuf.createGraphics();
                g2d.drawImage(image, 0, 0, parcaGenislik, parcaYukseklik, parcaGenislik * j, parcaYukseklik * i, parcaGenislik * (j + 1), parcaYukseklik * (i + 1), null);
                g2d.dispose();

                // Olu�turulan BufferedImage'� ImageIcon'a d�n��t�r ve par�a dizisine ekle
                imageIconParca[i][j] = new ImageIcon(imageBuf);

                // Board �zerindeki butonlara par�alar� ekle
                board[i][j].setIcon(imageIconParca[i][j]);
            }
        }
    	
    }
    public void setType() {
    	JFrame setType=new JFrame("Choose Type");
    	setType.getContentPane().setBackground(new Color(229, 204, 255));
    	
    	JButton pic = new JButton("Picture");
        JButton num = new JButton("Number");
        
        JLabel label = new JLabel("Choose Type:");
        label.setForeground(new Color(150,30,250));
        label.setFont(new Font("Arial", Font.BOLD, 16));
        
        setType.add(label);
        label.setBounds(160,40,200,20);
        
        setType.setSize(500,340);
        setType.setLayout(null);
        setType.setVisible(true);
        setType.setResizable(false);
        setType.setLocationRelativeTo(null);
        
        num.setBounds(30,100,205,100);
        pic.setBounds(265,100,205,100);
        
        num.setFont(new Font("Arial", Font.BOLD, 16));
        pic.setFont(new Font("Arial", Font.BOLD, 16));
        
        num.setBackground(new Color(225,160,250));
        pic.setBackground(new Color(225,160,250));
        
        num.setForeground(new Color(150,30,250));
        pic.setForeground(new Color(150,30,250));
        
        setType.add(pic);
        setType.add(num);
        
        
        
        pic.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                isPicture=true;
                choosePic();
                setType.setVisible(false);
            }
        });
        num.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
                isPicture=false;
                createPuzzle();
                setType.setVisible(false);
            }
        });
    }
    boolean isPicture;
    public void choosePic() {
    	
    	
    	JFrame pictures=new JFrame("Choose Picture");
        pictures.getContentPane().setBackground(new Color(229, 204, 255));

        JButton a = new JButton();
        JButton b = new JButton();
        JButton c = new JButton();
        JButton d = new JButton();
        JButton e = new JButton();
        JButton f = new JButton();
        JLabel label = new JLabel("Choose A Picture Below:");
        label.setForeground(new Color(150,30,250));
        label.setFont(new Font("Arial", Font.BOLD, 16));


        pictures.add(label);
        label.setBounds(160,40,200,20);


        pictures.setSize(500,340);
        pictures.setLayout(null);
        pictures.setVisible(true);
        pictures.setResizable(false);
        pictures.setLocationRelativeTo(null);

        a.setBounds(40,80,120,90);
        b.setBounds(180,80,120,90);
        c.setBounds(320,80,120,90);
        d.setBounds(40,190,120,90);
        e.setBounds(180,190,120,90);
        f.setBounds(320,190,120,90);
        
        a.setBackground(new Color(225,160,250));
        b.setBackground(new Color(225,160,250));
        c.setBackground(new Color(225,160,250));
        d.setBackground(new Color(225,160,250));
        e.setBackground(new Color(225,160,250));
        f.setBackground(new Color(225,160,250));

        a.setForeground(new Color(150,30,250));
        b.setForeground(new Color(150,30,250));
        c.setForeground(new Color(150,30,250));
        d.setForeground(new Color(150,30,250));
        e.setForeground(new Color(150,30,250));
        f.setForeground(new Color(150,30,250));
        
        pictures.add(a);
        pictures.add(b);
        pictures.add(c);
        pictures.add(d);
        pictures.add(e);
        pictures.add(f);

        ImageIcon imageIcon1 = new ImageIcon("choosePicture1.png");	
        ImageIcon imageIcon1a = new ImageIcon("picture1.jpg");	
        Image image1 = imageIcon1.getImage();
        Image image1a = imageIcon1a.getImage();
        a.setIcon(new ImageIcon(image1));
        
        ImageIcon imageIcon2 = new ImageIcon("choosePicture2.png");
        ImageIcon imageIcon2a = new ImageIcon("picture2.jpg");	
        Image image2 = imageIcon2.getImage();
        Image image2a = imageIcon2a.getImage();
        b.setIcon(new ImageIcon(image2));
        
        ImageIcon imageIcon3 = new ImageIcon("choosePicture3.jpg");
        ImageIcon imageIcon3a = new ImageIcon("picture3.jpg");	
        Image image3 = imageIcon3.getImage();
        Image image3a = imageIcon3a.getImage();
        c.setIcon(new ImageIcon(image3));
        
        ImageIcon imageIcon4 = new ImageIcon("choosePicture4.jpg");
        ImageIcon imageIcon4a = new ImageIcon("picture4.jpg");	
        Image image4 = imageIcon4.getImage();
        Image image4a = imageIcon4a.getImage();
        d.setIcon(new ImageIcon(image4));
        
        ImageIcon imageIcon5 = new ImageIcon("choosePicture5.jpg");
        ImageIcon imageIcon5a = new ImageIcon("picture5.jpg");	
        Image image5 = imageIcon5.getImage();
        Image image5a = imageIcon5a.getImage();
        e.setIcon(new ImageIcon(image5));
        
        ImageIcon imageIcon6 = new ImageIcon("choosePicture6.jpg");
        ImageIcon imageIcon6a = new ImageIcon("picture6.jpg");	
        Image image6 = imageIcon6.getImage();
        Image image6a = imageIcon6a.getImage();
        f.setIcon(new ImageIcon(image6));
        
        a.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
            	isPicture=true;
            	createPuzzle();
            	addPicture(imageIcon1a);
            	pictures.setVisible(false);
            }
        });
        
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
            	isPicture=true;
            	createPuzzle();
            	addPicture(imageIcon2a);
            	pictures.setVisible(false);
            }
        });
        
        c.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
            	isPicture=true;
            	createPuzzle();
            	addPicture(imageIcon3a);
                pictures.setVisible(false);
            }
        });
                
        d.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
            	isPicture=true;
            	createPuzzle();
            	addPicture(imageIcon4a);
                pictures.setVisible(false);
            }
        });
        
        e.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
            	isPicture=true;
            	createPuzzle();
            	addPicture(imageIcon5a);
                pictures.setVisible(false);
            }
        });
        
        f.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	soundEffect(1);
            	isPicture=true;
            	createPuzzle();
            	addPicture(imageIcon6a);
                pictures.setVisible(false);
            }
        });
        
    }
    private void soundEffect(int x) {
    	//0 for wrong move - 1 for valid move
    	if(x==0) {
	        try {
	            if (clip != null && clip.isRunning()) {
	            }
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("wrongMove.wav").getAbsoluteFile());
	            clip = AudioSystem.getClip();
	            clip.open(audioInputStream);
	            clip.start();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
    	else if(x==1) {
	        try {
	            if (clip != null && clip.isRunning()) {
	            }
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("validMove.wav").getAbsoluteFile());
	            clip = AudioSystem.getClip();
	            clip.open(audioInputStream);
	            clip.start();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
    	// else if congrats
    }
    private void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }
    private void playMusic() {
    	try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("backgroundMusic2.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
    
}

/*
 * @author	Romain Semler
 * 
 * @file	Menu.java
 * 
 * @date	03/06/2016
 */

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu extends JFrame implements ActionListener {
	
	private Image icone = Toolkit.getDefaultToolkit().getImage("images/files.ico");
	private JButton afficher = new JButton();
	private boolean sontAffiches = false;
	private APropos apropos;
	private Runtime rt = Runtime.getRuntime();
	
	public Menu(String titre) {
		
		super(titre);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(Contenu());
        this.setJMenuBar(BarreMenu());
        this.setIconImage(icone);
       
        this.addWindowListener(new WindowAdapter(){   //Quand on clique sur la croix rouge
    	    public void windowClosing(WindowEvent evt)
    	   {
    	      Quitter();
    	   }
    	});
        
        
        pack();
	}
	
	private JMenuBar BarreMenu() {
	     	
		JMenuBar BarreMenu = new JMenuBar();
		
		JMenu plus = new JMenu("Plus...");	
		BarreMenu.add(plus);
		
		JMenuItem item = new JMenuItem("A Propos");			
		plus.add(item);												
		item.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        APropos();
		    }
		});
		
		plus.addSeparator();
		
		item = new JMenuItem("Quitter");			
		plus.add(item);												
		    item.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent arg0) {
		            Quitter();
		        	
		        }
		    });
		
		 	return BarreMenu;
	}
	
	JPanel Contenu() {
    	
    	JPanel Content = new JPanel();		
    	JLabel Titre = new JLabel("Attention : le Finder redémarrera au cours de l'opération.");
    	
    	Font police = new Font("Trebuchet MS", Font.BOLD, 17);
    	Font policebut = new Font("Trebuchet MS", Font.BOLD, 15);
    	
        Content.setLayout(new GridLayout(0,1));
        Content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        Content.setBackground(Color.ORANGE);
        
        Titre.setFont(police);
        Titre.setForeground(Color.BLACK);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        Titre.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        afficher = new JButton("Afficher les fichiers");
        afficher.setFont(policebut);
        afficher.addActionListener(this);
    
        Content.add(Titre);
        Content.add(afficher);
        
        return Content;
    }
    
    private void APropos() {
    	apropos = new APropos(this, "A propos", true);
    	apropos.setLocationRelativeTo(null);
    	apropos.setVisible(true);	
    }
    
    private void Quitter() {
    	if (JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment quitter ?",
                "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
            System.exit(0);
    }
    
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == afficher) {
			
			afficher.setEnabled(false);
			
			if (sontAffiches) {
				cacherFichiers();
			} else {
				afficherFichiers();
			}
		}
	}
    
    public void afficherFichiers() {
		new Thread(new Runnable(){
			public void run() {
				try {
					rt.exec("defaults write com.apple.finder AppleShowAllFiles TRUE").waitFor();
					rt.exec("killall Finder").waitFor();
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		afficher.setEnabled(true);
		afficher.setText("Cacher les fichiers");
		this.sontAffiches = true;
    }
    
    public void cacherFichiers() {

    	new Thread(new Runnable(){
			public void run() {
				try {
					rt.exec("defaults write com.apple.finder AppleShowAllFiles FALSE").waitFor();
					rt.exec("killall Finder").waitFor();
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
    	
    	afficher.setEnabled(true);
    	afficher.setText("Afficher les fichiers");
		this.sontAffiches = false;
    }
	
	public static void main(String[] args) {
		Menu menu = new Menu("Mes Fichiers Cachés");
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
		
	}

}

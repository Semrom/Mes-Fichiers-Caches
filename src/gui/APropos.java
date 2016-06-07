package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class APropos extends JDialog implements ActionListener {

	private JButton site;
	private JButton fin;
	
	public APropos(JFrame parent, String titre, boolean modal) {
		super(parent, titre, modal);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(Contenu());
        pack();
	}
	
JPanel Contenu() {
    	
    	JPanel Content = new JPanel();	
    	JPanel Boutons = new JPanel();
    	JLabel Titre = new JLabel("Mes Fichiers Cachés");
    	JLabel Auteur = new JLabel("Réalisé par Romain Semler en Java");
    	JLabel Version = new JLabel("Version 1.0");
    	JLabel Copyright = new JLabel("©2016");
    	fin = new JButton("OK");
    	site = new JButton("Visiter le site internet");
    	Font police = new Font("Trebuchet MS", Font.BOLD, 17);
    	Font policebut = new Font("Trebuchet MS", Font.BOLD, 15);
    	
        Content.setLayout(new GridLayout(0,1));
        Content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        Content.setBackground(Color.ORANGE);
        
        Titre.setFont(police);
        Titre.setForeground(Color.BLACK);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        
        Auteur.setFont(police);
        Auteur.setForeground(Color.BLACK);
        Auteur.setHorizontalAlignment(JLabel.CENTER);
        
        Version.setFont(police);
        Version.setForeground(Color.BLACK);
        Version.setHorizontalAlignment(JLabel.CENTER);
        
        Copyright.setFont(police);
        Copyright.setForeground(Color.BLACK);
        Copyright.setHorizontalAlignment(JLabel.CENTER);
        
    	Boutons.setLayout(new FlowLayout());
    	
		fin.setFont(policebut);
		site.setFont(policebut);

		fin.addActionListener(this);
		site.addActionListener(this);
		
		Boutons.add(fin);
		Boutons.add(site);
        		
        Boutons.setBackground(Color.ORANGE);
    
        Content.add(Titre);
        Content.add(Auteur);
        Content.add(Version);
        Content.add(Copyright);
        Content.add(Boutons);

        
        return Content;
    }

	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource() == site)
			try {
				ouvrirLien();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		if(arg0.getSource() == fin)
			fermer();
	}
	
	private void ouvrirLien() throws IOException {
		URI uri = URI.create("http://semrom.fr/");
		Desktop.getDesktop().browse(uri);
	}
	
	private void fermer() {
		this.dispose();
	}
}

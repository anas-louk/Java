package GS;

	import java.io.*;
	import java.util.*;

	// Classe de base Produit
	class Produit implements Serializable {
	    private String nom;
	    private double prix;
	    private int quantite;

	    public Produit(String nom, double prix, int quantite) {
	        this.nom = nom;
	        this.prix = prix;
	        this.quantite = quantite;
	    }

	    public String getNom() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    public double getPrix() {
	        return prix;
	    }

	    public void setPrix(double prix) {
	        this.prix = prix;
	    }

	    public int getQuantite() {
	        return quantite;
	    }

	    public void setQuantite(int quantite) {
	        this.quantite = quantite;
	    }

	    public void afficherDetails() {
	        System.out.println("Nom: " + nom + ", Prix: " + prix + ", Quantité: " + quantite);
	    }
	}

	// Classe ProduitAlimentaire
	class ProduitAlimentaire extends Produit {
	    private double poids;

	    public ProduitAlimentaire(String nom, double prix, int quantite, double poids) {
	        super(nom, prix, quantite);
	        this.poids = poids;
	    }

	    public double getPoids() {
	        return poids;
	    }

	    public void setPoids(double poids) {
	        this.poids = poids;
	    }

	    @Override
	    public void afficherDetails() {
	        super.afficherDetails();
	        System.out.println("Poids: " + poids + " kg");
	    }
	}

	// Classe ProduitElectronique
	class ProduitElectronique extends Produit {
	    private int puissance;

	    public ProduitElectronique(String nom, double prix, int quantite, int puissance) {
	        super(nom, prix, quantite);
	        this.puissance = puissance;
	    }

	    public int getPuissance() {
	        return puissance;
	    }

	    public void setPuissance(int puissance) {
	        this.puissance = puissance;
	    }

	    @Override
	    public void afficherDetails() {
	        super.afficherDetails();
	        System.out.println("Puissance: " + puissance + " watts");
	    }
	}

	// Classe Stock
	class Stock {
	    private List<Produit> produits;

	    public Stock() {
	        this.produits = new ArrayList<>();
	    }

	    public void ajouterProduit(Produit p) {
	        produits.add(p);
	    }

	    public void afficherTousLesProduits() {
	        for (Produit p : produits) {
	            p.afficherDetails();
	            System.out.println("--------------------");
	        }
	    }

	    public double calculerValeurTotaleStock() {
	        double valeurTotale = 0;
	        for (Produit p : produits) {
	            valeurTotale += p.getPrix() * p.getQuantite();
	        }
	        return valeurTotale;
	    }

	    public void mettreAJourQuantite(String nom, int nouvelleQuantite) {
	        for (Produit p : produits) {
	            if (p.getNom().equalsIgnoreCase(nom)) {
	                p.setQuantite(nouvelleQuantite);
	                System.out.println("Quantité mise à jour pour " + nom);
	                return;
	            }
	        }
	        System.out.println("Produit non trouvé : " + nom);
	    }

	    public void augmenterQuantitePourcentage(String nom, double pourcentage) {
	        for (Produit p : produits) {
	            if (p.getNom().equalsIgnoreCase(nom)) {
	                int nouvelleQuantite = (int) (p.getQuantite() * (1 + pourcentage / 100));
	                p.setQuantite(nouvelleQuantite);
	                System.out.println("Quantité augmentée de " + pourcentage + "% pour " + nom);
	                return;
	            }
	        }
	        System.out.println("Produit non trouvé : " + nom);
	    }

	    public void enregistrerProduits(String nomFichier) {
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
	            produits.sort(Comparator.comparing(Produit::getNom));
	            oos.writeObject(produits);
	            System.out.println("Produits enregistrés dans " + nomFichier);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	// Classe principale Main
	public class GestionStock {
	    public static void main(String[] args) {
	        Stock stock = new Stock();

	        ProduitAlimentaire pomme = new ProduitAlimentaire("Pomme", 2.5, 50, 1.2);
	        ProduitAlimentaire banane = new ProduitAlimentaire("Banane", 1.8, 100, 1.0);

	        ProduitElectronique tele = new ProduitElectronique("Télévision", 300.0, 10, 150);
	        ProduitElectronique frigo = new ProduitElectronique("Réfrigérateur", 500.0, 5, 200);

	        stock.ajouterProduit(pomme);
	        stock.ajouterProduit(banane);
	        stock.ajouterProduit(tele);
	        stock.ajouterProduit(frigo);

	        System.out.println("Liste des produits :");
	        stock.afficherTousLesProduits();

	        System.out.println("Valeur totale du stock : " + stock.calculerValeurTotaleStock() + " €");

	        stock.mettreAJourQuantite("Pomme", 60);
	        stock.augmenterQuantitePourcentage("Banane", 10);

	        System.out.println("Après mise à jour des quantités :");
	        stock.afficherTousLesProduits();

	        stock.enregistrerProduits("stock.bin");
	    }
	}


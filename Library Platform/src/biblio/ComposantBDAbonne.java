package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Composant logiciel assurant la gestion des abonnés.
 */
public class ComposantBDAbonne {

  /**
   * Récupération de la liste complète des abonnés.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un abonné.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id</li>
   *   <li>1 : nom</li>
   *   <li>2 : prénom</li>
   *   <li>3 : statut</li>
   *   <li>4 : adresse email</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeTousLesAbonnes() throws SQLException {
    // L'ArrayList qui sera renvoyé : structure de données de type tableau non limitée en taille
    ArrayList<String[]> abonnes = new ArrayList<String[]>();
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select * from abonne";
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] abonne = new String[5];
      abonne[0] = rset.getString("id");
      abonne[1] = rset.getString("nom");
      abonne[2] = rset.getString("prenom");
      abonne[3] = rset.getString("statut");
      abonne[4] = rset.getString("email");

      abonnes.add(abonne);
    }
    rset.close();
    stmt.close();
    return abonnes;
  }

  /**
   * Retourne le nombre d'abonnés référencés dans la base.
   * 
   * @return le nombre d'abonnés.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbAbonnes() throws SQLException {
	 int nbAbonne = 0;
		Statement stmt = Connexion.getConnection().createStatement();
		String sql = "select count(id) from abonne";
		ResultSet rset = stmt.executeQuery(sql);
		while(rset.next()){
			nbAbonne = rset.getInt("count");
		}
	rset.close();
	stmt.close();
	 return nbAbonne;
  }

  /**
   * Récupération des informations sur un abonné à partir de son identifiant.
   * 
   * @param idAbonne : id de l'abonné à rechercher
   * @return un tableau de chaînes de caractères (<code>String[]</code>). Chaque
   * tableau doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id</li>
   *   <li>1 : nom</li>
   *   <li>2 : prénom</li>
   *   <li>3 : statut</li>
   *   <li>4 : adresse email</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static String[] getAbonne(int idAbonne) throws SQLException {
    String[] abonne = new String[5];
    Statement stmt = Connexion.getConnection().createStatement();
	String sql = "select * from abonne where id =" + idAbonne;
	ResultSet rset = stmt.executeQuery(sql);
	while(rset.next()){
	     abonne[0] = rset.getString("id");
	     abonne[1] = rset.getString("nom");
	     abonne[2] = rset.getString("prenom");
	     abonne[3] = rset.getString("statut");
	     abonne[4] = rset.getString("email");
	}
    return abonne;
  }

  /**
   * Référencement d'un nouvel abonné dans la base de données.
   * 
   * @param nom
   * @param prenom
   * @param statut (deux valeurs possibles <i>Etudiant</i> et <i>Enseignant</i>)
   * @param email
   * @return l'identifiant de l'abonné référencé.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int insererNouvelAbonne(String nom, String prenom, String statut, String email) throws SQLException {
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "insert into abonne values(nextval('abonne_id_seq'), '"+nom+"','"+prenom+"','"+statut+"','"+email+"')";
	  stmt.executeUpdate(sql);
	  String sql2 = "select id from abonne where nom = '"+nom+"'and prenom = '"+prenom+"'and statut = '"+statut+"'and email = '"+email+"'";
	  ResultSet rset = stmt.executeQuery(sql2);
	  int id = 0;
	 	while(rset.next()){
	 		id = rset.getInt("id");
	 	}
	  stmt.close();
	  rset.close();
	  return id;
  }

  /**
   * Modification des informations d'un abonné donné connu à partir de son
   * identifiant : les nouvelles valeurs (nom, prenom, etc.) écrasent les anciennes.
   * 
   * @param idAbonne : identifiant de l'abonné dont on veut modifier les informations.
   * @param nom
   * @param prenom
   * @param statut (deux valeurs possibles <i>Etudiant</i> et <i>Enseignant</i>)
   * @param email
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void modifierAbonne(int idAbonne, String nom, String prenom, String statut, String email) throws SQLException {
	    Statement stmt = Connexion.getConnection().createStatement();
	 	String sql = "update abonne "+
	 					"set nom = '"+nom+
	 					"', prenom = '" +prenom+
	 					"', statut = '" +statut+
	 					"', email = '" +email+
	 					"' where id= " +idAbonne;
	 	stmt.executeUpdate(sql);
	    stmt.close();
  }

  /**
   * Suppression d'un abonné connu à partir de son identifiant.
   * 
   * @param idAbonne : identifiant de l'utilisateur
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void supprimerAbonne(int idAbonne) throws SQLException {
	   Statement stmt = Connexion.getConnection().createStatement();
	 	String sql = "delete from abonne where id="+idAbonne;
	 	stmt.executeUpdate(sql);
	    stmt.close();
  }
}

package persistence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import persistence.extendeddb.BdeApi;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;

public class Database {
    private static BdeApi connection;

    // Configuration des requêtes textuelles
    private static Path sourcePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description");
    private static Path indexPath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Index");
    private static String table = "Site";
    private static String joinKey = "id";

    // Configuration SQL pour les connexions classiques
    private static String system = "mysql";
    private static String host = "";
    private static String base = "";
    private static String user = ""; // Assurez-vous que le nom d'utilisateur est correct
    private static String password = ""; // Assurez-vous que le mot de passe est correct

    private Database() {
        // Constructeur privé pour le pattern Singleton
    }

    public static BdeApi getConnection() {
        if (connection == null) {
            // Tenter d'obtenir une connexion via JNDI
            try {
                // Charger explicitement le pilote JDBC MySQL (utile pour Connector/J 5.1.6)
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Pilote JDBC MySQL chargé avec succès.");

                // Obtenir la DataSource via JNDI
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                DataSource ds = (DataSource) envContext.lookup("jdbc/AGPDB");
                @SuppressWarnings("unused")
				Connection conn = ds.getConnection();
                System.out.println("Connexion à la base de données réussie via JNDI.");

                // Créer la configuration SQL (peut-être pas nécessaire si vous utilisez déjà la connexion)
                SQLConfiguration sqlConfiguration = new SQLConfiguration(
                    system,
                    host,
                    base,
                    user,
                    password
                );

                TextualConfiguration textualConfiguration = new TextualConfiguration(
                    sourcePath,
                    indexPath,
                    table,
                    joinKey
                );

                // Initialiser ExtendedDatabaseAPI avec les configurations et la connexion
                connection = new BdeApi(sqlConfiguration, textualConfiguration);
                System.out.println("ExtendedDatabaseAPI initialisé via JNDI.");

            } catch (NamingException | SQLException | ClassNotFoundException e) {
                System.out.println("Connexion via JNDI échouée. Tentative de connexion classique.");
                // Si la connexion via JNDI échoue, tenter une connexion classique
                try {
                    // Charger le pilote JDBC MySQL
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Pilote JDBC MySQL chargé pour la connexion classique.");

                    // Construire l'URL JDBC
                    String url = "jdbc:mysql://mysql-agp-antilles.alwaysdata.net:3306/agp-antilles_database?useSSL=false&serverTimezone=UTC";

                    // Établir la connexion via DriverManager
                    @SuppressWarnings("unused")
					Connection conn = DriverManager.getConnection(url, user, password);
                    System.out.println("Connexion à la base de données réussie via DriverManager.");

                    // Créer la configuration SQL
                    SQLConfiguration sqlConfiguration = new SQLConfiguration(
                        system,
                        host,
                        base,
                        user,
                        password
                    );

                    TextualConfiguration textualConfiguration = new TextualConfiguration(
                        sourcePath,
                        indexPath,
                        table,
                        joinKey
                    );

                    // Initialiser ExtendedDatabaseAPI avec les configurations et la connexion
                    connection = new BdeApi(sqlConfiguration, textualConfiguration);
                    System.out.println("ExtendedDatabaseAPI initialisé via DriverManager.");

                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException("Erreur lors de la connexion à la base de données", ex);
                }
            }
        }

        return connection;
    }
    
}

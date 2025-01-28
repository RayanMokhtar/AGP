package persistence.extendeddb.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Searcher class
 * 
 * Used to search keywords in an index.
 */
public class Searcher {
    
    private static final int MAX_RESULTS = 100;
    private final Path indexPath;
    
    /**
     * Constructor
     * @param indexPath the directory that contains the index
     */
    public Searcher(Path indexPath) {
        this.indexPath = indexPath;
    }
    
    /**
     * search
     * 
     * Method used to search keywords in the index.
     * 
     * @param query A textual query.
     * @return TextualResults
     * @throws IOException
     * @throws ParseException
     */
    public TextualResults search(String query) throws IOException, ParseException {
        
        // 1. Vérification de la requête
        if (query == null || query.isEmpty()) {
            System.err.println("Error: a non-empty query is required.");
            return null;
        }
        
        // 2. Initialisations des objets Lucene
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("content", analyzer);
        
        try (Directory directory = FSDirectory.open(indexPath);
             IndexReader reader = DirectoryReader.open(directory)) {
            
            IndexSearcher searcher = new IndexSearcher(reader);
            Query parsedQuery = parser.parse(query);
            
            // 3. Recherche
            ScoreDoc[] hits = searcher.search(parsedQuery, MAX_RESULTS).scoreDocs;
            
            TextualResults textualResults = new TextualResults();
            
            // 4. Parcours des résultats
            for (ScoreDoc hit : hits) {
                // Récupération du Document à partir des champs stockés
                Document doc = searcher.storedFields().document(hit.doc);
                
                // Calcul du "score" (multiplié par 1000, comme dans l'exemple initial)
                int score = (int) (hit.score * 1000);
                
                // Récupération du chemin du fichier (champ "path" indexé/storé dans Lucene)
                String filePath = doc.get("path");
                if (filePath == null) {
                    // Si aucun champ "path", ignorer ou gérer autrement
                    continue;
                }
                
                // Lecture du contenu du fichier
                StringBuilder contentBuilder = new StringBuilder();
                File file = new File(filePath);
                
                try (FileReader fr = new FileReader(file);
                     BufferedReader br = new BufferedReader(fr)) {
                     
                    String line;
                    while ((line = br.readLine()) != null) {
                        contentBuilder.append(line).append("\n");
                    }
                }
                
                // Nom de fichier (ex. "123.txt")
                String filename = file.getName();
                // On retire l'extension
                int dotIndex = filename.lastIndexOf('.');
                String baseFilename = (dotIndex >= 0) ? filename.substring(0, dotIndex) : filename;
                
                int id;
                try {
                    id = Integer.parseInt(baseFilename);
                } catch (NumberFormatException e) {
                    // Si le nom ne correspond pas à un entier, gérer le cas
                    // On peut mettre -1 ou continuer
                    id = -1;
                }
                
                // Ajout du résultat
                textualResults.add(new TextualResult(id, score, contentBuilder.toString()));
            }
            
            return textualResults;
        }
    }
}

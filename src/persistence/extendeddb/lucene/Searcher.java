package persistence.extendeddb.lucene;

import java.io.*;
import java.nio.file.*;
import java.text.Normalizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
    private static final int MAX_RESULTS = 100;
    private final Path indexPath;

    public Searcher(Path indexPath) {
        this.indexPath = indexPath;
    }

    public TextualResults search(String query) throws IOException, ParseException {
        TextualResults textualResults = new TextualResults();

        if (query == null || query.trim().isEmpty()) {
            return textualResults;
        }

        // Utilisation de l'analyseur standard
        Analyzer analyzer = new StandardAnalyzer();

        try (Directory directory = FSDirectory.open(indexPath)) {
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            // Configuration du QueryParser avec options de recherche floue
            QueryParser parser = new QueryParser("content", analyzer);
            parser.setDefaultOperator(QueryParser.Operator.OR);
            parser.setAllowLeadingWildcard(true);

            // Construction de la requête
            String normalizedQuery = normalizeQuery(query);
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            
            // Ajout des différentes variantes de recherche
            booleanQuery.add(parser.parse(normalizedQuery), BooleanClause.Occur.SHOULD);
            booleanQuery.add(new FuzzyQuery(new Term("content", normalizedQuery), 2), BooleanClause.Occur.SHOULD);
            booleanQuery.add(parser.parse("*" + normalizedQuery + "*"), BooleanClause.Occur.SHOULD);

            // Recherche
            ScoreDoc[] hits = searcher.search(booleanQuery.build(), MAX_RESULTS).scoreDocs;

            // Traitement des résultats
            for (ScoreDoc hit : hits) {
                Document doc = searcher.storedFields().document(hit.doc);
                String filePath = doc.get("path");
                
                if (filePath != null) {
                    File file = new File(filePath);
                    String content = new String(Files.readAllBytes(Paths.get(filePath)));
                    int id = extractId(file.getName());
                    float normalizedScore = hit.score * 1000;
                    
                    textualResults.add(new TextualResult(id, (int)normalizedScore, content));
                }
            }
        }

        return textualResults;
    }

    private String normalizeQuery(String query) {
        return Normalizer.normalize(query, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            .toLowerCase()
            .trim()
            .replaceAll("[^a-z0-9 ]", " ");
    }

    private int extractId(String filename) {
        try {
            return Integer.parseInt(filename.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
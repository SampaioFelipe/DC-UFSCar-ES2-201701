package net.sf.jabref.logic.importer.fileformat;

import net.sf.jabref.logic.importer.Importer;
import net.sf.jabref.logic.importer.ParserResult;
import net.sf.jabref.logic.util.FileExtensions;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro on 20/07/17.
 */
public class CSVImporter extends Importer {

    @Override
    public String getName(){
        return "CSV Importer";
    }

    @Override
    public FileExtensions getExtensions(){
        return FileExtensions.TXT;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader){
        return true;
    }

    @Override
    public ParserResult importDatabase(BufferedReader reader) throws IOException{
        List<BibEntry> bibitems = new ArrayList<>();

        String line = reader.readLine();
        while(line != null){
            if(!line.trim().isEmpty()){
                String[] fields = line.split(";");
                BibEntry be = new BibEntry();
                be.setType(BibtexEntryTypes.BOOK);
                be.setField("year", fields[0]);
                be.setField("author", fields[1]);
                be.setField("title", fields[2]);
                bibitems.add(be);
                line = reader.readLine();
            }
        }

        return new ParserResult(bibitems);
    }


}

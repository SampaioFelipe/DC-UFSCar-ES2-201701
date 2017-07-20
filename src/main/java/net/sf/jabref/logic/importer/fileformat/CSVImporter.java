package net.sf.jabref.logic.importer.fileformat;

import net.sf.jabref.gui.importer.ImportFormats;
import net.sf.jabref.logic.importer.OutputPrinter;
import net.sf.jabref.model.entry.BibEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.sf.jabref.logic.importer.ImportFormatReader;
import net.sf.jabref.model.entry.BibtexEntryType;
import net.sf.jabref.model.entry.BibtexEntryTypes;

/**
 * Created by pedro on 20/07/17.
 */
public class CSVImporter extends ImportFormats {

    @Override
    public String getFormatName(){
        return "CSV";
    }

    @Override
    public String getExtensions(){
        return "csv";
    }

    @Override
    public boolean isRecognizedFormat(InputStream stream) throws IOException {
        return true;
    }

    @Override
    public List<BibEntry> importEntries(InputStream stream, OutputPrinter printer) throws IOException{
        List<BibEntry> bibitems = new ArrayList<>();
        BufferedReader in = new BufferedReader(ImportFormatReader.getReaderDefaultEncoding(stream));

        String line = in.readLine();
        while(line != null){
            if(!line.trim().isEmpty()){
                String[] fields = line.split(";");
                BibEntry be = new BibEntry();
                be.setType(BibtexEntryTypes.BOOK);
                be.setField("year", fields[0]);
                be.setField("author", fields[1]);
                be.setField("title", fields[2]);
                bibitems.add(be);
                line = in.readLine();
            }
        }

        return bibitems;
    }

}

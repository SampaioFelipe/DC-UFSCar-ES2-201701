package net.sf.jabref.logic.importer.fileformat;

import net.sf.jabref.logic.importer.Importer;
import net.sf.jabref.logic.importer.ParserResult;
import net.sf.jabref.logic.util.FileExtensions;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.model.OpenDocument;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pedro on 21/07/17.
 */
public class ODSImporter extends Importer {

    @Override
    public String getName() {
        return "ODS Importer";
    }

    @Override
    public FileExtensions getExtensions() {
        return FileExtensions.ODS;
    }

    @Override
    public String getDescription() {
        return "ODS File";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) {
        return true;
    }

    @Override
    public ParserResult importDatabase(BufferedReader input) throws IOException {
        return null;
    }

    @Override
    public ParserResult importDatabase(Path filePath, Charset encoding) throws IOException{
        List<BibEntry> bibitems = new ArrayList<>();
        final OpenDocument doc = new OpenDocument();

        File file = new File(filePath.toString());

        SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
        Sheet sheet = spreadSheet.getSheet(0);

        int row = 500;
        for(int i = 0; i < row; i++){
            MutableCell cell = sheet.getCellAt(0, i);
            if(cell.getValue().toString() != "") {
                BibEntry be = new BibEntry();
                be.setType(BibtexEntryTypes.BOOK);

                be.setField("year", cell.getValue().toString());
                cell = sheet.getCellAt(1, i);
                be.setField("author", cell.getValue().toString());
                cell = sheet.getCellAt(2, i);
                be.setField("title", cell.getValue().toString());
                bibitems.add(be);
            }
        }

        ParserResult parserResult = new ParserResult(bibitems);
        parserResult.getMetaData().setEncoding(encoding);
        parserResult.setFile(filePath.toFile());

        return parserResult;
    }
}


package net.sf.jabref.logic.importer;

import net.sf.jabref.logic.util.FileExtensions;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by pedro on 20/07/17.
 */
public class XLSImporter extends Importer {

    @Override
    public String getName(){
        return "XLS Importer";
    }

    @Override
    public FileExtensions getExtensions(){
        return FileExtensions.XLS;
    }

    @Override
    public String getDescription() {
        return ("XLS file");
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader){
        return true;
    }

    @Override
    public ParserResult importDatabase(BufferedReader input) throws IOException {
        return null;
    }

    @Override
    public ParserResult importDatabase(Path filePath, Charset encoding) throws IOException {
        List<BibEntry> bibitems = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(new File(filePath.toString()));
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        HSSFSheet sheet = workbook.getSheetAt(0);

        int rowNum = sheet.getLastRowNum()+1;
        for(int i=0; i<rowNum; i++){
            HSSFRow row = sheet.getRow(i);
            BibEntry be = new BibEntry();
            be.setType(BibtexEntryTypes.BOOK);
            HSSFCell cel = row.getCell(0);
            be.setField("year", cel.toString());
            cel = row.getCell(1);
            be.setField("author", cel.toString());
            cel = row.getCell(2);
            be.setField("title", cel.toString());
            bibitems.add(be);

        }

        ParserResult parserResult = new ParserResult(bibitems);
        parserResult.getMetaData().setEncoding(encoding);
        parserResult.setFile(filePath.toFile());

        return parserResult;
    }
}


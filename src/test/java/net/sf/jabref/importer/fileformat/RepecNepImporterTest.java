package net.sf.jabref.importer.fileformat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefPreferences;
import net.sf.jabref.logic.bibtex.BibEntryAssert;
import net.sf.jabref.model.entry.BibEntry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RepecNepImporterTest {

    private RepecNepImporter testImporter;


    @Before
    public void setUp() {
        Globals.prefs = JabRefPreferences.getInstance();
        testImporter = new RepecNepImporter();
    }

    @Test
    public final void testIsRecognizedFormat() throws IOException, URISyntaxException {
        List<String> accepted = Arrays.asList("RepecNepImporterTest1.txt", "RepecNepImporterTest2.txt",
                "RepecNepImporterTest3.txt");
        for (String s : accepted) {
            Path file = Paths.get(RepecNepImporter.class.getResource(s).toURI());
            Assert.assertTrue(testImporter.isRecognizedFormat(file, Charset.defaultCharset()));
        }
    }

    @Test
    public final void testIsNotRecognizedFormat() throws IOException, URISyntaxException {
        List<String> notAccepted = Arrays.asList("RepecNep1.xml", "CopacImporterTest1.txt", "RisImporterTest1.ris",
                "CopacImporterTest2.txt", "IEEEImport1.txt");
        for (String s : notAccepted) {
            Path file = Paths.get(RepecNepImporter.class.getResource(s).toURI());
            Assert.assertFalse(testImporter.isRecognizedFormat(file, Charset.defaultCharset()));
        }
    }

    @Test
    public final void testImportEntries1() throws IOException, URISyntaxException {
        Path file = Paths.get(RepecNepImporter.class.getResource("RepecNepImporterTest1.txt").toURI());
        try (InputStream bibIn = RepecNepImporter.class.getResourceAsStream("RepecNepImporterTest1.bib")) {
            List<BibEntry> entries = testImporter.importDatabase(file, Charset.defaultCharset()).getDatabase().getEntries();
            Assert.assertEquals(1, entries.size());
            BibEntryAssert.assertEquals(bibIn, entries.get(0));
        }
    }

    @Test
    public final void testImportEntries2() throws IOException, URISyntaxException {
        Path file = Paths.get(RepecNepImporter.class.getResource("RepecNepImporterTest2.txt").toURI());
        try (InputStream bibIn = RepecNepImporter.class.getResourceAsStream("RepecNepImporterTest2.bib")) {
            List<BibEntry> entries = testImporter.importDatabase(file, Charset.defaultCharset()).getDatabase().getEntries();
            Assert.assertEquals(1, entries.size());
            BibEntryAssert.assertEquals(bibIn, entries.get(0));
        }
    }

    @Test
    public final void testImportEntries3() throws IOException, URISyntaxException {
        Path file = Paths.get(RepecNepImporter.class.getResource("RepecNepImporterTest3.txt").toURI());
        try (InputStream bibIn = RepecNepImporter.class.getResourceAsStream("RepecNepImporterTest3.bib")) {
            List<BibEntry> entries = testImporter.importDatabase(file, Charset.defaultCharset()).getDatabase().getEntries();
            Assert.assertEquals(1, entries.size());
            BibEntryAssert.assertEquals(bibIn, entries.get(0));
        }
    }

    @Test
    public final void testGetFormatName() {
        Assert.assertEquals("REPEC New Economic Papers (NEP)", testImporter.getFormatName());

    }

    @Test
    public final void testGetCliId() {
        Assert.assertEquals("repecnep", testImporter.getId());
    }

    @Test
    public final void testGetDescription() {
        Assert.assertEquals("Imports a New Economics Papers-Message (see http://nep.repec.org)\n"
                + "from the REPEC-NEP Service (see http://www.repec.org).\n"
                + "To import papers either save a NEP message as a text file and then import or\n"
                + "copy&paste the papers you want to import and make sure, one of the first lines\n"
                + "contains the line \"nep.repec.org\".", testImporter.getDescription());
    }
}

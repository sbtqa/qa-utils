package ru.sbtqa.tag.qautils.parsers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import ru.sbtqa.tag.qautils.parsers.exception.ParserException;
import ru.sbtqa.tag.qautils.parsers.interfaces.Parser;
import ru.sbtqa.tag.qautils.parsers.interfaces.callback.ParserCallback;

/**
 * PDF file parser
 */
public class PdfParser implements Parser, ParserCallback {

    private static final Logger log = LoggerFactory.getLogger(PdfParser.class);

    @Override
    public String call(ParserItem item) {
        String result = "";
        try {
            result = read(item.getSource(), item.getPath());
        } catch (Exception e) {
            log.error("Error to get value by path {} in source {}", item.getPath(), item.getSource(), e);
        }

        return result;
    }

    /**
     * Applies this path to provided source as file name.
     * Render source file to string and apply the path on it.
     *
     * @param source the system-dependent file name
     * @param path the path to apply
     * @return a string matched by the given path
     * @throws ParserException if an error occurred while applying the path on source
     */
    @Override
    public String read(String source, String path) throws ParserException {
        ContentHandler handler;
        try (InputStream pdf = new FileInputStream(source)) {
            PDFParser parser = new PDFParser();
            handler = new ToXMLContentHandler();
            ParseContext context = new ParseContext();
            parser.parse(pdf, handler, new Metadata(), context);
            return new XmlParser().read(handler.toString(), path);
        } catch (IOException | SAXException | TikaException ex) {
            throw new ParserException("Failed to get value by path " + path + " in source " + source, ex);
        }
    }
}

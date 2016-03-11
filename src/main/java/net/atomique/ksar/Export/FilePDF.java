
package net.atomique.ksar.Export;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.FontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import net.atomique.ksar.Config;
import net.atomique.ksar.VersionNumber;
import net.atomique.ksar.kSar;
import net.atomique.ksar.Graph.Graph;
import net.atomique.ksar.Graph.GraphList;
import net.atomique.ksar.UI.ParentNodeInfo;
import net.atomique.ksar.UI.SortedTreeNode;
import net.atomique.ksar.UI.TreeNodeInfo;

/**
 *
 * @author Max
 */
public class FilePDF extends PdfPageEventHelper implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilePDF.class);
    
    private GraphExportCallback exportCallback = null;
    
    public FilePDF(String filename, kSar hissar) {
        pdffilename = filename;
        mysar = hissar;
    }

    public FilePDF(String filename, kSar hissar, GraphExportCallback exportCallback) {
        pdffilename = filename;
        mysar = hissar;
        this.exportCallback = exportCallback;
    }

    public void run() {
        totalPages += mysar.getPageToPrint();
        org.jfree.text.TextUtilities.setUseDrawRotatedStringWorkaround(true);
        if ("LEGAL".equals(Config.getPDFPageFormat())) {
            document = new Document(PageSize.LEGAL.rotate());
        }
        else if ("LETTER".equals(Config.getPDFPageFormat())) {
            document = new Document(PageSize.LETTER.rotate());
        }
        else {
            document = new Document(PageSize.A4.rotate());
        }
        
        pdfheight = document.getPageSize().getHeight();
        pdfwidth = document.getPageSize().getWidth();
        pageheight = pdfheight - (2 * pdfmargins);
        pagewidth = pdfwidth - (2 * pdfmargins);
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdffilename));
        
            writer.setPageEvent(this);
            writer.setCompressionLevel(0);
    
            // document parameter before open
            document.addTitle("kSar Grapher");
            document.addCreator("kSar Version:" + VersionNumber.getVersionNumber());
            document.addAuthor("Xavier cherif");
    
            // open the doc
            document.open();
            pdfcb = writer.getDirectContent();
            PdfOutline root = pdfcb.getRootOutline();
    
            indexPage(writer, document);
    
            exportTreenode(mysar.graphtree, root);
    
            document.close();
        } catch (DocumentException ex) {
            LOGGER.error("", ex);
        } catch (FileNotFoundException ex) {
            LOGGER.error("", ex);
        }
        finally {
            
        }

        if (exportCallback != null) {
            exportCallback.onEnd();
        }
    }


    public void exportTreenode(SortedTreeNode node, PdfOutline root) {
        int num = node.getChildCount();
        if (num > 0) {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof ParentNodeInfo) {
                ParentNodeInfo tmpnode = (ParentNodeInfo) obj1;
                GraphList nodeobj = tmpnode.getNodeObject();
                if ( nodeobj.isPrintSelected() ) {
                    root = new PdfOutline(root, new PdfDestination(PdfDestination.FIT), nodeobj.getTitle());
                }
            }
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                exportTreenode(l, root);
            }
        } else {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                TreeNodeInfo tmpnode = (TreeNodeInfo) obj1;
                Graph nodeobj = tmpnode.getNode_object();
                if ( nodeobj.isPrintSelected() ) {
                    root = new PdfOutline(root, new PdfDestination(PdfDestination.FIT), nodeobj.getTitle());
                    updateUi();
                    addchart(writer, nodeobj);
                    document.newPage();
                }
            }
        }
    }

    private void updateUi() {
        if (exportCallback != null) {
            exportCallback.onGraphExported();
        }
    }

    public void onEndPage(PdfWriter writer, Document document) {
        try {
            String text = "Page " + writer.getPageNumber() + "/" + totalPages;

            pdfcb.beginText();
            pdfcb.setFontAndSize(bf, 10);
            pdfcb.setColorFill(new BaseColor(0x00, 0x00, 0x00));
            pdfcb.showTextAligned(PdfContentByte.ALIGN_RIGHT, text, ((pdfheight - pdfmargins) - 10), 10 + pdfmargins, 0);
            pdfcb.endText();
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public int addchart(PdfWriter writer, Graph graph) {
        JFreeChart chart = graph.getgraph(mysar.parser.getStartofgraph(), mysar.parser.getEndofgraph());
        PdfTemplate pdftpl = pdfcb.createTemplate(pagewidth,pageheight);
        Graphics2D g2d = pdftpl.createGraphics(pagewidth,pageheight , mapper);
        Double r2d = new Rectangle2D.Double(0, 0, pagewidth,pageheight );
        chart.draw(g2d, r2d, chartinfo);
        g2d.dispose();
        pdfcb.addTemplate(pdftpl, pdfmargins, pdfmargins);
        try {
            writer.releaseTemplate(pdftpl);
        } catch (IOException ioe) {
            System.err.println("Unable to write to : " + pdffilename);
        }
        return 0;
    }

    private void indexPage(PdfWriter writer, Document document) {
        String title = "Statistics";
        String date = "On " + mysar.parser.getDate();
        pdfcb.beginText();
        pdfcb.setFontAndSize(bf, 48);
        pdfcb.setColorFill(new BaseColor(0x00, 0x00, 0x00));
        pdfcb.showTextAligned(PdfContentByte.ALIGN_CENTER, title, ((pdfheight - pdfmargins) / 2), 500, 0);
        pdfcb.setFontAndSize(bf, 36);
        pdfcb.showTextAligned(PdfContentByte.ALIGN_CENTER, date, ((pdfheight - pdfmargins) / 2), 300, 0);
        pdfcb.endText();
        document.newPage();
    }

    private float pdfheight;
    private float pdfwidth;
    private int pdfmargins = 10;
    private float pageheight;
    private float pagewidth;
    private int totalPages = 1; // page 1 (index)
    private String pdffilename = null;
    private Document document = null;
    private PdfWriter writer = null;
    private PdfContentByte pdfcb;
    private kSar mysar = null;
    private FontMapper mapper = new DefaultFontMapper();
    private BaseFont bf = FontFactory.getFont(FontFactory.COURIER).getCalculatedBaseFont(false);
    private ChartRenderingInfo chartinfo = null;
}

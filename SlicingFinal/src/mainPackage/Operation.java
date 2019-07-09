package mainPackage;

import controlDependence.ControlDependenceGraph;
import dataDependence.DataDependenceGraph;
import graph.GraphNode;
import org.eclipse.jdt.core.dom.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Operation {

    int i = 0;

    public String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();

        return  fileData.toString();
    }

    void traversal (GraphNode node)
    {
        System.out.println(node.node);

        for(GraphNode g : node.children)
        {
            System.out.println(i);
            i++;
            traversal(g);
            i--;
        }
    }

    public void operations () {

        try {
            String str = readFileToString("C:\\Users\\ASUS\\Desktop\\demo\\src\\Saal.java");
            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(str.toCharArray());
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            parser.setResolveBindings(true);
            parser.setEnvironment(new String[] {"C:\\Users\\ASUS\\Desktop\\demo\\out\\production\\demo"}, new String[] {"C:\\Users\\ASUS\\Desktop\\demo\\src"}, null, true);
            parser.setUnitName("Saal.java");
            CompilationUnit cu = (CompilationUnit) parser.createAST(null);

            ControlDependenceGraph cdg = new ControlDependenceGraph(cu);

            GraphNode root = cdg.getControlDependenceGraph();

            traversal(root);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
